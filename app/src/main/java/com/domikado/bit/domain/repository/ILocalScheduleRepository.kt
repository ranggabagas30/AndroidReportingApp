package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Schedule
import io.reactivex.Completable
import io.reactivex.Single

interface ILocalScheduleRepository {

    fun getSchedules(userId: Int, workTypeId: Int? = null): Single<List<Schedule>>

    fun deleteSchedules(userId: Int): Completable

}