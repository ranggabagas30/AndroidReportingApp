package com.domikado.bit.ui.screen.setting

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseActivity
import com.domikado.bit.abstraction.notification.NotificationHelper
import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.ui.screen.login.LoginActivity
import com.domikado.bit.utility.PREF_KEY_FIREBASE_TOKEN
import com.domikado.bit.utility.PREF_KEY_USER
import com.domikado.bit.utility.makeText
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : BaseActivity(), Observer<SettingEvent> {

    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        settingViewModel.settingEvent.observe(this, this)

        setting_btn_notification.setOnClickListener {
            testNotify()
        }
        setting_btn_logout.setOnClickListener {
            doLogout()
        }
    }

    override fun onChanged(e: SettingEvent?) {
        when(e) {
            is SettingEvent.OnLogout -> onLogout()
            is SettingEvent.OnSuccessLogout -> onSuccessLogout()
            is SettingEvent.OnFailedLogout -> onFailedLogout(e.t)
        }
    }

    private fun onLogout() {
        showLoadingMessage("Sign out", "Mohon tunggu")
    }

    private fun onSuccessLogout() {
        hideLoading()
        navigateAfterLogout()
    }

    private fun onFailedLogout(t: Throwable) {
        hideLoading()
        makeText("Error logout: ${t.message}", Toast.LENGTH_LONG)
    }

    private fun testNotify() {
        val title = setting_notification_title.text.toString()
        val message = setting_notification_message.text.toString()
        NotificationHelper.notifyRejection(this, NotificationHelper.DEFAULT_NAME, title, message)
    }

    private fun doLogout() {
        val userPrefs = Prefs.getString(PREF_KEY_USER, null)
        val firebaseToken = Prefs.getString(PREF_KEY_FIREBASE_TOKEN, null)

        if (TextUtils.isEmpty(userPrefs) || TextUtils.isEmpty(firebaseToken)) {
            navigateAfterLogout()
            return
        }

        val user = Gson().fromJson(userPrefs, User::class.java)
        settingViewModel.logout(user.id, user.accessToken, firebaseToken)
    }

    private fun navigateAfterLogout() {
        Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
            finish()
        }
    }
}
