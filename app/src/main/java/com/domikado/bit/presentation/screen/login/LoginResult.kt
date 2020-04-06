package com.domikado.bit.presentation.screen.login

import com.domikado.bit.domain.domainmodel.User

data class LoginResult(
    val user: User,
    val message: String?
)