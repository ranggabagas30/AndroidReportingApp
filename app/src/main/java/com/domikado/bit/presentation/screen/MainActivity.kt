package com.domikado.bit.presentation.screen

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.domikado.bit.R
import com.domikado.bit.external.notification.NotificationHelper
import com.domikado.bit.presentation.screen.mainmenu.MainMenuFragmentDirections
import com.github.ajalt.timberkt.d
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.main_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        main_toolbar.setupWithNavController(navController, appBarConfiguration)

        intent.extras?.also {
            val dataRejection = it.getString(NotificationHelper.EXTRA_KEY_REJECTION, null)
            d {"data rejection: $dataRejection"}

            if (!TextUtils.isEmpty(dataRejection)) {
                val action = MainMenuFragmentDirections.actionMainMenuFragmentToScheduleListFragment(1)
                navController.navigate(action)
            }
        }
    }
}
