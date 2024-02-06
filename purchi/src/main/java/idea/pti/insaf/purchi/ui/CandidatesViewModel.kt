package candidates.insaf.pk.list.ui.candidate

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import idea.pti.insaf.purchi.api.ApiResult
import idea.pti.insaf.purchi.api.Voter1
import idea.pti.insaf.purchi.list.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CandidatesViewModel : ViewModel() {

    sealed class UpdateResult {
        object Completed : UpdateResult()
        data class Failed(val errorMessage: String) : UpdateResult()
    }
    private val _updateResult = MutableLiveData<UpdateResult>()
    val updateResult: LiveData<UpdateResult>
        get() = _updateResult

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun load(context: Context) {
        Repo.initialize(context)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Repo.loadData(context.applicationContext)
                _updateResult.postValue(UpdateResult.Completed)
            }
        }
    }
}
