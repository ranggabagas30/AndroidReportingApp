package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Schedule
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalScheduleRepository {

    fun getSchedules(userId: String): Single<List<Schedule>>

    fun deleteSchedules(userId: String): Completable

    fun deleteSchedules(schedules: List<Schedule>): Completable

    fun saveSchedules(schedules: List<Schedule>): Completable

    fun getProgress(scheduleId: Int): Single<Int>
}