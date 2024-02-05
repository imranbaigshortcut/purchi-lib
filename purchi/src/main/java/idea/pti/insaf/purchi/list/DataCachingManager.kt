package idea.pti.insaf.purchi.list

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class DataCachingManager(private val context: Context) {

    companion object {
        const val TAG = "DataCachingManager"
        private const val BASE_URL = "https://tqsource.s3.eu-central-1.amazonaws.com/extra"
        // private const val BASE_URL = "https://dx6wxg1sm9jm0.cloudfront.net/extra"
    }

    private val defaultVersion = 0
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("CachingPreferences", Context.MODE_PRIVATE)
    }

    fun getVersion(): Int {
        return sharedPreferences.getInt("version", defaultVersion)
    }
    private fun shouldUpdateData(): Boolean {
        val localVersion = sharedPreferences.getInt("version", defaultVersion)
        val remoteVersion = fetchRemoteVersion()

        Log.d(TAG, "shouldUpdateData: local v $localVersion")
        Log.d(TAG, "shouldUpdateData: remote v $remoteVersion")

        val shouldUpdate = localVersion < remoteVersion

        Log.d(TAG, "shouldUpdateData: $shouldUpdate")

        return shouldUpdate
    }

    fun updateData() {
        if (!shouldUpdateData()) {
            return
        }

        val remoteFiles = listOf(
            "$BASE_URL/all1.json",
        )

        for (remoteFile in remoteFiles) {
            downloadAndCacheFile(remoteFile)
        }

        // Update the local version
        val remoteVersion = fetchRemoteVersion()
        sharedPreferences.edit().putInt("version", remoteVersion).apply()
        Log.d(TAG, "updateData: updated version to $remoteVersion")
    }

    private fun downloadAndCacheFile(url: String) {
        val fileName = url.substringAfterLast("/")
        val file = File(context.cacheDir, fileName)

        try {
            // For simplicity, using Fuel library for HTTP requests
            val (_, _, result) = Fuel.download(url)
                .destination { _, _ -> file }
                .response()

            if (result is com.github.kittinunf.result.Result.Failure) {
                // Handle download failure
                Log.e(TAG, "Failed to download file: $url. Error: ${result.error}")
            } else {
                Log.d(TAG, "downloadAndCacheFile: $url")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception while downloading file: $url. Error: ${e.message}")
        }
    }

    private fun fetchRemoteVersion(): Int {
        val versionUrl = "$BASE_URL/command.vat"

        try {
            // For simplicity, using Fuel library for HTTP requests
            val (_, _, result) = versionUrl.httpGet().responseString()

            if (result is com.github.kittinunf.result.Result.Success) {
                return result.value.toIntOrNull() ?: 0
            } else {
                // Handle fetch failure
                println("Failed to fetch remote version. Error: ${(result as com.github.kittinunf.result.Result.Failure).error}")
            }
        } catch (e: Exception) {
            // Handle general exceptions
            println("Exception while fetching remote version. Error: ${e.message}")
        }

        return 0
    }

    fun readCachedFileByName(fileName: String): InputStream? {
        val file = File(context.cacheDir, fileName)

        return if (file.exists()) {
            FileInputStream(file)
        } else {
            null
        }
    }

    fun areAllFilesCached(): Boolean {
        val remoteFiles = listOf(
            "all1.json",
        )

        return remoteFiles.all { fileName ->
            File(context.cacheDir, fileName).exists()
        }
    }

    fun deleteAllCachedFiles(rawVersion: Int) {
        val cacheDir = context.cacheDir
        val cachedFiles = cacheDir.listFiles()

        cachedFiles?.forEach { file ->
            file.delete()
        }

        sharedPreferences.edit().putInt("version", rawVersion).commit()
    }
}
