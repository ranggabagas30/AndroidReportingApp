package com.domikado.bit.presentation.screen.schedulelist

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.data.localrepo.database.BitDatabase
import com.domikado.bit.data.remoterepo.retrofit.BitAPI
import com.domikado.bit.data.localrepo.auth.LocalAuthenticationImpl
import com.domikado.bit.data.localrepo.schedule.RoomLocalScheduleImpl
import com.domikado.bit.data.remoterepo.auth.AuthenticationImpl
import com.domikado.bit.data.remoterepo.schedule.RemoteScheduleImpl
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.interactor.ScheduleSource
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import com.domikado.bit.domain.servicelocator.UserServiceLocator

class ScheduleListViewModel(application: Application): BaseAndroidViewModel(application) {

    private val userDao by lazy {
        BitDatabase.getInstance(application).userDao()
    }

    private val scheduleDao by lazy {
        BitDatabase.getInstance(application).scheduleDao()
    }

    private val localAuth by lazy {
        LocalAuthenticationImpl(userDao)
    }

    private val localSchedule by lazy {
        RoomLocalScheduleImpl(scheduleDao)
    }

    private val remoteSchedule by lazy {
        RemoteScheduleImpl(BitAPI)
    }

    private val auth by lazy {
        AuthenticationImpl(BitAPI)
    }

    fun buildScheduleListLogic(view: ScheduleListFragment): ScheduleListLogic =
        ScheduleListLogic(
            view,
            AuthSource(),
            ScheduleSource(),
            ScheduleServiceLocator(localSchedule, remoteSchedule),
            UserServiceLocator(localAuth, auth),
            this
        ).also { view.setObserver(it) }
}