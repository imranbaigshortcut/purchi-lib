package idea.pti.insaf.purchi.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("search/cnic/")
    fun getVoters(@Body request: PurchiRequest): Call<List<Voter>>


    @POST("search/cnic/constituency")
    fun getVoter(@Body request: PurchiRequest): Call<PurchiResponse>
}