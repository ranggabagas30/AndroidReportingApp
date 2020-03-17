package com.domikado.bit.abstraction.network

import com.domikado.bit.data.remote.pojo.JSONGetSchedule
import com.domikado.bit.data.remote.pojo.JSONUser
import io.reactivex.Single
import retrofit2.http.*

interface BitRemoteService {

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String?
    ): Single<JSONUser>

    @GET("users/{userId}/schedules")
    fun getSchedules(
        @Path("userId") userId: String,
        @Query("template") template: String
    ): Single<JSONGetSchedule>
}