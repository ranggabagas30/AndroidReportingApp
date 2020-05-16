package com.domikado.bit.external.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.domikado.bit.BuildConfig
import java.io.File
import java.io.OutputStream
import java.lang.Exception

object FileUtil {

    private const val childDir = "Bit"
    private val parentDir =
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            childDir
        )

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

    fun getPhotoDirByScheduleId(scheduleId: Int): File {
        return File(parentDir, scheduleId.toString())
    }

    fun getPhotoDirBySiteMonitorId(siteMonitorId: Int): File{
        return File(parentDir, siteMonitorId.toString())
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    fun insertImage(contentResolver: ContentResolver, source: Bitmap?, title: String, description: String): Uri? {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, title)
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, title)
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, description)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        // added meta data to make image is added at the front of gallery
        contentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())

        var uri: Uri? = null

        try {
            uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                if (source != null) {
                    val imageOut = contentResolver.openOutputStream(uri)
                    imageOut.use { out ->
                        source.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    }
                } else {
                    contentResolver.delete(uri, null, null)
                    uri = null
                }
            }
        } catch (e: Exception) {
            if (uri != null) {
                contentResolver.delete(uri, null, null);
                uri = null;
            }
        }

        return uri
    }
}