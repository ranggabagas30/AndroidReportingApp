package com.domikado.bit.domain.servicelocator

import com.domikado.bit.domain.repository.IFormFillDataRepository

data class FormFillServiceLocator(val formFillDataRepository: IFormFillDataRepository) {
}