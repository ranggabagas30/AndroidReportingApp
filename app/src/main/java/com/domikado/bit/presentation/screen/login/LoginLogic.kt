package com.domikado.bit.presentation.screen.login

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.domikado.bit.abstraction.base.BaseLogic
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import com.domikado.bit.external.PREF_KEY_FIREBASE_ID
import com.domikado.bit.external.PREF_KEY_FIREBASE_TOKEN
import com.domikado.bit.external.PREF_KEY_USER
import com.domikado.bit.external.utils.AuthUtil
import com.domikado.bit.external.utils.DebugUtil
import com.domikado.bit.external.utils.SecurityUtil
import com.github.ajalt.timberkt.Timber.d
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
        }
    }

    private fun onCreate() {}

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
            view.showError(BitThrowable.BitFirebaseException(FIREBASE_TOKEN_KOSONG))
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
    }

    private fun getFirebaseIdAndToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    DebugUtil.getReadableErrorMessage(task.exception?.cause?: BitThrowable.BitFirebaseException())
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

internal const val USERNAME_PASSWORD_EMPTY = "Mohon masukkan username dan password yang benar"
internal const val LOGIN_SUCCESS = "Login Berhasil"
internal const val LOGIN_ERROR    = "Login Gagal"
internal const val LOGIN_LOADING_TITLE = "Sign In"
internal const val LOGIN_LOADING_MESSAGE = "Menunggu validasi akun"
internal const val FIREBASE_TOKEN_KOSONG = "Firebase token kosong"