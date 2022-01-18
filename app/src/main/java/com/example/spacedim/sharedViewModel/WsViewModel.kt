package com.example.spacedim.sharedViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacedim.interfaces.MessageListener
import okhttp3.*
import okio.ByteString

class WsViewModel : ViewModel(), MessageListener {
    private val client = OkHttpClient();
    lateinit var ws: WebSocket;
    private lateinit var listener: EchoWebSocketListener;
    private lateinit var request: Request;

    private val _eventMessage = MutableLiveData<String>()
    val eventMessage: LiveData<String>
        get() = _eventMessage

    fun createWS(roomName:String, idUser:Int) {
        request = Request.Builder()
                .url("ws://spacedim.async-agency.com:8081/ws/join/"+roomName+"/"+idUser)
                .build()
        listener = EchoWebSocketListener(this);
        ws = client.newWebSocket(request, listener)



    }


    override fun onMessage(text: String) {
        _eventMessage.postValue(text)
    }


    private class EchoWebSocketListener(val messageListener: MessageListener) : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response?) {
            Log.i(this.javaClass.name, "open")
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            Log.i(this.javaClass.name, "Receiving bytes : " + bytes.hex())
        }
        override fun onMessage(webSocket: WebSocket?, str: String) {
            Log.i(this.javaClass.name, "Receiving : $str")
            messageListener.onMessage(str)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            Log.i(this.javaClass.name, "Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            Log.i(this.javaClass.name, "Error : " + t.message)
        }

        companion object {
            private const val NORMAL_CLOSURE_STATUS = 1000
        }

    }
}