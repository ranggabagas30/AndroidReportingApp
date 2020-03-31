package com.domikado.bit.ui.screen.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.domikado.bit.R
import com.domikado.bit.abstraction.base.BaseActivity
import com.domikado.bit.domain.domainmodel.Loading
import com.domikado.bit.ui.screen.MainActivity
import com.domikado.bit.utility.makeText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), ILoginContract.View {

    private val loginEvent = MutableLiveData<LoginEvent<Nothing>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginViewModel: LoginViewModel by viewModels()
        loginViewModel
            .buildLoginLogic(this)

        login_btn_signin.setOnClickListener {
            loginEvent.value = LoginEvent.OnSignInButtonClick(
                login_tf_username.text.toString(),
                login_tf_password.text.toString()
            )
        }

        loginEvent.value = LoginEvent.OnCreate
    }

    override fun onStart() {
        super.onStart()
        loginEvent.value = LoginEvent.OnStart
    }

    override fun navigateAfterLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun startLoadingSignIn(loading: Loading) = showLoadingMessage(loading.title, loading.message)

    override fun dismissLoading() = hideLoading()

    override fun setObserver(observer: Observer<LoginEvent<Nothing>>) = loginEvent.observe(this, observer)

    override fun showError(t: Throwable) = makeText(t.message?: LOGIN_ERROR, Toast.LENGTH_LONG)

    override fun showMessageLoginSuccess(s: String) = makeText(s, Toast.LENGTH_SHORT)
}
