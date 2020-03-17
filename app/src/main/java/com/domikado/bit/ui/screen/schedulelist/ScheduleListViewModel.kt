package com.domikado.bit.ui.screen.schedulelist

import com.domikado.bit.abstraction.base.BaseViewModel
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.remote.auth.AuthenticationImpl
import com.domikado.bit.data.remote.schedule.RemoteScheduleImpl
import com.domikado.bit.data.remote.schedule.RoomLocalScheduleImpl
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.LocalScheduleSource
import com.domikado.bit.domain.interactor.RemoteScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator

class ScheduleListViewModel: BaseViewModel() {

    private val localSchedule by lazy {
        RoomLocalScheduleImpl()
    }

    private val remoteSchedule by lazy {
        RemoteScheduleImpl(BitAPI)
    }

    private val auth by lazy {
        AuthenticationImpl()
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