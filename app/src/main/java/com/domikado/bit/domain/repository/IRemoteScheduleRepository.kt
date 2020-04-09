package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.CheckIn
import com.domikado.bit.domain.domainmodel.Schedule
import io.reactivex.Single
import okhttp3.RequestBody

interface IRemoteScheduleRepository {

    fun getSchedules(userId: String, apiToken: String, firebaseToken: String): Single<List<Schedule>>

    fun checkIn(userId: String, apiToken: String, firebaseId: String, siteMonitorId: Int, textBody: Map<String, RequestBody>): Single<CheckIn>
}