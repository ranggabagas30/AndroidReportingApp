package com.domikado.bit.ui.common.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.domikado.bit.R
import com.domikado.bit.utility.FileUtil
import com.domikado.bit.utility.GpsUtils
import com.domikado.bit.utility.PermissionUtil
import com.domikado.bit.utility.makeText
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
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val permissionRationale = "Mohon izinkan untuk melanjutkan pengambilan foto"
    private val scheduleIdEmpty = "Gagal menyimpan foto. Schedule id kosong"

    private val gpsUtils by lazy { 
        GpsUtils(this)
    }

    private val cameraViewModel: CameraViewModel by viewModels()

    companion object {
        const val RESULT_IMAGE_FILE = "RESULT_IMAGE_FILE"
        const val RESULT_FORM_FILL_ID = "RESULT_FORM_FILL_ID"
        const val RESULT_POSITION = "RESULT_POSITION"
        const val EXTRA_SCHEDULE_ID = "EXTRA_SCHEDULE_ID"
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
            PermissionUtil.shouldShowPermissionRationale(this, permissions) -> Toast.makeText(this, permissionRationale, Toast.LENGTH_LONG).show()
            PermissionUtil.hasPermissions(this, permissions) -> cameraView.setLifecycleOwner(this)
            else -> PermissionUtil.requestPermissions(this, permissions, RC_PERMISSION_TAKE_PICTURE)
        }
    }
    
    private fun invokeActionCapture() {
        when {
            !gpsUtils.isGpsOn() -> gpsUtils.askTurnOnGps()
            PermissionUtil.shouldShowPermissionRationale(this, permissions) -> Toast.makeText(this, permissionRationale, Toast.LENGTH_LONG).show()
            PermissionUtil.hasPermissions(this, permissions) -> cameraView.setLifecycleOwner(this)
            else -> PermissionUtil.requestPermissions(this, permissions, RC_PERMISSION_SAVE_PICTURE)
        }
    }

    private val cameraListener = object : CameraListener() {
        override fun onPictureTaken(result: PictureResult) {
            val scheduleId: String? = cameraViewModel.args?.getString(EXTRA_SCHEDULE_ID, null)
            if (scheduleId == null) {

                // if schedule id is null, then cancel
                makeText(scheduleIdEmpty, Toast.LENGTH_LONG)
                setResult(Activity.RESULT_CANCELED)
                finish()
                return
            }

            try {
                FileUtil.getPhotoDir(scheduleId.toInt()).also { photoDir ->
                    FileUtil.createOrUseDir(photoDir).also { path ->
                        val imageFile = FileUtil.createImageFile(path, "${FileUtil.createFileName()}.jpg")

                        // save image file asynchronously
                        result.toFile(imageFile) {
                            if (it == null) throw NullPointerException("periksa izin penyimpanan file ke storage")
                            Intent().apply {

                                // sent result image file back to caller activity
                                putExtra(RESULT_IMAGE_FILE, it)
                                setResult(Activity.RESULT_OK, this)
                                finish()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
                makeText("Gagal menyimpan foto: ${e.message}", Toast.LENGTH_LONG)
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
}
