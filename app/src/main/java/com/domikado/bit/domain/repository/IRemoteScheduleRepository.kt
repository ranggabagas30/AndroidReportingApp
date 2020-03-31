package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.CheckIn
import com.domikado.bit.domain.domainmodel.Schedule
import io.reactivex.Single

interface IRemoteScheduleRepository {

    fun getSchedules(userId: String, apiToken: String, firebaseToken: String): Single<List<Schedule>>

    fun checkIn(userId: String, apiToken: String, firebaseId: String, siteMonitorId: Int): Single<CheckIn>
}