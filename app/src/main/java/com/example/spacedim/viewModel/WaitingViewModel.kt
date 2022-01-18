package com.example.spacedim.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.State
import com.example.spacedim.User
import com.example.spacedim.classes.UIElement

class WaitingViewModel : ViewModel() {

    var users = MutableLiveData<List<User>>()
    private lateinit var usersList: List<User>

    private fun getUsersList() : List<User>{
        usersList = listOf(
            User(1, "henri", "henri.png", 0, State.WAITING),
            User(2, "Gotié", "gotie.png", 0, State.WAITING),
            User(3, "Emma", "emma.png", 0, State.READY),
            User(4, "Morgane", "morgane.png", 0, State.READY),
            User(5, "Nico", "nico.png", 0, State.WAITING)
        )
        return usersList
    }

    init {
        users.value = getUsersList()
        Log.i("WaitingViewModel", "WaitingViewModel created!")

    }
}