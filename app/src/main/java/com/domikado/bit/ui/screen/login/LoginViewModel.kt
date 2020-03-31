package com.domikado.bit.ui.screen.login

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.abstraction.database.BitDatabase
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.local.auth.LocalAuthenticationImpl
import com.domikado.bit.data.remote.auth.AuthenticationImpl
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.servicelocator.UserServiceLocator

class LoginViewModel(application: Application): BaseAndroidViewModel(application) {

    private val userDao by lazy {
        BitDatabase.getInstance(application).userDao()
    }

    private val localAuth by lazy {
        LocalAuthenticationImpl(userDao)
    }

    private val auth by lazy {
        AuthenticationImpl(BitAPI)
    }

    fun buildLoginLogic(view: LoginActivity): LoginLogic =
        LoginLogic(
            view,
            UserServiceLocator(localAuth, auth),
            AuthSource(),
            this
        ).also { view.setObserver(it) }
}