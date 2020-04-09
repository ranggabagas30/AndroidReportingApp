package com.domikado.bit.external.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import com.domikado.bit.external.location.LocationService
import com.github.ajalt.timberkt.Timber
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 *
 * ref : https://github.com/mayowa-egbewunmi/LocationUpdateWithLiveData/blob/master/app/src/main/java/com/mayowa/android/locationwithlivedata/GpsUtils.kt
 */
class GpsUtils(private val activity: Activity) {

    private val settingsClient: SettingsClient = LocationServices.getSettingsClient(activity)
    private val locationSettingsRequest: LocationSettingsRequest?
    private val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    companion object {
        const val GPS_REQUEST = 1000
    }

    init {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationService.locationRequest)
        locationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)
    }

    fun turnGPSOn(OnGpsListener: OnGpsListener?) {

        if (isGpsOn()) {
            OnGpsListener?.gpsStatus(true)
        } else {
            settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(activity) {
                    //  GPS is already enable, callback GPS status through listener
                    OnGpsListener?.gpsStatus(true)
                }
                .addOnFailureListener(activity) { e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(activity,
                                    GPS_REQUEST
                                )

//                                activity.startIntentSenderForResult(
//                                    rae.resolution.intentSender,
//                                    GPS_REQUEST,
//                                    null, 0, 0, 0, null
//                                )
                            } catch (sie: IntentSender.SendIntentException) {
                                Timber.i(sie) { "PendingIntent unable to execute request." }
                            }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage =
                                "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            Timber.e { errorMessage }
                            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }

    fun askTurnOnGps() {
        MaterialAlertDialogBuilder(activity)
            .setCancelable(false)
            .setMessage("Mohon izinkan penggunaan GPS location untuk melanjutkan")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
            .setNegativeButton("BATAL") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun isGpsOn() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    interface OnGpsListener {
        fun gpsStatus(isGPSEnable: Boolean)
    }
}