package idea.pti.insaf.purchi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import idea.pti.insaf.purchi.api.ApiResult
import idea.pti.insaf.purchi.api.Voter
import idea.pti.insaf.purchi.api.Voter1
import idea.pti.insaf.purchi.data.VotersRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PurchiListViewModel: ViewModel() {

    private val votersRepo = VotersRepo()
    private val _voters = MutableLiveData<List<Voter>>()
    val voters: LiveData<List<Voter>>
        get() = _voters


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getVoterById(id: String) {
        _loading.value = true
        viewModelScope.launch {
            val result=  withContext(Dispatchers.IO) {
                votersRepo.getVoter(id)
            }

            if (result is ApiResult.Success) {
                val result = result.data
                val voter = result?.voter?: Voter1()
                _voters.postValue(arrayListOf(voter.toVoter(result)))
            } else if (result is ApiResult.Error) {
                _voters.postValue(arrayListOf())
            }
            _loading.value = false
        }

    }
}