package com.domikado.bit.data.remoterepo.retrofit

import com.domikado.bit.data.remoterepo.pojo.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface BitRemoteService {

    // LOGIN
    @POST("login")
    fun login(
        @Header("Accept") accept: String = "application/json",
        @Header("Username") username: String,
        @Header("Password") password: String,
        @Header("Firebaseid") firebaseToken: String
    ): Single<JSONLogin>

    // LOGOUT
    @POST("logout")
    fun logout(
        @Header("Accept") accept: String = "application/json",
        @Header("Userid") userId: String,
        @Header("Apitoken") apiToken: String,
        @Header("Firebaseid") firebaseId: String
    ): Single<JSONLogout>

    // GET SCHEDULES
    @POST("v1/all/my-schedule")
    fun getSchedules(
        @Header("Accept") accept: String = "application/json",
        @Header("Userid") userId: String,
        @Header("Apitoken") apiToken: String,
        @Header("Firebaseid") firebaseId: String
    ): Single<JSONGetSchedule>

    // GET SITE DETAIL
    @POST("v1/my-site/{id_site_monitor}")
    fun getSiteDetail(
        @Header("user_id") userId: String,
        @Header("api_token") apiToken: String,
        @Path("id_site_monitor") idSiteMonitor: String
    ): Single<JSONGetSite>

    // CHECKIN
    @Multipart
    @POST("v1/check-in/site/{id_site_monitor}")
    fun checkIn(
        @Header("Accept") accept: String = "application/json",
        @Header("Userid") userId: String,
        @Header("Apitoken") apiToken: String,
        @Header("Firebaseid") firebaseId: String,
        @Path("id_site_monitor") idSiteMonitor: String,
        @PartMap textBody: Map<String, @JvmSuppressWildcards RequestBody>
    ): Single<JSONCheckIn>

    // UPLOAD FORM DATA
    @Multipart
    @POST("v1/upload/data")
    fun uploadFormData(
        @Header("Accept") accept: String = "application/json",
        @Header("Userid") userId: String,
        @Header("Apitoken") apiToken: String,
        @Header("Firebaseid") firebaseId: String,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
        @Part image5: MultipartBody.Part?,
        @PartMap textBody: Map<String, @JvmSuppressWildcards RequestBody?>
    ): Single<JSONUploadFormData>
}