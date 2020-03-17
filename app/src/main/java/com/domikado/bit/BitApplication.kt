package com.domikado.bit

import android.content.ContextWrapper
import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pixplicity.easyprefs.library.Prefs

class BitApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

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