package com.domikado.bit.ui.screen.schedulelist

import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.toScheduleModel
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.ScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.utility.PREF_KEY_FIREBASE_TOKEN
import com.pixplicity.easyprefs.library.Prefs

/**
 * - mengunduh data schedule dari remote server
 * - menyimpan data schedule ke dalam database lokal
 * - meload list data schedule ke halaman ScheduleListFragment
 *
 * */
class ScheduleListLogic(
    private val view: IScheduleListContract.View,
    private val authSource: AuthSource,
    private val scheduleSource: ScheduleSource,
    private val scheduleServiceLocator: ScheduleServiceLocator,
    private val userServiceLocator: UserServiceLocator,
    private val scheduleListViewModel: ScheduleListViewModel
): BaseLogic(), Observer<ScheduleListEvent> {

    override fun onChanged(t: ScheduleListEvent?) {
        when(t) {
            is ScheduleListEvent.OnStart -> onStart()
            is ScheduleListEvent.OnScheduleItemClick -> onScheduleItemClick()
        }
    }

    private fun onStart() {
        fetchRemoteSchedules()
    }

    private fun onScheduleItemClick() {

    }

    private fun fetchRemoteSchedules() {
        authSource.getCurrentUser(userServiceLocator)?.let {
            Prefs.getString(PREF_KEY_FIREBASE_TOKEN, null)?.let { firebaseToken ->
                view.startLoadingSchedule(Loading(message = "Memuat schedule"))
                scheduleListViewModel.async(
                    scheduleSource.getSchedules(it.id, it.accessToken, firebaseToken, scheduleServiceLocator)
                        .concatWith {  }
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({ data ->
                            view.dismissLoading()
                            view.loadSchedules(data.map { it.toScheduleModel } )
                        }, { t->
                            view.dismissLoading()
                            view.showError(t, "Gagal memuat schedule")
                        })
                )
            }
        }
    }
}