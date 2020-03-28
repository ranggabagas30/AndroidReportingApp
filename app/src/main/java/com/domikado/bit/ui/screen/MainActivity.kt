package com.domikado.bit.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.domikado.bit.R
import com.domikado.bit.utility.GpsUtils
import com.github.ajalt.timberkt.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d { "on activity result from MainActivity" }
        if (requestCode == GpsUtils.GPS_REQUEST) {
            Timber.d { "request code GPS REQUEST" }
            val checkInFragment = supportFragmentManager.findFragmentById(R.id.checkInFragment)
            checkInFragment?.onActivityResult(requestCode, resultCode, data)
        } else super.onActivityResult(requestCode, resultCode, data)
    }
}
