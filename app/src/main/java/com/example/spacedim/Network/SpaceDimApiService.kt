package com.example.retrofit.network



import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://spacedim.async-agency.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface SpaceDimApiService {

    //Permets de récupérer tous les utilisateurs
    @GET("api/users")
    fun getAllUsers():
            Call<String>

    //Permets de récupérer un utilisateur via l'id
    @GET("api/user/{id}")
    fun getPlayerById(@Path("id") id: Int):
            Call<String>

    //Permets de récupérer un utilisateur via le pseudo
    @GET("api/user/find/{name}")
    fun getPlayerByname(@Path("name") name: String):
            Call<String>

    //Crée un utilisateur
    @Headers("Content-Type:application/json")
    @POST("api/user/register")
    fun addUser(@Body user: String):
            Call<String>


}

object SpaceDimApi {
    val retrofitService: SpaceDimApiService by lazy {
        retrofit.create(SpaceDimApiService::class.java)
    }
}