package com.example.spacedim.sharedViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.classes.Event

class WinLooseViewModel : ViewModel() {

    var endEvent: MutableLiveData<Event.GameOver> = MutableLiveData<Event.GameOver>()

}
