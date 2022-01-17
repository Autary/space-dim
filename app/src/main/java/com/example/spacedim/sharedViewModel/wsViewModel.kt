package com.example.spacedim.sharedViewModel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import okhttp3.*
import okio.ByteString

class wsViewModel : ViewModel() {
    val client = OkHttpClient();
    private lateinit var ws: WebSocket;
    private lateinit var listener: EchoWebSocketListener;
    private lateinit var request: Request;

    fun createWS() {
        request = Request.Builder()
                .url("ws://spacedim.async-agency.com:8081/ws/")
                .build()
        listener = EchoWebSocketListener();
        ws = client.newWebSocket(request, listener)
    }


    private class EchoWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response?) {
            Log.i(this.javaClass.name, "open")
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            Log.i(this.javaClass.name, "Receiving bytes : " + bytes.hex())
        }
        override fun onMessage(webSocket: WebSocket?, str: String) {
            Log.i(this.javaClass.name, "Receiving : $str")
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