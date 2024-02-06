package idea.pti.insaf.purchi.api

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Keep
data class Na (
  @SerializedName("cand_id"         ) var candId         : String? = null,
  @SerializedName("cand_name"       ) var candName       : String? = null,
  @SerializedName("cn_name"         ) var cnName         : String? = null,
  @SerializedName("cn_number"       ) var cnNumber       : String? = null,
  @SerializedName("symbol"          ) var symbol         : String? = null,
  @SerializedName("symbol_url"      ) var symbolUrl      : String? = null,
  @SerializedName("whatsapp_link"   ) var whatsappLink   : String? = null,
  @SerializedName("whatsapp_number" ) var whatsappNumber : String? = null,
  @SerializedName("admin_name"      ) var adminName      : String? = null,
  @SerializedName("symbolfile"      ) var symbolfile     : String? = null

): Parcelable {
  fun toShare(): String {
    return """
            $candName نام 
            $cnName علامت 
            $cnNumber علامت نمبر 
            $adminName ایڈمن کا نام 
            $whatsappNumber واٹس ایپ نمبر 
            $whatsappLink واٹس ایپ لنک 
        """.trimIndent()
  }
}