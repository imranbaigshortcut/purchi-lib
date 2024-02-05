package idea.pti.insaf.purchi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import idea.pti.insaf.purchi.api.ApiResult
import idea.pti.insaf.purchi.api.Voter
import idea.pti.insaf.purchi.data.VotersRepo
import kotlinx.coroutines.launch

class PurchiListViewModel: ViewModel() {

    private val votersRepo = VotersRepo()
    private val _voters = MutableLiveData<List<Voter>>()
    val voters: LiveData<List<Voter>>
        get() = _voters


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getVotersById(id: String) {
        _loading.value = true
        this.viewModelScope.launch {
            val result = votersRepo.getVoters(id)
            if (result is ApiResult.Success) {
                _voters.postValue(result.data?: arrayListOf())
            } else if (result is ApiResult.Error) {
                _voters.postValue(arrayListOf())
            }
            _loading.value = false
        }
    }
}