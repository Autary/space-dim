package com.example.spacedim.sharedViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.*
import okio.ByteString
import java.lang.Exception

open class WsViewModel : ViewModel() {



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
        listener = EchoWebSocketListener();
        ws = client.newWebSocket(request, listener)



    }





    private class EchoWebSocketListener() : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response?) {
           // Log.i(this.javaClass.name, "open")
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
           // Log.i(this.javaClass.name, "Receiving bytes : " + bytes.hex())
        }
        override fun onMessage(webSocket: WebSocket?, str: String) {
            Log.i(this.javaClass.name, "Receiving : $str")

            try {
                val response = PolymoObject.adapterSpace.fromJson(str)
                Log.i(this.javaClass.name, response.toString())
            }catch (exeption: Exception){
                Log.i(this.javaClass.name, exeption.toString())
            }catch (exception: Throwable){
                Log.e(this.javaClass.name, exception.toString())
            }


        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            //Log.i(this.javaClass.name, "Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            //Log.i(this.javaClass.name, "Error : " + t.message)
        }

        companion object {
            private const val NORMAL_CLOSURE_STATUS = 1000
        }

    }
}