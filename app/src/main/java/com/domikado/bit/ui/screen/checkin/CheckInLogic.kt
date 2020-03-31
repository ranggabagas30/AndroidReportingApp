package com.domikado.bit.ui.screen.checkin

import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.ScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.utility.PREF_KEY_FIREBASE_ID
import com.pixplicity.easyprefs.library.Prefs

class CheckInLogic(
    private val view: ICheckInContract.View,
    private val authSource: AuthSource,
    private val scheduleSource: ScheduleSource,
    private val userServiceLocator: UserServiceLocator,
    private val scheduleServiceLocator: ScheduleServiceLocator,
    private val checkInViewModel: CheckInViewModel
): BaseLogic(), Observer<CheckInEvent>{

    override fun onChanged(t: CheckInEvent?) {
        when (t) {
            is CheckInEvent.OnViewCreated -> onViewCreated()
            is CheckInEvent.OnCheckInClick -> onCheckInClick()
        }
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
        authSource.getCurrentUser(userServiceLocator)?.also {
            val firebaseId = Prefs.getString(PREF_KEY_FIREBASE_ID, null)
            view.showLoadingCheckIn(Loading(message = "Memproses check in"))
            checkInViewModel.async(
                scheduleSource.checkIn(it.id, it.accessToken, firebaseId, checkInViewModel.siteMonitorId, scheduleServiceLocator)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({ result ->
                        view.dismissLoading()
                        if (result.status == 1) {
                            view.navigateAfterCheckIn()
                        } else {
                            view.checkInFailed(result.message?: "Gagal melakukan check in")
                        }
                    }, { t ->
                        view.dismissLoading()
                        view.showError(t, "Error check in")
                    })
            )
        }

    }
}