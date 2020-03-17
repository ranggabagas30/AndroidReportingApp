package com.domikado.bit.ui.screen.checkin

import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic

class CheckInLogic(
    private val view: ICheckInContract.View,
    private val checkInViewModel: CheckInViewModel
): BaseLogic(), Observer<CheckInEvent>{

    override fun onChanged(t: CheckInEvent?) {
        when (t) {
            is CheckInEvent.OnCreate -> onCreate(t.scheduleId, t.workDate, t.siteId, t.siteName, t.siteIdCustomer, t.siteLatitude, t.siteLongitude)
            is CheckInEvent.OnCreateView -> onCreateView()
            is CheckInEvent.OnViewCreated -> onViewCreated()
            is CheckInEvent.OnCheckInClick -> onCheckInClick(t.scheduleId, t.siteId, t.distanceToSite, t.gpsAccuracy)
        }
    }

    private fun onCreate(scheduleId: Int,
                         workDate: String?,
                         siteId: Int,
                         siteName: String?,
                         siteIdCustomer: String?,
                         siteLatitude: Double?,
                         siteLongitude: Double?) { // receives args from schedule list fragment
        checkInViewModel.scheduleId = scheduleId
        checkInViewModel.workDate = workDate
        checkInViewModel.siteId = siteId
        checkInViewModel.siteName = siteName
        checkInViewModel.siteIdCustomer = siteIdCustomer
        checkInViewModel.siteLatitude = siteLatitude
        checkInViewModel.siteLongitude = siteLongitude
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
                it.siteIdCustomer,
                it.siteLatitude,
                it.siteLongitude
            )
        }
    }

    private fun onCheckInClick(scheduleId: Int, siteId: Int, distanceToSite: Double, gpsAccuracy: Double) {
        // hit endpoint checkin
        // if success -> update site as checked in
    }
}