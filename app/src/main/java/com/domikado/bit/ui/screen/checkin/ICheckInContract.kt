package com.domikado.bit.ui.screen.checkin

import androidx.lifecycle.Observer
import com.domikado.bit.domain.domainmodel.Loading

interface ICheckInContract {

    interface View {
        fun showLoadingCheckIn(loading: Loading)
        fun dismissLoading()
        fun showCheckInData(scheduleId: Int,
                            workDate: String?,
                            siteId: Int,
                            siteName: String?,
                            siteIdCustomer: String?,
                            siteLatitude: Double?,
                            siteLongitude: Double?)
        fun setObserver(observer: Observer<CheckInEvent>)
    }
}

sealed class CheckInEvent {
    data class OnCreate(
        val scheduleId: Int,
        val workDate: String?,
        val siteId: Int,
        val siteName: String?,
        val siteIdCustomer: String?,
        val siteLatitude: Double?,
        val siteLongitude: Double?
    ): CheckInEvent()
    object OnCreateView: CheckInEvent()
    object OnViewCreated: CheckInEvent()
    data class OnCheckInClick(
        val scheduleId: Int,
        val siteId: Int,
        val distanceToSite: Double,
        val gpsAccuracy: Double
    ): CheckInEvent()
}