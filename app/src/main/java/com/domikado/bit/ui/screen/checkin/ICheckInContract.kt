package com.domikado.bit.ui.screen.checkin

import android.location.Location
import androidx.lifecycle.Observer
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.Result

interface ICheckInContract {

    interface View {
        fun showLoadingCheckIn(loading: Loading)
        fun dismissLoading()
        fun showCheckInData(scheduleId: Int,
                            workDate: String?,
                            siteId: Int,
                            siteName: String?,
                            siteCode: String?,
                            siteLatitude: Double?,
                            siteLongitude: Double?,
                            siteMonitorId: Int)
        fun setObserver(observer: Observer<CheckInEvent>)
    }
}

sealed class CheckInEvent {
    data class OnCreate(
        val scheduleId: Int,
        val workDate: String?,
        val siteId: Int,
        val siteName: String?,
        val siteCode: String?,
        val siteLatitude: Double?,
        val siteLongitude: Double?,
        val siteMonitorId: Int
    ): CheckInEvent()
    object OnCreateView: CheckInEvent()
    object OnViewCreated: CheckInEvent()
    object OnCheckInClick: CheckInEvent()
    data class LocationListener(val result: Result<Throwable, Location?>): CheckInEvent()
}