package com.domikado.bit.external.location

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.domikado.bit.domain.domainmodel.Result
import com.github.ajalt.timberkt.Timber
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationService(context: Context): LiveData<Result<Throwable, Location?>>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun onInactive() { // is called when the lifecycle owner(LocationActivity) is either paused, stopped or destroyed
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onActive() { // is called when the lifecycle owner(LocationActivity) is either started or resumed
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { setLocationData(it) }
            .addOnFailureListener {t -> setErrorData(t) }

        startLocationUpdates()
    }

    private fun setLocationData(location: Location?) { // location is nullable as if GPS is turned off even if the last
                                                        // location was previously retrieve because disabling location clears the cache
        //value = LocationEvent.OnLocationResult(Result.build { location })
        value = Result.build { location }
    }

    private fun setErrorData(t: Throwable) {
        //value = LocationEvent.OnLocationResult(Result.build { throw t })
        value = Result.build { throw t }
    }

    private fun startLocationUpdates() {
        Timber.d { "start location updates" }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null // looper
        )
    }

    companion object {
        private const val INTERVAL: Long = 5 * 1000
        private const val FASTEST_INTERVAL: Long = 3 * 1000
        val locationRequest = LocationRequest.create().apply {
            interval =
                INTERVAL
            fastestInterval =
                FASTEST_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?: return
            for (location in locationResult.locations) setLocationData(location)
        }
    }
}

//sealed class LocationEvent {
//    data class OnLocationResult(val result: Result<Throwable, Location?>): LocationEvent()
//}