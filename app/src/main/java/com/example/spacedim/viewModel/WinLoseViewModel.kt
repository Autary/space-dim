package com.example.spacedim.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.classes.Event

class WinLoseViewModel : ViewModel() {

    var score: MutableLiveData<Event.GameOver> = MutableLiveData<Event.GameOver>()
    fun getItem(): Event.GameOver? {
        return score.getValue()
    }

    fun setItem(item: Event.GameOver) {
        this.score.setValue(item)
    }


}
