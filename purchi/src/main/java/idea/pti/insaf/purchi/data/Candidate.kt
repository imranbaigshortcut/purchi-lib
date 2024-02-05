package idea.pti.insaf.purchi.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class Candidate(
    val name: String,
    val location: String,
    val constituency: String,
    val image: String?,
    val urduName: String,
    var symbolName: String? = null,
    var symbolFile: String? = null,
    var whatsApp: String? = null,
    var featured: Boolean = false,
    var result: Int = 0,
) : Parcelable {
    fun constituencyMatch(token: String): Boolean {
        return if (constituency.isNullOrEmpty().not()) {
            val simple = constituency.replace("-", "")
            simple.compareTo(token, ignoreCase = true) == 0 || constituency.compareTo(token, ignoreCase = true) == 0
        } else {
            false
        }
    }

    val constituencyNumber: Int
        get() {
            try {
                val regex = Regex("[0-9]+")
                val matches = regex.findAll(constituency)

                val result = matches.map { it.value }.joinToString(separator = "")
                return result.toInt()
            }
            catch (e: Exception){
            }
            return 0
        }

    companion object {
        val ConstituencyNumberComparator: Comparator<Candidate> = compareBy { it.constituencyNumber }
    }
}
