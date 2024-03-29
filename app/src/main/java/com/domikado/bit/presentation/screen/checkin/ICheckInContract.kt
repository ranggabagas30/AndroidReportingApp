package com.domikado.bit.presentation.screen.checkin

import android.location.Location
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
                            siteCode: String?,
                            siteLatitude: Double?,
                            siteLongitude: Double?,
                            siteMonitorId: Int)
        fun checkInFailed(message: String)
        fun showError(t: Throwable)
        fun navigateAfterCheckIn()
        fun setObserver(observer: Observer<CheckInEvent>)
    }
}

sealed class CheckInEvent {
    object OnCreateView: CheckInEvent()
    object OnViewCreated: CheckInEvent()
    data class OnCheckInClick(val userLocation: Location?): CheckInEvent()
}