package com.domikado.bit.domain.servicelocator

import com.domikado.bit.domain.repository.ILocalOperatorRepository

data class OperatorServiceLocator(val localOperatorRepository: ILocalOperatorRepository) {
}