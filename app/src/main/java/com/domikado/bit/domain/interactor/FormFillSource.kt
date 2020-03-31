package com.domikado.bit.domain.interactor

import android.text.TextUtils
import com.domikado.bit.domain.domainmodel.FormFillData
import com.domikado.bit.domain.servicelocator.FormFillServiceLocator
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

    fun uploadData(userId: String, apiToken: String, firebaseId: String, siteMonitorId: Int, locator: FormFillServiceLocator): Completable =
        getFormFillData(siteMonitorId, locator)
            .flatMapCompletable { data ->
                if (TextUtils.isEmpty(data[0].image) && TextUtils.isEmpty(data[0].remark)) throw NullPointerException("Data form ${data[0].title} ada yang kosong. Mohon dilengkapi")
                if (TextUtils.isEmpty(data[1].image) && TextUtils.isEmpty(data[1].remark)) throw NullPointerException("Data form ${data[1].title} ada yang kosong. Mohon dilengkapi")

                val fileImage1 = File(data[0].image!!)
                val reqFile1 = RequestBody.create(MediaType.parse("image/*"), fileImage1)
                val image1 = MultipartBody.Part.createFormData("image1", fileImage1.name, reqFile1)

                val fileImage2 = File(data[1].image!!)
                val reqFile2 = RequestBody.create(MediaType.parse("image/*"), fileImage2)
                val image2 = MultipartBody.Part.createFormData("image2", fileImage2.name, reqFile2)

                val textBodyMap = hashMapOf(
                    "id_site_monitor" to createPartFromString(siteMonitorId.toString()),
                    "item1" to createPartFromString(""),
                    "remark1" to createPartFromString(data[0].remark!!),
                    "item2" to createPartFromString(""),
                    "remark2" to createPartFromString(data[1].remark!!),
                    "latitude" to createPartFromString("-6.239"),
                    "longitude" to createPartFromString( "106.123")
                )
                locator.remoteFormFillDataRepository.uploadData(userId, apiToken, firebaseId, image1, image2, textBodyMap)
            }

    private fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, descriptionString
        )
    }

}