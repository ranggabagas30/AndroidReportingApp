package com.domikado.bit.ui.screen.schedulelist

import androidx.lifecycle.Observer
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.ui.screen.schedulelist.recyclerview.ScheduleModel

interface IScheduleListContract {

    interface View {
        fun startLoadingSchedule(loading: Loading)
        fun dismissLoading()
        fun loadSchedules(schedules: ArrayList<ScheduleModel>) // debug
        fun uploadScheduleData(schedule: ScheduleModel) // debug only
        fun setObserver(observer: Observer<ScheduleListEvent>)
    }
}

sealed class ScheduleListEvent {
    object OnStart: ScheduleListEvent()
    data class OnScheduleItemClick(val schedule: Schedule): ScheduleListEvent()
}

internal val SCHEDULE_LIST_LOADING_TITLE = "Mohon tunggu"
internal val SCHEDULE_LIST_LOADING_MESSAGE = "Memuat daftar schedule"