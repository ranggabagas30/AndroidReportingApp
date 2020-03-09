package com.domikado.bit.data.remote.schedule

import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.repository.IRemoteScheduleRepository
import io.reactivex.Single

class RemoteScheduleImpl: IRemoteScheduleRepository {

    override fun getWorkTypeSchedules(workTypeId: Int): Single<List<Schedule>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}