package com.domikado.bit.external

import android.app.Activity
import android.widget.Toast
import okhttp3.MultipartBody
import okhttp3.RequestBody

internal fun Activity.makeText(message: String, duration: Int) =
    Toast.makeText(this, message, duration).show()

internal val String.createPart
    get() = RequestBody.create(
        MultipartBody.FORM, this
    )