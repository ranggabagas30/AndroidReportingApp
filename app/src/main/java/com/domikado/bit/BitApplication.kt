package com.domikado.bit

import android.content.ContextWrapper
import androidx.multidex.MultiDexApplication
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
    }
}