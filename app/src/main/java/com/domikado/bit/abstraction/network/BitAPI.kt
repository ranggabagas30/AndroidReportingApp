package com.domikado.bit.abstraction.network

import com.domikado.bit.data.remote.pojo.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

object BitAPI {

    private val service = BitRetrofit.createService(BitRemoteService::class.java)

    fun login(username: String, password: String, firebaseToken: String): Single<JSONLogin> {
        return service.login(username = username, password = password, firebaseToken = firebaseToken)
    }

    fun logout(userId: String, apiToken: String, firebaseToken: String): Single<JSONLogout> {
        return service.logout(userId = userId, apiToken = apiToken, firebaseId = firebaseToken)
    }

    fun getSchedules(userId: String, apiToken: String, firebaseToken: String): Single<JSONGetSchedule> {
        return service.getSchedules(userId = userId, apiToken = apiToken, firebaseId = firebaseToken)
    }

    fun getSiteDetail(userId: String, apiToken: String, idSiteMonitor: String): Single<JSONGetSite> =
        service.getSiteDetail(userId, apiToken, idSiteMonitor)

    fun checkIn(userId: String, apiToken: String, firebaseId: String, idSiteMonitor: String): Single<JSONCheckIn> =
        service.checkIn(userId = userId, apiToken = apiToken, firebaseId = firebaseId, idSiteMonitor = idSiteMonitor)

    fun uploadFormData(
        userId: String,
        apiToken: String,
        firebaseId: String,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part,
        textBody: HashMap<String, RequestBody>
    ): Completable =
        service.uploadFormData(userId = userId, apiToken = apiToken, firebaseId = firebaseId, image1 = image1, image2 = image2, textBody = textBody)

    internal val ERROR_DATA_IS_NULL = "Error: data is null"
}