package com.domikado.bit.domain.servicelocator

import com.domikado.bit.domain.repository.IAuthRepository

data class UserServiceLocator(val authRepository: IAuthRepository)