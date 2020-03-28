package com.domikado.bit.utility

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import com.domikado.bit.BuildConfig
import java.io.File

object FileUtil {

    private const val childDir = "Bit"
    private val parentDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), childDir)

    fun getUriFromFile(
        context: Context,
        file: File
    ): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileProvider", file)
        } else {
            Uri.fromFile(file)
        }
    }

    fun createOrUseDir(dir: File): File {
        val fileDir = File(dir.path)
        if (!fileDir.exists()) fileDir.mkdirs()
        return fileDir
    }

    fun createFileName() = "${System.currentTimeMillis()}-photo-"

    fun createImageFile(dir: File, fileName: String) = File.createTempFile(fileName, ".jpg", dir)

    fun getPhotoDir(scheduleId: Int): File {
        return File(parentDir, scheduleId.toString())
    }
}