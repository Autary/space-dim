package com.example.spacedim.sharedViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.classes.User
import com.example.spacedim.classes.UserCreate
import retrofit2.Callback
import com.example.spacedim.network.SpaceDimApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class HttpViewModel : ViewModel() {

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val adapter: JsonAdapter<User> = moshi.adapter(User::class.java)

    private val type = Types.newParameterizedType(List::class.java, User::class.java)
    val adapterList: JsonAdapter<List<User>> = moshi.adapter(type)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _eventGoToCreateRoom = MutableLiveData<Boolean>()
    val eventGoToCreateRoom: LiveData<Boolean>
        get() = _eventGoToCreateRoom

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    //Exécute la fonction afin d'appeler la requête pour récuperer tout les utilisateurs
    fun getAllUsers() {
        SpaceDimApi.retrofitService.getAllUsers().enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _userList.value = adapterList.fromJson(response.body()!!)
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
                    val user = adapter.fromJson(response.body()!!)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Timber.i("Fail get user")
                }
            })
    }

    //Exécute la fonction afin d'appeler la requête pour récupérer le json de lié à l'utilisateur via le nom
    fun getPlayerByName(name: String) {
        SpaceDimApi.retrofitService.getPlayerByname(name).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _user.value = adapter.fromJson(response.body()!!)
                    _eventGoToCreateRoom.value = true
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Timber.i("Fail get user by name")
                }
            })
    }

    //Exécute la fonction afin d'appeler la requête pour inserer un utilisateur
    fun addUser(name: String) {

        val userCreate = UserCreate(name)
        val createAdapter: JsonAdapter<UserCreate> = moshi.adapter(UserCreate::class.java)
        val data = createAdapter.toJson(userCreate)

        SpaceDimApi.retrofitService.addUser(data).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.body() == null) {
                        getPlayerByName(userCreate.name)
                    } else {
                        _user.value = adapter.fromJson(response.body()!!)
                        _eventGoToCreateRoom.value = true
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Timber.i(" Fail :" + t.message)
                }
            }
        )
    }
}