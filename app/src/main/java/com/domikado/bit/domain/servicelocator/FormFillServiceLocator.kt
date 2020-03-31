package com.domikado.bit.domain.servicelocator

import com.domikado.bit.domain.repository.IFormFillDataRepository
import com.domikado.bit.domain.repository.IRemoteFormFillDataRepository

data class FormFillServiceLocator(
    val formFillDataRepository: IFormFillDataRepository,
    val remoteFormFillDataRepository: IRemoteFormFillDataRepository
) {
}