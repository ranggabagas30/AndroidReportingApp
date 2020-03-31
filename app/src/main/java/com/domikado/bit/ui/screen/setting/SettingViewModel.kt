package com.domikado.bit.ui.screen.setting

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.domikado.bit.abstraction.base.BaseAndroidViewModel
import com.domikado.bit.abstraction.database.BitDatabase
import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.abstraction.rx.AppSchedulerProvider
import com.domikado.bit.data.local.auth.LocalAuthenticationImpl
import com.domikado.bit.data.remote.auth.AuthenticationImpl
import com.domikado.bit.domain.interactor.AuthSource
import com.domikado.bit.domain.servicelocator.UserServiceLocator

class SettingViewModel(application: Application): BaseAndroidViewModel(application) {

    private val scheduleProvider by lazy {
        AppSchedulerProvider()
    }

    private val userDao by lazy {
        BitDatabase.getInstance(application).userDao()
    }

    private val localAuth by lazy {
        LocalAuthenticationImpl(userDao)
    }

    private val bitApi by lazy {
        BitAPI
    }

    private val auth by lazy {
        AuthenticationImpl(bitApi)
    }

    private val userServiceLocator by lazy {
        UserServiceLocator(localAuth, auth)
    }

    private val authSource by lazy {
        AuthSource()
    }

    val settingEvent by lazy { MutableLiveData<SettingEvent>() }

    fun logout(userId: String, apiToken: String, firebaseToken: String) {
        settingEvent.value = SettingEvent.OnLogout
        async(
            authSource.signOutCurrentUser(userId, apiToken, firebaseToken, userServiceLocator)
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribe({
                    settingEvent.value = SettingEvent.OnSuccessLogout
                }, {
                    settingEvent.value = SettingEvent.OnFailedLogout(it)
                })
        )
    }
}

sealed class SettingEvent {
    object OnLogout: SettingEvent()
    object OnSuccessLogout: SettingEvent()
    data class OnFailedLogout(val t: Throwable): SettingEvent()
}