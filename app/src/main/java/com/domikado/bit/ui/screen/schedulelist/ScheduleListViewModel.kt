package com.domikado.bit.ui.screen.schedulelist

import com.domikado.bit.abstraction.base.BaseViewModel
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.remote.auth.AuthenticationImpl
import com.domikado.bit.data.remote.schedule.RemoteScheduleImpl
import com.domikado.bit.data.remote.schedule.RoomLocalScheduleImpl
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.LocalScheduleSource
import com.domikado.bit.domain.interactor.RemoteScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.utility.PREF_KEY_ACCESS_TOKEN
import com.pixplicity.easyprefs.library.Prefs

class ScheduleListViewModel: BaseViewModel() {

    private val localSchedule by lazy {
        RoomLocalScheduleImpl()
    }

    private val remoteSchedule by lazy {
        val accessToken = Prefs.getString(PREF_KEY_ACCESS_TOKEN, "dummy")
        if (accessToken != null)
            RemoteScheduleImpl(BitAPI, accessToken)
        else throw BitThrowable.BitApiTokenNullException()
    }

    private val auth by lazy {
        AuthenticationImpl(BitAPI)
    }

    fun buildScheduleListLogic(view: ScheduleListFragment): ScheduleListLogic =
        ScheduleListLogic(
            view,
            AuthSource(),
            LocalScheduleSource(),
            RemoteScheduleSource(),
            ScheduleServiceLocator(localSchedule, remoteSchedule),
            UserServiceLocator(auth)
        ).also { view.setObserver(it) }
}