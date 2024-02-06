package idea.pti.insaf.purchi

import android.content.Context
import idea.pti.insaf.purchi.list.Repo

object PurchiLib {
    var secret : String = ""
    var baseUrl : String = ""

    fun initFormFeature(context: Context) {
        Repo.initialize(context)
    }
}