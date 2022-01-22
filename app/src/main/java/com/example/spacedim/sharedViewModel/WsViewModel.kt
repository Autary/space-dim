package com.example.spacedim.sharedViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.*
import okio.ByteString
import java.lang.Exception
import com.example.spacedim.classes.Event

class WsViewModel : ViewModel() {
    private val client = OkHttpClient();
    lateinit var ws: WebSocket;
    lateinit var listener: EchoWebSocketListener;
    private lateinit var request: Request;
    var room = "";


    fun createWS(roomName: String, idUser: Int) {
        request = Request.Builder()
            .url("ws://spacedim.async-agency.com:8081/ws/join/" + roomName + "/" + idUser)
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

    private val _eventMessage = MutableLiveData<Event>()
    val eventMessage: LiveData<Event>
        get() = _eventMessage

    private val _eventGameStarted = MutableLiveData<Event.GameStarted>()
    val eventGameStarted: LiveData<Event.GameStarted>
        get() = _eventGameStarted

    private val _eventNextAction = MutableLiveData<Event.NextAction>()
    val eventNextAction: LiveData<Event.NextAction>
        get() = _eventNextAction


    private val _eventGameOver = MutableLiveData<Event.GameOver>()
    val eventGameOver: LiveData<Event.GameOver>
        get() = _eventGameOver

    private val _eventNextLevel = MutableLiveData<Event.NextLevel>()
    val eventNextLevel: LiveData<Event.NextLevel>
        get() = _eventNextLevel


    override fun onOpen(webSocket: WebSocket, response: Response?) {
       // Log.i(this.javaClass.name, "open")
        _state.postValue(true);
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
        // Log.i(this.javaClass.name, "Receiving bytes : " + bytes.hex())
    }

    override fun onMessage(webSocket: WebSocket?, str: String) {
        Log.i("TESTEEEEE", "Receiving : $str")

        try {
            val response = PolymoObject.adapterSpace.fromJson(str)

            response?.let {
                _eventMessage.postValue(it)
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
                    _eventGameOver.postValue(response)
                }
                if (response is Event.NextLevel) {
                    _eventNextLevel.postValue(response)
                }
            }

            Log.i(this.javaClass.name, response.toString())
        } catch (exeption: Exception) {
            Log.i(this.javaClass.name, exeption.toString())
        } catch (exception: Throwable) {
            Log.e(this.javaClass.name, exception.toString())
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        //Log.i(this.javaClass.name, "Closing : $code / $reason")
        _state.postValue(false);
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
        //Log.i(this.javaClass.name, "Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

}
