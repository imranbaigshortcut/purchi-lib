package idea.pti.insaf.purchi.api

import idea.pti.insaf.purchi.PurchiLib
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    val retrofit = Retrofit.Builder()
        .baseUrl(PurchiLib.baseUrl) // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(UserService::class.java)

    val secret = PurchiLib.secret

    suspend fun getUsers(): ApiResult<List<User>>
    {
        return try {
            val response: Response<List<User>> = apiService.getUsers().execute()
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Exception occurred")
        }
    }


    suspend fun getVoters(id: String): ApiResult<List<Voter>>
    {
        return try {
            val response: Response<List<Voter>> = apiService.getVoters(PurchiRequest(secret, id)).execute()
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Exception occurred")
        }
    }

    suspend fun getVoter(id: String): ApiResult<PurchiResponse>
    {
        return try {
            val response: Response<PurchiResponse> = apiService.getVoter(PurchiRequest(secret, id)).execute()
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Exception occurred")
        }
    }
}