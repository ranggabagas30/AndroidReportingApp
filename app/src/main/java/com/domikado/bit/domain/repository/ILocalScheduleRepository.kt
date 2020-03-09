package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Schedule
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalScheduleRepository {

    fun getWorkTypeSchedules(workTypeId: Int): Single<List<Schedule>>

    fun deleteSchedules(schedules: List<Schedule>): Completable

}