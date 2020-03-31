package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.CheckIn
import com.domikado.bit.domain.domainmodel.Schedule
import com.domikado.bit.domain.servicelocator.ScheduleServiceLocator
import io.reactivex.Single

class ScheduleSource {

    fun getSchedules(userId: String, apiToken: String, firebaseToken: String, locator: ScheduleServiceLocator): Single<List<Schedule>> =
        locator.remoteScheduleRepository.getSchedules(userId, apiToken, firebaseToken)
            .map {
                locator.localScheduleRepository.saveSchedules(it)
                it
            }

    fun getProgress(scheduleId: Int, locator: ScheduleServiceLocator) =
        locator.localScheduleRepository.getProgress(scheduleId)

    fun checkIn(userId: String, apiToken: String, firebaseId: String, siteMonitorId: Int, locator: ScheduleServiceLocator): Single<CheckIn> =
        locator.remoteScheduleRepository.checkIn(userId, apiToken, firebaseId, siteMonitorId)

}