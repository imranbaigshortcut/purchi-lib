package idea.pti.insaf.purchi.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("voterlist/{id}")
    fun getVoters(@Path("id") id: String): Call<List<Voter>>
}