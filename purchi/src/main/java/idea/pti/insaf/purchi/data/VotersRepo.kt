package idea.pti.insaf.purchi.data

import idea.pti.insaf.purchi.api.ApiClient
import idea.pti.insaf.purchi.api.ApiResult
import idea.pti.insaf.purchi.api.PurchiResponse
import idea.pti.insaf.purchi.api.Voter

class VotersRepo {
    private val api: ApiClient = ApiClient()
    suspend fun getVoters(id: String): ApiResult<List<Voter>> {
        // return ApiResult.Success(FakeDataGenerator.generateFakeData())
        return api.getVoters(id)
    }

    suspend fun getVoter(id: String): ApiResult<PurchiResponse> {
        // return ApiResult.Success(FakeDataGenerator.generateFakeData())

        val result = api.getVoter(id)

        return result
    }
}