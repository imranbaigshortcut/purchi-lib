package idea.pti.insaf.purchi.data

import idea.pti.insaf.purchi.api.ApiClient
import idea.pti.insaf.purchi.api.ApiResult
import idea.pti.insaf.purchi.api.Voter

class VotersRepo {
    private val api: ApiClient = ApiClient()
    suspend fun getVoters(id: String): ApiResult<List<Voter>> {
        return ApiResult.Success(FakeDataGenerator.generateFakeData())
        // return api.getVoters(id)
    }
}