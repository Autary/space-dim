package com.example.spacedim.sharedViewModel

import androidx.lifecycle.ViewModel
import com.example.spacedim.Event
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory

class PolymoViewModel: ViewModel() {
    var factory = PolymorphicJsonAdapterFactory.of(Event::class.java,"type")
        .withSubtype(Event.NextAction::class.java, "NEXT_ACTION")
        .withSubtype(Event.GameStarted::class.java, "GAME_STARTED")
        .withSubtype(Event.GameOver::class.java, "GAME_OVER")
        .withSubtype(Event.NextLevel::class.java, "NEXT_LEVEL")
        .withSubtype(Event.WaitingForPlayer::class.java, "WAITING_FOR_PLAYER")
        .withSubtype(Event.Error::class.java, "ERROR")
        .withSubtype(Event.Ready::class.java, "READY")
        .withSubtype(Event.PlayerAction::class.java, "PLAYER_ACTION")

    val moshi = Moshi.Builder()
        .add(factory)
        .build()
}