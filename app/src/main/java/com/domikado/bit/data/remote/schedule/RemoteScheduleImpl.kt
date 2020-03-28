package com.domikado.bit.data.remote.schedule

import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.CheckIn
import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.domainmodel.toSchedule
import com.domikado.bit.domain.repository.IRemoteScheduleRepository
import io.reactivex.Single

class RemoteScheduleImpl(private val bitAPI: BitAPI, private val apiToken: String): IRemoteScheduleRepository {

    override fun getSchedules(userId: Int): Single<List<Schedule>> {
        return bitAPI.getSchedules(userId.toString(), apiToken)
            .map { response ->
                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
                else {
                    response.data?.map {
                        it.toSchedule
                    }
                }
            }
    }

    override fun checkIn(userId: Int): Single<CheckIn> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //    override fun checkIn(userId: Int, siteIdMonitor: String): Single<CheckIn> {
////        return bitAPI.checkIn(userId.toString(), apiToken, siteIdMonitor)
////            .map { response ->
////                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
////                else {
////                    response.data?.map {
////                        it.toSite
////                    }
////                }
////            }
//    }
}