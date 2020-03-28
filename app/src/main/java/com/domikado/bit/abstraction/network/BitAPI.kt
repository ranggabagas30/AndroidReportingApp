package com.domikado.bit.abstraction.network

import com.domikado.bit.data.remote.pojo.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

object BitAPI {

    private val service = BitRetrofit.createService(BitRemoteService::class.java)

    fun login(username: String, password: String): Single<JSONLogin> {
        return service.login(username, password)
    }

    fun getSchedules(userId: String, apiToken: String): Single<JSONGetSchedule> {
        return service.getSchedules(userId, apiToken)
    }

    fun getSiteDetail(userId: String, apiToken: String, idSiteMonitor: String): Single<JSONGetSite> =
        service.getSiteDetail(userId, apiToken, idSiteMonitor)

    fun checkIn(userId: String, apiToken: String, idSiteMonitor: String): Single<JSONCheckIn> =
        service.checkIn(userId, apiToken, idSiteMonitor)

    fun uploadFormData(
        userId: String,
        apiToken: String,
        idSiteMonitor: String,
        image1: MultipartBody.Part,
        item1: RequestBody,
        remark1: RequestBody,
        image2: MultipartBody.Part,
        item2: RequestBody,
        remark2: RequestBody
    ): Single<JSONUploadFormData> =
        service.uploadFormData(userId, apiToken, idSiteMonitor, image1, item1, remark1, image2, item2, remark2)

    internal val ERROR_DATA_IS_NULL = "Error: data is null"
}