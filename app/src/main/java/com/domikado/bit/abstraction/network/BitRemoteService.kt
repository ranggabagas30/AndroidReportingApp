package com.domikado.bit.abstraction.network

import com.domikado.bit.data.remote.pojo.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface BitRemoteService {

    // LOGIN
    @POST("login")
    fun login(
        @Header("username") username: String,
        @Header("password") password: String?
    ): Single<JSONLogin>

    // GET SCHEDULES
    @POST("v1/all/my-schedule")
    fun getSchedules(
        @Header("user_id") userId: String,
        @Header("api_token") apiToken: String
    ): Single<JSONGetSchedule>

    // GET SITE DETAIL
    @POST("v1/my-site/{id_site_monitor}")
    fun getSiteDetail(
        @Header("user_id") userId: String,
        @Header("api_token") apiToken: String,
        @Path("id_site_monitor") idSiteMonitor: String
    ): Single<JSONGetSite>

    // CHECKIN
    @POST("v1/check-in/site/{id_site_monitor}")
    fun checkIn(
        @Header("user_id") userId: String,
        @Header("api_token") apiToken: String,
        @Path("id_site_monitor") idSiteMonitor: String
    ): Single<JSONCheckIn>

    // UPLOAD FORM DATA
    @Multipart
    @POST("v1/upload/data")
    fun uploadFormData(
        @Header("user_id") userId: String,
        @Header("api_token") apiToken: String,
        @Part("id_site_monitor") idSiteMonitor: String,
        @Part image1: MultipartBody.Part,
        @Part("item1") item1: RequestBody,
        @Part("remark1") remark1: RequestBody,
        @Part image2: MultipartBody.Part,
        @Part("item2") item2: RequestBody,
        @Part("remark2") remark2: RequestBody
    ): Single<JSONUploadFormData>
}