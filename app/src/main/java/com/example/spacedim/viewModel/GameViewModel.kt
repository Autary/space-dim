package com.example.spacedim.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.classes.UIElement
import com.example.spacedim.classes.UIType
import com.example.spacedim.classes.Action
import timber.log.Timber

class GameViewModel : ViewModel() {
    /* view model used to debug ONLY*/
    var uiElements = MutableLiveData<List<UIElement>>()
    var timer = MutableLiveData<Int>()
    var currentAction = MutableLiveData<Action>()

    private lateinit var action: Action
    private lateinit var elementsList: List<UIElement>

    /* private fun getAction() : Action{
         action = Action("Shake your phone ",UIElement(3, UIType.SHAKE,"") )
         return action
     }*/

    // list of UIElements given by the server
    private fun getUIElements(): List<UIElement> {
        elementsList = listOf(
            UIElement(3, UIType.BUTTON, "booster"),
            UIElement(5, UIType.SWITCH, "filter"),
            UIElement(4, UIType.SWITCH, "pressure"),
            UIElement(7, UIType.SWITCH, "light"),
            UIElement(6, UIType.BUTTON, "eggs"),
            UIElement(4, UIType.SWITCH, "pressure"),
            UIElement(7, UIType.SWITCH, "light"),
            UIElement(6, UIType.BUTTON, "eggs"),
            UIElement(4, UIType.SWITCH, "pressure"),
            UIElement(7, UIType.SWITCH, "light"),
            UIElement(6, UIType.BUTTON, "eggs"),            UIElement(4, UIType.SWITCH, "pressure"),
            UIElement(7, UIType.SWITCH, "light"),
            UIElement(6, UIType.BUTTON, "eggs"),
        )
        return elementsList
    }


    init {
        //uiElements.value = getUIElements()
        ///timer.value = updateTimer()
        //currentAction.value = getAction()

        Timber.i("GameViewModel created!")

    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("GameViewModel destroyed!")
    }
}