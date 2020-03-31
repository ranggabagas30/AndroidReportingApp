package com.domikado.bit.domain.servicelocator

import com.domikado.bit.domain.repository.IAuthRepository
import com.domikado.bit.domain.repository.ILocalAuthRepository

data class UserServiceLocator(val localAuthRepository: ILocalAuthRepository, val authRepository: IAuthRepository)