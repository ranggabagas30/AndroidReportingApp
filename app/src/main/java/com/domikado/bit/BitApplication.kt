package com.domikado.bit

import android.content.ContextWrapper
import androidx.core.app.NotificationManagerCompat
import androidx.multidex.MultiDexApplication
import com.domikado.bit.external.notification.NotificationHelper
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pixplicity.easyprefs.library.Prefs
import timber.log.Timber

class BitApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // install timber
        Timber.plant(Timber.DebugTree())

        // Prefs initialization
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        // ThreeTenABP by JakeWharton implements JSR-310 date/time
        AndroidThreeTen.init(this)

        // registering notification channel
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
            false,
            NotificationHelper.DEFAULT_NAME,
            "Channel notifikasi default"
        )
    }
}