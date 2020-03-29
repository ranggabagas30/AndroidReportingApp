package com.domikado.bit.ui.screen.login

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.domainmodel.Result
import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.utility.PREF_KEY_ACCESS_TOKEN
import com.github.ajalt.timberkt.Timber.d
import com.github.ajalt.timberkt.Timber.e
import com.google.firebase.iid.FirebaseInstanceId
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class LoginLogic(
    private val view: ILoginContract.View,
    private val userServiceLocator: UserServiceLocator,
    private val authSource: AuthSource,
    private val loginViewModel: LoginViewModel
): BaseLogic(), Observer<LoginEvent<Nothing>> {

    override fun onChanged(t: LoginEvent<Nothing>?) {
        when(t) {
            is LoginEvent.OnSignInButtonClick -> onSignInClick(t.username, t.password)
            //is LoginEvent.OnSignInResult -> onSignInResult(t.result)
            is LoginEvent.OnStart -> onStart()
        }
    }

    private fun onSignInClick(username: String, password: String) {

//        view.startLoadingSignIn(Loading(LOGIN_LOADING_TITLE, LOGIN_LOADING_MESSAGE))
//        loginViewModel.async(authSource.signInUser(username, password, userServiceLocator)
//            .subscribeOn(schedulerProvier.io())
//            .observeOn(schedulerProvier.ui())
//            .subscribe(
//                { user ->
//                    view.dismissLoading()
//                    onSignInResult(Result.build { LoginResult(user, LOGIN_SUCCESS) })
//                },
//                { t ->
//                    view.dismissLoading()
//                    onSignInResult(Result.build { throw t })
//                }
//            ))


        //debug
        view.startLoadingSignIn(Loading(LOGIN_LOADING_TITLE, LOGIN_LOADING_MESSAGE))
        loginViewModel.async(
            Completable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        view.dismissLoading()
                        view.navigateAfterLogin()
                    },
                    { t ->
                        view.dismissLoading()
                        view.showError(t)
                    }
                )
        )
    }

    private fun onSignInResult(loginResult: Result<Throwable, LoginResult>) {

        when(loginResult) {
            is Result.Value -> {
                val user        = loginResult.value.user
                val message     = loginResult.value.message

                // save user data
                saveUserData(user)
                message?.also {
                    view.showMessageLoginSuccess(it)
                    view.navigateAfterLogin()
                }
            }
            is Result.Error -> {
                // show error
                val error = loginResult.error
                view.showError(error)
            }
        }
    }

    private fun onStart() {

        getFirebaseIdAndToken()

        // mengecek apakah session user masih ada
        val accessToken = Prefs.getString(PREF_KEY_ACCESS_TOKEN, null)
        if (!TextUtils.isEmpty(accessToken)) view.navigateAfterLogin()
    }

    private fun saveUserData(user: User) {

    }

    private fun getFirebaseIdAndToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    e {"Error FCM: ${task.exception}"}
                    return@addOnCompleteListener
                }

                val id = task.result?.id
                val token = task.result?.token
                d {"firebase id: $id"}
                d {"firebase Token: $token"}
            }
    }
}

internal val USERNAME_PASSWORD_EMPTY = "Mohon masukkan username dan password yang benar"
internal val LOGIN_SUCCESS = "Login Berhasil"
internal val LOGIN_ERROR    = "Login Gagal"
internal val LOGIN_LOADING_TITLE = "Sign In"
internal val LOGIN_LOADING_MESSAGE = "Menunggu validasi akun"