package com.domikado.bit.data.remote.schedule

import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.domainmodel.toSchedule
import com.domikado.bit.domain.repository.IRemoteScheduleRepository
import io.reactivex.Single

class RemoteScheduleImpl(private val bitAPI: BitAPI): IRemoteScheduleRepository {

    override fun getSchedules(userId: Int): Single<List<Schedule>> {
        return bitAPI.getSchedules(userId.toString())
            .map {
                it.data.map { data ->
                    data.toSchedule
            }}
    }
}