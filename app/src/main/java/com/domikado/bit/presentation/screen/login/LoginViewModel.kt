package com.domikado.bit.presentation.screen.login

import android.app.Application
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.data.localrepo.database.BitDatabase
import com.domikado.bit.data.remoterepo.retrofit.BitAPI
import com.domikado.bit.data.localrepo.auth.LocalAuthenticationImpl
import com.domikado.bit.data.remoterepo.auth.AuthenticationImpl
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