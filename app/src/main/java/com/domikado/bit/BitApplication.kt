package com.domikado.bit

import android.content.ContextWrapper
import androidx.multidex.MultiDexApplication
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

        AndroidThreeTen.init(this); // ThreeTenABP by JakeWharton implements JSR-310 date/time
    }
}