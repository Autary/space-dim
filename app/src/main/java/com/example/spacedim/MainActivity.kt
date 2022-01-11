package com.example.spacedim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import timber.log.Timber

class MainActivity : AppCompatActivity(), LifeCycleLogs {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        super<LifeCycleLogs>.onCreate()
        setContentView(R.layout.activity_main)
        val navCtrl = this.findNavController(R.id.mainNavHostFrag)
        // android:label in navigation.xml not working, fix by this code
        navCtrl.addOnDestinationChangedListener{
            ctrl, dest, args -> title = dest.label
        }
        NavigationUI.setupActionBarWithNavController(this, navCtrl)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navCtrl = this.findNavController(R.id.mainNavHostFrag)
        return navCtrl.navigateUp()
    }

    override fun onStart() {
        super<AppCompatActivity>.onStart()
        super<LifeCycleLogs>.onStart()
    }

    override fun onResume() {
        super<AppCompatActivity>.onResume()
        super<LifeCycleLogs>.onResume()
    }

    override fun onPause() {
        super<AppCompatActivity>.onPause()
        super<LifeCycleLogs>.onPause()
    }

    override fun onStop() {
        super<AppCompatActivity>.onStop()
        super<LifeCycleLogs>.onStop()
    }

    override fun onDestroy() {
        super<AppCompatActivity>.onDestroy()
        super<LifeCycleLogs>.onDestroy()
    }

    override fun onRestart() {
        super<AppCompatActivity>.onRestart()
        super<LifeCycleLogs>.onRestart()
    }
}