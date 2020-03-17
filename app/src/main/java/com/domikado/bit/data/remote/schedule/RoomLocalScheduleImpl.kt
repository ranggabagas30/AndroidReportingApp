package com.domikado.bit.data.remote.schedule

import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.repository.ILocalScheduleRepository
import io.reactivex.Completable
import io.reactivex.Single

class RoomLocalScheduleImpl : ILocalScheduleRepository {

    override fun getSchedules(userId: Int, workTypeId: Int?): Single<List<Schedule>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSchedules(userId: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}