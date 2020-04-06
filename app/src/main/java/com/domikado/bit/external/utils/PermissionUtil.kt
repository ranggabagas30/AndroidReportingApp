package com.domikado.bit.external.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder


object PermissionUtil {

    fun hasPermissions(activity: Activity, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    fun shouldShowPermissionRationale(activity: Activity, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return true
        }
        return false
    }

    fun requestPermissions(activity: Activity, permissions: Array<out String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun requestPermissions(fragment: Fragment, permissions: Array<out String>, requestCode: Int) {
        fragment.requestPermissions(permissions, requestCode)
    }

    fun openAppPermissionSettings(context: Context) {
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            addCategory(Intent.CATEGORY_DEFAULT)
            data = Uri.parse("package:" + context.packageName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            context.startActivity(this)
        }
    }

    fun showPermissionRationaleDialog(
        context: Context,
        permissionRationale: String,
        onPositiveDialogClickListener: DialogInterface.OnClickListener,
        onNegativeDialogClickListener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(context)
            .setMessage(permissionRationale)
            .setPositiveButton("OK", onPositiveDialogClickListener)
            .setNegativeButton("Cancel", onNegativeDialogClickListener)
            .show()
    }
}