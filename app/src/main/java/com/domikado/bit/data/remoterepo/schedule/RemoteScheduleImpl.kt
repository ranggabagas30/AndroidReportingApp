package com.domikado.bit.data.remoterepo.schedule

import com.domikado.bit.data.remoterepo.retrofit.BitAPI
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.CheckIn
import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.domainmodel.toSchedule
import com.domikado.bit.domain.repository.IRemoteScheduleRepository
import com.github.ajalt.timberkt.d
import io.reactivex.Single

class RemoteScheduleImpl(private val bitAPI: BitAPI): IRemoteScheduleRepository {

    override fun getSchedules(userId: String, apiToken: String, firebaseToken: String): Single<List<Schedule>> {
        return bitAPI.getSchedules(userId, apiToken, firebaseToken)
            .map { response ->
                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
                else {
                    if (response.data == null) throw BitThrowable.BitApiResponseException("Response schedule list kosong")
                    response.data.map {
                        it.toSchedule
                    }
                }
            }

    }

    override fun checkIn(
        userId: String,
        apiToken: String,
        firebaseId: String,
        siteMonitorId: Int
    ): Single<CheckIn> {
        return bitAPI.checkIn(userId, apiToken, firebaseId, siteMonitorId.toString())
            .map { response ->
                d {"checkin response: $response"}
                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
                else {
                    if (response.data == null) throw BitThrowable.BitApiResponseException("Response checkin kosong")
                    CheckIn(
                        response.status,
                        response.message
                    )
                }
            }
    }
}