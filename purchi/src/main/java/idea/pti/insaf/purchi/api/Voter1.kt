package idea.pti.insaf.purchi.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class Voter1 (

  @SerializedName("id"        ) var id        : Int?    = null,
  @SerializedName("cnic"      ) var cnic      : String? = null,
  @SerializedName("age"       ) var age       : String? = null,
  @SerializedName("silsilaNo" ) var silsilaNo : String? = null,
  @SerializedName("gharanaNo" ) var gharanaNo : String? = null,
  @SerializedName("gender"    ) var gender    : String? = null,
  @SerializedName("blockCode" ) var blockCode : String? = null,
  @SerializedName("psNo"      ) var psNo      : String? = null,
  @SerializedName("psName"    ) var psName    : String? = null,
  @SerializedName("uc"        ) var uc        : String? = null,
  @SerializedName("pa"        ) var pa        : String? = null,
  @SerializedName("na"        ) var na        : String? = null,
  @SerializedName("distt"     ) var distt     : String? = null,

) {

  fun toVoter(response: PurchiResponse): Voter {
    return Voter(
      index = silsilaNo?:"",
      blockCode= blockCode?:"",
      cnic= cnic?: "",
      family= gharanaNo?:"",
      national= na?:"",
      provincial= pa?:"",
      name = "",
      fatherName= "",
      pollingStationAddress= "$psName, $uc, $distt",
      candidateNa = response.na,
      candidatePa = response.pa,
    )
  }
}

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
  val pollingStationAddress: String,
  var candidateNa: Na? = null,
  var candidatePa: Pa? = null
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
        سلسلۂ نمبر: $index
        بلاک کوڈ: $blockCode
        خاندان نمبر: $family
        قومی اسمبلی: $national
        صوبائی اسمبلی: $provincial
        پولنگ اسٹیشن کا پتہ: $pollingStationAddress
    """.trimIndent()
  }

}
