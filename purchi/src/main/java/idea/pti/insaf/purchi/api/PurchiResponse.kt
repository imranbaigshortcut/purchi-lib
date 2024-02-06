package idea.pti.insaf.purchi.api

import com.google.gson.annotations.SerializedName


data class PurchiResponse (

  @SerializedName("voter" ) var voter : Voter1? = Voter1(),
  @SerializedName("na"    ) var na    : Na?    = Na(),
  @SerializedName("pa"    ) var pa    : Pa?    = Pa()

)