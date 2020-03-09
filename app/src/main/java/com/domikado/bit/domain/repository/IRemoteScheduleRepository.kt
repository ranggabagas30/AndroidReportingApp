package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Schedule
import io.reactivex.Single

interface IRemoteScheduleRepository {

    fun getWorkTypeSchedules(workTypeId: Int): Single<List<Schedule>>

}