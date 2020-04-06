package com.domikado.bit.utility

import android.text.TextUtils

object AuthUtil {

    fun isFirebaseTokenValid(firebaseToken: String?): Boolean {
        return !TextUtils.isEmpty(firebaseToken)
    }

    fun isUsernameValid(username: String?): Boolean {
        return !TextUtils.isEmpty(username)
    }

    fun isPasswordValid(password: String?): Boolean {
        return !TextUtils.isEmpty(password)
    }
}