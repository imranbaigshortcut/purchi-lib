package idea.pti.insaf.purchi.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val id: Int,
    val name: String,
    val email: String
)

@Parcelize
data class Voter(
    val index: String,
    val blockCode: String,
    val cnic: String,
    val family: String,
    val national: String,
    val provincial: String,
    val name: String,
    val fatherName: String,
    val pollingStationAddress: String
) : Parcelable {

     fun toShare1(): String {
        return """
            $name نام 
            $fatherName والد کا نام 
            $index   سلسلۂ نمبر 
            $blockCode بلاک کوڈ 
            $cnic شناختی کارڈ نمبر 
            $family  خاندان نمبر 
            $national قومی اسمبلی 
            $provincial صوبائی اسمبلی 
            $pollingStationAddress پولنگ اسٹیشن کا پتہ
        """.trimIndent()
    }


    fun toShare(): String {
        return """
         شناختی کارڈ نمبر:$cnic
        نام: $name
        والد کا نام: $fatherName
        سلسلۂ نمبر: $index
        بلاک کوڈ: $blockCode
        خاندان نمبر: $family
        قومی اسمبلی: $national
        صوبائی اسمبلی: $provincial
        پولنگ اسٹیشن کا پتہ: $pollingStationAddress
    """.trimIndent()
    }
}