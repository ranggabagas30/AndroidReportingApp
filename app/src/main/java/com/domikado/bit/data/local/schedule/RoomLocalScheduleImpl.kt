package com.domikado.bit.data.local.schedule

import com.domikado.bit.data.local.database.dao.ScheduleDao
import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.domainmodel.toSchedule
import com.domikado.bit.domain.domainmodel.toTbSchedule
import com.domikado.bit.domain.repository.ILocalScheduleRepository
import io.reactivex.Completable
import io.reactivex.Single

class RoomLocalScheduleImpl(private val scheduleDao: ScheduleDao) : ILocalScheduleRepository {

    override fun getSchedules(userId: String): Single<List<Schedule>> {
        return scheduleDao.getUserSchedules(userId).map {
            it.map { it.toSchedule }
        }
    }

    override fun deleteSchedules(userId: String): Completable {
        return scheduleDao.deleteByUserId(userId)
    }

    override fun deleteSchedules(schedules: List<Schedule>): Completable {
        return scheduleDao.delete(schedules.map { it.toTbSchedule })
    }

    override fun saveSchedules(schedules: List<Schedule>): Completable {
        return scheduleDao.insertOrReplace(schedules.map { it.toTbSchedule })
    }

    override fun getProgress(scheduleId: Int): Single<Int> {
        return scheduleDao.getUserScheduleProgress(scheduleId)
    }
}