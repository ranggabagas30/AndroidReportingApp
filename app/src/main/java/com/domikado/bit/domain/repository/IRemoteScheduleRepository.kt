package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Schedule
import io.reactivex.Single

interface IRemoteScheduleRepository {

    fun getSchedules(userId: Int): Single<List<Schedule>>
}