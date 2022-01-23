package com.example.spacedim.sharedViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.*
import okio.ByteString
import java.lang.Exception
import com.example.spacedim.classes.Event
import timber.log.Timber

class WsViewModel : ViewModel() {
    private val client = OkHttpClient();
    lateinit var ws: WebSocket;
    lateinit var listener: EchoWebSocketListener;
    private lateinit var request: Request;
    var room = "";

    fun createWS(roomName: String, idUser: Int) {
        request = Request.Builder()
            .url("ws://spacedim.async-agency.com:8081/ws/join/$roomName/$idUser")
            .build()
        room = roomName
        listener = EchoWebSocketListener()
        ws = client.newWebSocket(request, listener)
    }
}

class EchoWebSocketListener() : WebSocketListener() {

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean>
        get() = _state

    private val _eventGoToPlay = MutableLiveData<Boolean>()
    val eventGoToPlay: LiveData<Boolean>
        get() = _eventGoToPlay

    private val _eventGameStarted = MutableLiveData<Event.GameStarted>()
    val eventGameStarted: LiveData<Event.GameStarted>
        get() = _eventGameStarted

    private val _eventNextAction = MutableLiveData<Event.NextAction>()
    val eventNextAction: LiveData<Event.NextAction>
        get() = _eventNextAction

    private val _eventGameEnded = MutableLiveData<Event.GameOver>()
    val eventGameEnded: LiveData<Event.GameOver>
        get() = _eventGameEnded

    private val _eventNextLevel = MutableLiveData<Event.NextLevel>()
    val eventNextLevel: LiveData<Event.NextLevel>
        get() = _eventNextLevel

    private val _eventWaitingForPlayers = MutableLiveData<Event.WaitingForPlayer>()
    val eventWaitingForPlayers: LiveData<Event.WaitingForPlayer>
        get() = _eventWaitingForPlayers

    override fun onOpen(webSocket: WebSocket, response: Response?) {
       // Timber.i("open")
        _state.postValue(true);
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
        // Timber.i("Receiving bytes : " + bytes.hex())
    }

    override fun onMessage(webSocket: WebSocket?, str: String) {
        try {
            val response = PolymoObject.adapterSpace.fromJson(str)

            response?.let {
                if (response is Event.GameStarted) {
                    _eventGoToPlay.postValue(true)
                }
                if (response is Event.GameStarted) {
                    _eventGameStarted.postValue(response)
                }
                if (response is Event.NextAction) {
                    _eventNextAction.postValue(response)
                }
                if (response is Event.GameOver) {
                    _eventGameEnded.postValue(response)
                }
                if (response is Event.NextLevel) {
                    _eventNextLevel.postValue(response)
                }
                if(response is Event.WaitingForPlayer) {
                    _eventWaitingForPlayers.postValue(response)
                }
            }

            Timber.i(response.toString())
        } catch (exception: Exception) {
            Timber.i(exception.toString())
        } catch (exception: Throwable) {
            Timber.e(exception.toString())
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        Timber.i("WS closing : $code / $reason")
        _state.postValue(false);
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
        Timber.i("WS Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

}
