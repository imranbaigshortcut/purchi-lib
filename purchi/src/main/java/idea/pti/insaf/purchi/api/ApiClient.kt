package idea.pti.insaf.purchi.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(UserService::class.java)


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
            val response: Response<List<Voter>> = apiService.getVoters(id).execute()
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