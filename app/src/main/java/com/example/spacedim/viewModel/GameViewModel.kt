package com.example.spacedim.viewModel

import android.R.attr
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.spacedim.*
import com.example.spacedim.Class.UIElement
import com.example.spacedim.game.Action

class GameViewModel : ViewModel() {

    // The current action
    var action = ""
    // The current score
    var score = 0
    // The list of actions - the front of the list is the next action
    private lateinit var actionList: MutableList<Action>
    private lateinit var elementsList: MutableList<UIElement>


    private fun getList() {
        actionList = mutableListOf(
            Action("Turn on the ",UIElement.Switch(3, "booster") ),
            Action("Turn off the ",UIElement.Switch(5, "filter") ),
            Action("Click the ",UIElement.Button(4, "pressure") ),
            Action("Turn off the ",UIElement.Switch(7, "light") ),
            Action("Shake ",UIElement.Shake(5, "eggs") ),
        )
    }

    fun getElements(): MutableList<UIElement> {
        elementsList = mutableListOf(
            UIElement.Switch(3, "booster"),
            UIElement.Switch(5, "filter") ,
            UIElement.Button(4, "pressure"),
            UIElement.Switch(7, "light"),
            UIElement.Shake(5, "eggs"),
        )
        return elementsList
    }

    // UIElements given by the server
    fun dashboardElementsList(){
        elementsList = mutableListOf(
            UIElement.Switch(3, "booster"),
            UIElement.Switch(5, "filter") ,
            UIElement.Button(4, "pressure"),
            UIElement.Switch(7, "light"),
            UIElement.Shake(5, "eggs"),
        )


    }

    init {
        dashboardElementsList()
        //getList()
        Log.i("GameViewModel", "GameViewModel created!")

    }



    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    fun addFragmentToFragment(manager: FragmentManager, fragment: Fragment?, frameId: Int) {
        val transaction: FragmentTransaction = manager.beginTransaction()
        if (fragment != null) {
            transaction.add(frameId, fragment)
        }
        transaction.commit()
    }
}