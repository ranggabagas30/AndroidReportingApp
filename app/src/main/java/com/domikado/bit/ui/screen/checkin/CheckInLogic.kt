package com.domikado.bit.ui.screen.checkin

import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic

class CheckInLogic(
    private val view: ICheckInContract.View,
    private val checkInViewModel: CheckInViewModel
): BaseLogic(), Observer<CheckInEvent>{

    override fun onChanged(t: CheckInEvent?) {
        when (t) {
            is CheckInEvent.OnCreate -> onCreate(t.scheduleId, t.workDate, t.siteId, t.siteName, t.siteCode, t.siteLatitude, t.siteLongitude, t.siteMonitorId)
            is CheckInEvent.OnCreateView -> onCreateView()
            is CheckInEvent.OnViewCreated -> onViewCreated()
            is CheckInEvent.OnCheckInClick -> onCheckInClick()
        }
    }

    private fun onCreate(scheduleId: Int,
                         workDate: String?,
                         siteId: Int,
                         siteName: String?,
                         siteCode: String?,
                         siteLatitude: Double?,
                         siteLongitude: Double?,
                         siteMonitorId: Int) { // receives args from schedule list fragment
        checkInViewModel.scheduleId = scheduleId
        checkInViewModel.workDate = workDate
        checkInViewModel.siteId = siteId
        checkInViewModel.siteName = siteName
        checkInViewModel.siteCode = siteCode
        checkInViewModel.siteLatitude = siteLatitude
        checkInViewModel.siteLongitude = siteLongitude
        checkInViewModel.siteMonitorId = siteMonitorId
    }

    private fun onCreateView() {

    }

    private fun onViewCreated() {
        checkInViewModel.also {
            view.showCheckInData(
                it.scheduleId,
                it.workDate,
                it.siteId,
                it.siteName,
                it.siteCode,
                it.siteLatitude,
                it.siteLongitude,
                it.siteMonitorId
            )
        }
    }

    private fun onCheckInClick() {
        // hit endpoint checkin
        // if success -> update Site as checked in

    }
}