package com.example.spacedim.interfaces

import timber.log.Timber

interface LifeCycleLogs {
    fun onCreate(){
        // Timber.i("onCreate Called")
    }
    fun onStart(){
        // Timber.i("onStart Called")
    }
    fun onResume(){
        // Timber.i("onResume Called")
    }
    fun onPause(){
        // Timber.i("onPause Called")
    }
    fun onStop(){
        // Timber.i("onStop Called")
    }
    fun onDestroy(){
        // Timber.i("onDestroy Called")
    }
    fun onRestart(){
        // Timber.i("onRestart Called")
    }
}