package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import io.reactivex.Single

class RemoteScheduleSource {

    fun getWorkTypeSchedules(workTypeId: Int, locator: ScheduleServiceLocator): Single<List<Schedule>>
            = locator.remoteScheduleRepository.getWorkTypeSchedules(workTypeId)

}