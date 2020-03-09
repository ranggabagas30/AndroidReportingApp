package com.domikado.bit.domain.servicelocator

import com.domikado.bit.domain.repository.ILocalScheduleRepository
import com.domikado.bit.domain.repository.IRemoteScheduleRepository

data class ScheduleServiceLocator(
    val localScheduleRepository: ILocalScheduleRepository,
    val remoteScheduleRepository: IRemoteScheduleRepository
)