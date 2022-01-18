package com.example.spacedim.interfaces

import android.util.Log

interface LifeCycleLogs {
    fun onCreate(){
        Log.i(this.javaClass.name, "onCreate Called")
    }
    fun onStart(){
        Log.i(this.javaClass.name, "onStart Called")
    }
    fun onResume(){
        Log.i(this.javaClass.name, "onResume Called")
    }
    fun onPause(){
        Log.i(this.javaClass.name, "onPause Called")
    }
    fun onStop(){
        Log.i(this.javaClass.name, "onStop Called")
    }
    fun onDestroy(){
        Log.i(this.javaClass.name, "onDestroy Called")
    }
    fun onRestart(){
        Log.i(this.javaClass.name, "onRestart Called")
    }
}