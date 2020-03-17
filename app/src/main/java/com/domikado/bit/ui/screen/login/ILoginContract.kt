package com.domikado.bit.ui.screen.login

import androidx.lifecycle.Observer
import com.domikado.bit.domain.domainmodel.Loading

interface ILoginContract {

    interface View {
        fun startLoadingSignIn(loading: Loading)
        fun dismissLoading()
        fun setObserver(observer: Observer<LoginEvent<Nothing>>)
        fun showError(t: Throwable)
        fun showMessageLoginSuccess(s: String)
        fun navigateAfterLogin()
    }
}

sealed class LoginEvent<out T> {
    object OnStart: LoginEvent<Nothing>()
    data class OnSignInButtonClick(val username: String, val password: String): LoginEvent<Nothing>()
    //data class OnSignInResult<out T>(val result: T): LoginEvent<T>()
}
