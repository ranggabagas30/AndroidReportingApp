package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import io.reactivex.Completable
import io.reactivex.Single

class LocalScheduleSource {

    fun getWorkTypeSchedules(workTypeId: Int, locator: ScheduleServiceLocator): Single<List<Schedule>>
            = locator.localScheduleRepository.getWorkTypeSchedules(workTypeId)

    fun deleteSchedules(schedules: List<Schedule>, locator: ScheduleServiceLocator): Completable
            = locator.localScheduleRepository.deleteSchedules(schedules)
}