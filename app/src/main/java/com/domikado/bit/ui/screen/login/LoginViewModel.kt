package com.domikado.bit.ui.screen.login

import com.domikado.bit.abstraction.base.BaseViewModel
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.remote.auth.AuthenticationImpl
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.servicelocator.UserServiceLocator

class LoginViewModel: BaseViewModel() {

    private val auth by lazy {
        AuthenticationImpl(BitAPI)
    }

    fun buildLoginLogic(view: LoginActivity): LoginLogic =
        LoginLogic(
            view,
            UserServiceLocator(auth),
            AuthSource(),
            this
        ).also { view.setObserver(it) }
}