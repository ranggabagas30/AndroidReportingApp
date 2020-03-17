package com.domikado.bit.abstraction.base

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
    }

    override fun onStop() {
        super.onStop()
        hideLoading()
    }

    fun showLoadingMessage(title: String?, message: String) {
        title?.let {
            progressDialog.setTitle(it)
        }

        progressDialog.setMessage(message)
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun hideLoading() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }
}