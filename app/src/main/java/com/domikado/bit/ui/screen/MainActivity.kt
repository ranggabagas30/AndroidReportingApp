package com.domikado.bit.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.domikado.bit.R
import com.domikado.bit.abstraction.notification.NotificationHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var args: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.main_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        main_toolbar.setupWithNavController(navController, appBarConfiguration)

        intent.extras?.also {
            val extraFromNotification = it.getString(NotificationHelper.EXTRA_KEY_REJECTION, null)
            println("extra from notification: $extraFromNotification")
        }
    }
}
