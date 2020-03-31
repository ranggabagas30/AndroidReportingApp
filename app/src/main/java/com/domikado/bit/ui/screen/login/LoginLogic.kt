package com.domikado.bit.ui.screen.login

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.utility.*
import com.github.ajalt.timberkt.Timber.d
import com.github.ajalt.timberkt.Timber.e
import com.google.firebase.iid.FirebaseInstanceId
import com.pixplicity.easyprefs.library.Prefs

class LoginLogic(
    private val view: ILoginContract.View,
    private val userServiceLocator: UserServiceLocator,
    private val authSource: AuthSource,
    private val loginViewModel: LoginViewModel
): BaseLogic(), Observer<LoginEvent<Nothing>> {

    override fun onChanged(t: LoginEvent<Nothing>?) {
        when(t) {
            is LoginEvent.OnCreate -> onCreate()
            is LoginEvent.OnStart -> onStart()
            is LoginEvent.OnSignInButtonClick -> onSignInClick(t.username, t.password)
            //is LoginEvent.OnSignInResult -> onSignInResult(t.result)
        }
    }

    private fun onCreate() {
//        // mendapatkan firebase id dan token untuk disimpan
//        getFirebaseIdAndToken()
    }

    private fun onStart() {
        // mendapatkan firebase id dan token untuk disimpan
        getFirebaseIdAndToken()

        // mengecek apakah session user masih ada
        val userPrefs = Prefs.getString(PREF_KEY_USER, null)
        if (!TextUtils.isEmpty(userPrefs)) view.navigateAfterLogin()
    }

    private fun onSignInClick(username: String, password: String) {

        val firebaseToken = Prefs.getString(PREF_KEY_FIREBASE_TOKEN, null)

        if (!AuthUtil.isUsernameValid(username) || !AuthUtil.isPasswordValid(password)) {
            view.showError(BitThrowable.BitIllegalAuthException())
            return
        }

        if (!AuthUtil.isFirebaseTokenValid(firebaseToken)) {
            view.showError(NullPointerException("Firebase token kosong"))
            return
        }

        val encodedPassword = SecurityUtil.getSHA256bytes(password).let {
            SecurityUtil.bytesToHex(it)
        }
        d {"username: $username"}
        d {"encoded password: $encodedPassword"}
        d {"firebase token: $firebaseToken"}


        view.startLoadingSignIn(Loading(LOGIN_LOADING_TITLE, LOGIN_LOADING_MESSAGE))
        loginViewModel.async(
            authSource.signInUser(username, encodedPassword, firebaseToken, userServiceLocator)
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

        //debug
//        if (BuildConfig.DEBUG) {
//            view.startLoadingSignIn(Loading(LOGIN_LOADING_TITLE, LOGIN_LOADING_MESSAGE))
//            loginViewModel.async(
//                Completable.timer(1, TimeUnit.SECONDS)
//                    .subscribeOn(schedulerProvider.io())
//                    .observeOn(schedulerProvider.ui())
//                    .subscribe(
//                        {
//                            view.dismissLoading()
//                            view.navigateAfterLogin()
//                        },
//                        { t ->
//                            view.dismissLoading()
//                            view.showError(t)
//                        }
//                    )
//            )
//        }
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

                // save firebase id and token
                Prefs.putString(PREF_KEY_FIREBASE_ID, id)
                Prefs.putString(PREF_KEY_FIREBASE_TOKEN, token)
            }
    }
}

internal val USERNAME_PASSWORD_EMPTY = "Mohon masukkan username dan password yang benar"
internal val LOGIN_SUCCESS = "Login Berhasil"
internal val LOGIN_ERROR    = "Login Gagal"
internal val LOGIN_LOADING_TITLE = "Sign In"
internal val LOGIN_LOADING_MESSAGE = "Menunggu validasi akun"