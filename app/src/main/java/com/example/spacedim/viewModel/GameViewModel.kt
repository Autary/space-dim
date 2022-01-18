package com.example.spacedim.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.classes.UIElement
import com.example.spacedim.classes.UIType
import com.example.spacedim.game.Action

class GameViewModel : ViewModel() {

    // The current action
    var action = ""
    // The current score
    var score = 0

    var uiElements = MutableLiveData<List<UIElement>>()
    var timer = MutableLiveData<Int>()

    private lateinit var actionList: MutableList<Action>
    private lateinit var elementsList: List<UIElement>


    // list of UIElements given by the server
  /*  private fun getUIElements(): List<UIElement> {
        elementsList = listOf(
            UIElement(3,UIType.SWITCH,"booster"),
            UIElement(5,UIType.SWITCH, "filter") ,
            UIElement(4,UIType.BUTTON ,"pressure"),
            UIElement(7, UIType.SWITCH,"light"),
            UIElement(6,UIType.SHAKE ,"eggs"),
        )
        return elementsList
    }*/

    private fun updateTimer() : Int{
        var time = 5000

        return time
    }

    init {
      //  uiElements.value = getUIElements()
        timer.value = updateTimer()

        Log.i("GameViewModel", "GameViewModel created!")

    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}