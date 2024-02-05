package idea.pti.insaf.purchi.list

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import idea.pti.insaf.purchi.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import idea.pti.insaf.purchi.data.Candidate
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.TreeMap


object Repo {

    val TAG = "DataCachingManager"

    var allCandidates: List<Candidate> = ArrayList()
    var na: List<Candidate> = ArrayList()
    var ps: List<Candidate> = ArrayList()
    var pk: List<Candidate> = ArrayList()
    var pp: List<Candidate> = ArrayList()
    var pb: List<Candidate> = ArrayList()

    var cityName: String = ""

    fun getDataVersion(): String {
        val dataVer = "$source${dataManager?.getVersion()}"
        return dataVer
    }

    private var source: String = ""
    var hashMap: HashMap<String, Candidate>? = HashMap()
    var filterMap: TreeMap<String, Candidate> = TreeMap(String.CASE_INSENSITIVE_ORDER)

    var dataManager: DataCachingManager? = null
    fun initialize(context: Context) {
        dataManager = DataCachingManager(context)
    }

    private fun addToSymbolHashMap() {
        allCandidates.forEach {
            if (it.constituency.isNotEmpty()) {
                hashMap!![it.constituency] = it
            }
        }
    }

    private fun loadFromJson(inputStream: InputStream): ArrayList<Candidate> {
        var list = parseJson(inputStream)
        if (list == null) {
            list = parseJsonLenient(inputStream)
            if (list == null) {
                list = parseCandidateArray(inputStream)
            }
        }

        try {
            list = list?.filter { it.constituency!= "Click" }?.toList() as ArrayList<Candidate>?
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list?: ArrayList()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun parseJsonLenient(inputStream: InputStream): ArrayList<Candidate>? {
         return try {
            val listType = object : TypeToken<ArrayList<Candidate>>() {}.type
            val lenientGson: Gson = GsonBuilder()
                .setLenient()
                .create()
              lenientGson.fromJson(InputStreamReader(inputStream), listType)
        }  catch (e: Exception){
            null
        }
    }

    private fun parseJson(inputStream: InputStream): ArrayList<Candidate>? {
        return try {
            val listType = object : TypeToken<ArrayList<Candidate>>() {}.type
            val gson: Gson = GsonBuilder()
                .create()
           gson.fromJson(InputStreamReader(inputStream), listType)
        }  catch (e: Exception){
            null
        }
    }

    private fun parseCandidateArray(inputStream: InputStream): ArrayList<Candidate> {
        var candidateList = ArrayList<Candidate>()
        try {
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.readText()
            val jsonArray = JSONArray(jsonString)
            // Iterate through the JSON array and create Candidate objects
            for (i in 0 until jsonArray.length()) {
                val candidateObject = jsonArray.getJSONObject(i)

                val candidate = Candidate(
                    name = candidateObject.getString("name"),
                    location = candidateObject.getString("location"),
                    constituency = candidateObject.getString("constituency"),
                    image = candidateObject.optString("image", null),
                    urduName = candidateObject.getString("urduName"),
                    symbolName = candidateObject.optString("symbolName", null),
                    symbolFile = candidateObject.optString("symbolFile", null),
                    whatsApp = candidateObject.optString("whatsApp", null),
                    featured = candidateObject.optBoolean("featured", false),
                    result = candidateObject.optInt("result", 0)
                )
                candidateList.add(candidate)
            }
        }
        catch (e: Exception){
        }

        return candidateList
    }


    private fun fetchRawVersion(context: Context): Int {
        val inputStream = context.resources.openRawResource(R.raw.command)
        return inputStream.bufferedReader().use { it.readText().toInt() }
    }

    fun loadData(context: Context) {

        deleteCacheIfOlder(context)
        loadFromSource(context)

        buildFiltermap()

        na = readSubsetByConstituencyType("NA-")
        pb = readSubsetByConstituencyType("PB-")
        pk = readSubsetByConstituencyType("PK-")
        pp = readSubsetByConstituencyType("PP-")
        ps = readSubsetByConstituencyType("PS-")

        Log.d(TAG, "Building candidate symbol map.")

        addToSymbolHashMap()

        Log.d(TAG, "All candidates loaded.")

        // getCandidateWithNoImage()

    }

    private fun readSubsetByConstituencyType(input: String): List<Candidate> {
        return try {
             allCandidates.filter { it.constituency.contains(input) }
        } catch (e: Exception) {
            //FirebaseCrashlytics.getInstance().recordException(e)
            ArrayList()
        }
    }

    private fun buildFiltermap() {
        allCandidates.forEach {
            if (it.constituency.isNotEmpty()) {
                filterMap[it.constituency] = it
                if (it.constituency.contains("-")) {
                    filterMap[it.constituency.replace("-", "")] = it
                }
            }
        }
    }

    private fun loadFromSource(context: Context) {
        if (dataManager?.areAllFilesCached() == true) {
            Log.d(TAG, "Loading from cache.")
            source = "c"
            loadFromCache()
        } else {
            source = "r"
            Log.d(TAG, "Loading from raw.")
            loadFromRaw(context)
        }
    }

    private fun deleteCacheIfOlder(context: Context) {
        val rawVersion = fetchRawVersion(context)
        if (rawVersion > dataManager?.getVersion() ?: 0) {
            Log.d(TAG, "Raw files are newer than cached files. Deleting cached files.")
            dataManager?.deleteAllCachedFiles(rawVersion)
        }
    }

    private fun getInputStreamForFile(context: Context, fileName: String, rawResId: Int): InputStream {
        var inputStream = dataManager!!.readCachedFileByName(fileName)
        if (inputStream == null) {
            Log.d(TAG, "buildCandidateSymbolMap: from raw. $fileName")
            inputStream = context.resources.openRawResource(rawResId)
        } else {
            Log.d(TAG, "buildCandidateSymbolMap: from cache. $fileName")
        }
        return inputStream
    }

    private fun loadFromCache() {
        dataManager?.let { dat ->
            if (dat.areAllFilesCached()) {
                dat.readCachedFileByName("all1.json")?.let {
                    allCandidates = loadFromJson(it)
                }
            }
        }
    }

    private fun loadFromRaw(context: Context) {
        context.resources.openRawResource(R.raw.all1)?.let {
            allCandidates = loadFromJson(it)
        }
    }


    fun getNaImageName(constituency: String): String? {
        return hashMap?.get(constituency)?.symbolFile?.let { imagekey ->
            if (imagekey.isNullOrEmpty()) {
                return null
            }
            return "$imagekey"
        }
    }

    fun getNaSymbolName(constituency: String): String? {
        return hashMap?.get(constituency)?.symbolName?.let { symbol ->
            if (symbol.isNotEmpty()) {
                symbol.replace("\\n\\n", " ")
                    .replace("\\n", " ")
            } else {
                null
            }
        }
    }

    fun getWhatsApp(constituency: String): String? {
        return hashMap?.get(constituency)?.whatsApp?.let { symbol ->
            if (symbol.isNotEmpty()) {
                symbol
            } else {
                null
            }
        }
    }

    fun getCandidatesInCity(city: String): ArrayList<Candidate> {
        val candidates = arrayListOf<Candidate>()
        allCandidates.forEach {
            if (it.location.contains(city)) {
                candidates.add(it)
            }
        }
        return candidates
    }

    fun getFeaturedCandidates(): ArrayList<Candidate> {
        val candidates = arrayListOf<Candidate>()
        allCandidates.filter { it.featured }?.forEach() {
            candidates.add(it)
        }
        return candidates
    }

    fun getCandidatesByHalqas(halqas: List<String>): ArrayList<Candidate> {
        val candidates = arrayListOf<Candidate>()
        allCandidates.forEach {
            if (it.constituency in halqas) {
                candidates.add(it)
            }
        }
        return candidates
    }

    fun getCandidateWithPending(): ArrayList<Candidate> {
        val candidates = arrayListOf<Candidate>()
        allCandidates.forEach {
            if (it.name.contains("pending", true)) {
                candidates.add(it)
            }
        }
        return candidates
    }

    private fun getCandidateWithNoImage(){
        allCandidates.forEach {
            if (it.symbolFile.isNullOrEmpty() && !it.name.contains("pending", true)) {
                Log.d("NoImage", "No Symbol for File: ${it.constituency}")
            }
        }
    }
}
