package com.domikado.bit.ui.common.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.domikado.bit.R
import com.domikado.bit.domain.domainmodel.Result
import com.domikado.bit.utility.*
import com.github.ajalt.timberkt.Timber
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity() {

    private val RC_PERMISSION_TAKE_PICTURE = 1
    private val RC_PERMISSION_SAVE_PICTURE = 2

    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE, 
        Manifest.permission.CAMERA, 
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val permissionRationale = "Mohon izinkan untuk melanjutkan pengambilan foto"
    private val scheduleIdEmpty = "Schedule id kosong"

    private val gpsUtils by lazy { 
        GpsUtils(this)
    }

    private val cameraViewModel: CameraViewModel by viewModels()

    companion object {
        const val RESULT_IMAGE_FILE = "RESULT_IMAGE_FILE"
        const val EXTRA_SCHEDULE_ID = "EXTRA_SCHEDULE_ID"
        const val EXTRA_SITE_LATITUDE = "EXTRA_SITE_LATITUDE"
        const val EXTRA_SITE_LONGITUDE = "EXTRA_SITE_LONGITUDE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraViewModel.args = intent.extras

        btnTakePhoto.setOnClickListener { 
            invokeActionCapture()
        }

        cameraView.addCameraListener(cameraListener)
    }

    override fun onResume() {
        super.onResume()
        invokeActionCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            RC_PERMISSION_TAKE_PICTURE -> invokeActionCamera()
            RC_PERMISSION_SAVE_PICTURE -> invokeActionCapture()
        }
    }

    private fun invokeActionCamera() {
        when {
            !gpsUtils.isGpsOn() -> gpsUtils.askTurnOnGps()
            PermissionUtil.hasPermissions(this, permissions) -> {
                cameraView.setLifecycleOwner(this)
                startLocationUpdates()
            } else -> PermissionUtil.requestPermissions(this, permissions, RC_PERMISSION_TAKE_PICTURE)
        }
    }
    
    private fun invokeActionCapture() {
        when {
            !gpsUtils.isGpsOn() -> gpsUtils.askTurnOnGps()
            PermissionUtil.hasPermissions(this, permissions) -> cameraView.takePictureSnapshot()
            else -> PermissionUtil.requestPermissions(this, permissions, RC_PERMISSION_SAVE_PICTURE)
        }
    }

    private fun startLocationUpdates() {
        println("start location updates")
        cameraViewModel.locationService.observe(this, Observer {
            try {
                when(it) {
                    is Result.Value -> {
                        val location = it.value
                        if (location != null) {
                            updateWatermarkValue(location)
                        }
                    }
                    is Result.Error -> {
                        throw it.error
                    }
                }
            } catch (t: Throwable) {
                Timber.e(t) { "error location"}
                makeText("Error location: ${t.message}", Toast.LENGTH_LONG)
            }
        })
    }

    private fun updateWatermarkValue(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        val siteLatitude = cameraViewModel.args?.getString(EXTRA_SITE_LATITUDE, null)
        val siteLongitude = cameraViewModel.args?.getString(EXTRA_SITE_LONGITUDE, null)

        watermark_location.text = "Lat: $latitude, Long: $longitude"
        watermark_distance.text = if (TextUtils.isEmpty(siteLatitude) || TextUtils.isEmpty(siteLongitude)) {
            "N/A. Tidak ada data site latitude atau longitude"
        } else {
            val siteLocation = Location(LocationManager.GPS_PROVIDER).apply {
                this.latitude = siteLatitude!!.toDouble()
                this.longitude = siteLongitude!!.toDouble()
            }
            "Jarak ke site: ${siteLocation.distanceTo(location)} meter"
        }
        val date = DateUtil.getDateTimeNow()
        watermark_photodate.text = "Tgl photo: ${date.dayOfMonth}/${date.monthValue}/${date.year}"
    }

    private val cameraListener = object : CameraListener() {
        override fun onPictureTaken(result: PictureResult) {

            val scheduleId: Int? = cameraViewModel.args?.getInt(EXTRA_SCHEDULE_ID, -1)

            try {
                if (scheduleId == null) throw NullPointerException(scheduleIdEmpty)
                if (scheduleId == -1) throw IllegalArgumentException("Schedule id tidak ditemukan")
                savePhoto(scheduleId.toString(), result)
            } catch (e: Exception) {
                Timber.e(e)
                makeText("Gagal menyimpan foto: ${e.message}", Toast.LENGTH_LONG)
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }

    private fun savePhoto(scheduleId: String, result: PictureResult) {
        FileUtil.getPhotoDir(scheduleId.toInt()).also { photoDir ->
            FileUtil.createOrUseDir(photoDir).also { path ->

                // create image file
                val imageFile = FileUtil.createImageFile(path, FileUtil.createFileName())

                // save image file asynchronously
                result.toFile(imageFile) {
                    if (it == null) throw IllegalAccessException("periksa izin penyimpanan file ke storage")

                    Intent().apply {

                        // sent result image file back to caller activity
                        putExtra(RESULT_IMAGE_FILE, it)
                        setResult(Activity.RESULT_OK, this)
                        finish()
                    }
                }
            }
        }
    }
}
