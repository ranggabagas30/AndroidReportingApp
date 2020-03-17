package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import io.reactivex.Completable
import io.reactivex.Single

class LocalScheduleSource {

    fun getWorkTypeSchedules(userId: Int, workTypeId: Int? = null, locator: ScheduleServiceLocator): Single<List<Schedule>>
            = locator.localScheduleRepository.getSchedules(userId, workTypeId)

    fun deleteSchedules(userId: Int, locator: ScheduleServiceLocator): Completable
            = locator.localScheduleRepository.deleteSchedules(userId)
}