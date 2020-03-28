package com.domikado.bit.ui.screen.checkin

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.abstraction.location.LocationService

class CheckInViewModel(application: Application): BaseAndroidViewModel(application) {

    var scheduleId: Int = -1
    var workDate: String? = null
    var siteId: Int = -1
    var siteName: String? = null
    var siteCode: String? = null
    var siteLatitude: Double? = null
    var siteLongitude: Double? = null
    var siteMonitorId: Int = 0

    val locationService: LocationService by lazy {
        LocationService(application)
    }

    fun buildCheckInLogic(view: CheckInFragment): CheckInLogic =
        CheckInLogic(
            view,
            this
        ).also { view.setObserver(it) }
}