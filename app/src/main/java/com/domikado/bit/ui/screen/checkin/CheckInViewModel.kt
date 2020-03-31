package com.domikado.bit.ui.screen.checkin

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.abstraction.database.BitDatabase
import com.domikado.bit.abstraction.location.LocationService
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.local.auth.LocalAuthenticationImpl
import com.domikado.bit.data.local.schedule.RoomLocalScheduleImpl
import com.domikado.bit.data.remote.auth.AuthenticationImpl
import com.domikado.bit.data.remote.schedule.RemoteScheduleImpl
import com.domikado.bit.domain.domainmodel.Operator
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.ScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator

class CheckInViewModel(application: Application): BaseAndroidViewModel(application) {

    var scheduleId: Int = -1
    var workDate: String? = null
    var siteId: Int = -1
    var siteName: String? = null
    var siteCode: String? = null
    var siteLatitude: Double? = null
    var siteLongitude: Double? = null
    var siteMonitorId: Int = -1
    var operator: Operator? = null

    private val userDao by lazy {
        BitDatabase.getInstance(application).userDao()
    }

    private val scheduleDao by lazy {
        BitDatabase.getInstance(application).scheduleDao()
    }

    private val localSchedule by lazy {
        RoomLocalScheduleImpl(scheduleDao)
    }

    private val remoteSchedule by lazy {
        RemoteScheduleImpl(BitAPI)
    }

    private val localAuth by lazy {
        LocalAuthenticationImpl(userDao)
    }

    private val auth by lazy {
        AuthenticationImpl(BitAPI)
    }

    val locationService: LocationService by lazy {
        LocationService(application)
    }

    fun buildCheckInLogic(view: CheckInFragment): CheckInLogic =
        CheckInLogic(
            view,
            AuthSource(),
            ScheduleSource(),
            UserServiceLocator(localAuth, auth),
            ScheduleServiceLocator(localSchedule, remoteSchedule),
            this
        ).also { view.setObserver(it) }
}