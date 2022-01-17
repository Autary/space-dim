package com.example.retrofit.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.User
import com.example.spacedim.UserCreate
import retrofit2.Callback
import com.example.retrofit.network.SpaceDimApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response

class HttpViewModel : ViewModel() {

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val adapter: JsonAdapter<User> = moshi.adapter(User::class.java)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        //getAllUsers()
        //postSpaceDimRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    //Exécute la fonction afin d'appeler la requête pour récuperer tout les utilisateurs
    private fun getAllUsers() {
        SpaceDimApi.retrofitService.getAllUsers().enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })
    }

    //Exécute la fonction afin d'appeler la requête pour récuperer toutes les rooms
    private fun getAllRoom() {
        SpaceDimApi.retrofitService.getAllRoom().enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })
    }

    //Exécute la fonction afin d'appeler la requête pour récupérer le json de lié à l'utilisateur via l'id
    fun getPlayerById(id: Int) {
        SpaceDimApi.retrofitService.getPlayerById(id).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val user = adapter.fromJson(response.body())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i(this.javaClass.name, "Fail get user")
                }
            })
    }

    //Exécute la fonction afin d'appeler la requête pour récupérer le json de lié à l'utilisateur via le nom
    fun getPlayerByname(name: String) {
        SpaceDimApi.retrofitService.getPlayerByname(name).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _user.value = adapter.fromJson(response.body())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i(this.javaClass.name, "Fail get user by name")
                }
            })
    }

    //Exécute la fonction afin d'appeler la requête pour inserer un utilisateur
    fun addUser(name: String) {

        var userCreate = UserCreate(name)
        val createAdapter: JsonAdapter<UserCreate> = moshi.adapter(UserCreate::class.java)
        val data = createAdapter.toJson(userCreate)


        SpaceDimApi.retrofitService.addUser(data).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.body() == null) {
                        getPlayerByname(userCreate.name)
                    } else {
                        _user.value = adapter.fromJson(response.body())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i(this.javaClass.name, " Fail :" + t.message)
                }
            }
        )

    }
}