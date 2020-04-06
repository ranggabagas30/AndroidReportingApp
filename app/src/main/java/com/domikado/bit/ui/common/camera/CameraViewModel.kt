package com.domikado.bit.ui.common.camera

import android.app.Application
import android.os.Bundle
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.abstraction.location.LocationService

class CameraViewModel(application: Application): BaseAndroidViewModel(application) {
    var args: Bundle? = null

    val locationService: LocationService by lazy {
        LocationService(application)
    }
}