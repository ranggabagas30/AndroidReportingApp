package com.domikado.bit.domain.interactor

import android.text.TextUtils
import com.domikado.bit.domain.domainmodel.FormFillData
import com.domikado.bit.domain.domainmodel.Upload
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
import com.domikado.bit.external.createPart
import com.github.ajalt.timberkt.d
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FormFillSource {

    fun delete(listFormFillData: List<FormFillData>, locator: FormFillServiceLocator): Completable =
        locator.formFillDataRepository.delete(listFormFillData)

    fun delete(formFillModelId: Int, siteMonitorId: Int, locator: FormFillServiceLocator): Completable =
        locator.formFillDataRepository.delete(formFillModelId, siteMonitorId)

    fun addOrReplace(listFormFillData: List<FormFillData>, locator: FormFillServiceLocator): Completable =
        locator.formFillDataRepository.addOrUpdate(listFormFillData)

    fun getFormFillData(siteMonitorId: Int, locator: FormFillServiceLocator): Single<List<FormFillData>> =
        locator.formFillDataRepository.getFormFillData(siteMonitorId)

    fun getValidFormFillData(data: List<FormFillData>): Single<List<FormFillData>> {
        for (i in data.indices) {
            // cek jika pada satu item yang terisi remark, lengkap dengan gambar dan nilai itemvalue nya
            if (TextUtils.isEmpty(data[i].image))
                throw NullPointerException("Form ${data[i].title} tidak ada foto. Mohon lengkapi")

            if (TextUtils.isEmpty(data[i].remark))
                throw NullPointerException("Form ${data[i].title} tidak ada remark. Mohon lengkapi")

            if (TextUtils.isEmpty(data[i].itemValue))
                throw NullPointerException("Form ${data[i].title} tidak ada itemValue. Silahkan laporkan ke developer")

            // data latitude longitude item tidak boleh kosong
            if (data[i].latitude == null || data[i].longitude == null)
                throw NullPointerException("Tidak ada data gps location pada item ${data[i].title}")
        }
        return Single.just(data)
    }

    fun uploadData(userId: String, apiToken: String, firebaseId: String, siteMonitorId: Int, locator: FormFillServiceLocator): Single<Upload> =
        getFormFillData(siteMonitorId, locator)
            .flatMap { data -> getValidFormFillData(data) }
            .flatMap { data ->

                // penyiapan upload data body
                val textBody = hashMapOf(
                    "id_site_monitor" to siteMonitorId.toString().createPart
                )
                val imageBody = hashMapOf<String, MultipartBody.Part>()

                var latitude: Double? = null
                var longitude: Double? = null
                var latestModifietAt: Long = Long.MIN_VALUE

                for (i in data.indices) {
                    val formKe = data[i].formFillModelId + 1 // form fill model id is 0 based index
                    d { "formKe: $formKe" }
                    d { "data: ${data[i]}"}

                    // itemValue
                    textBody["item$formKe"] = data[i].itemValue!!.createPart

                    // remark
                    textBody["remark$formKe"] = data[i].remark!!.createPart

                    // image
                    val imageFile = File(data[i].image!!)
                    val reqFile = RequestBody.create(MediaType.parse("image/*"), imageFile)
                    val imageMultipartBody = MultipartBody.Part.createFormData("image$formKe", imageFile.name, reqFile)
                    imageBody["image$formKe"] = imageMultipartBody

                    // pick latest modified time lat long
                    data[i].modifiedAt?.also {
                        if (it >= latestModifietAt) { // ambil data lat long dengan modfied time terbaru
                            latitude = data[i].latitude
                            longitude = data[i].longitude
                            latestModifietAt = it
                        }
                    }
                }

                // gps lat long
                textBody["latitude"] = latitude.toString().createPart
                textBody["longitude"] = longitude.toString().createPart

                d { "textBody: $textBody"}
                d { "imageBody: $imageBody"}
                locator.remoteFormFillDataRepository.uploadData(userId, apiToken, firebaseId, imageBody, textBody)
            }

}