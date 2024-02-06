package idea.pti.insaf.purchi.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val id: Int,
    val name: String,
    val email: String
)


data class PurchiRequest(val secret: String, val cnic: String)