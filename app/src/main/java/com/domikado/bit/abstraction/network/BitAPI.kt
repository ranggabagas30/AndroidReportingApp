package com.domikado.bit.abstraction.network

import com.domikado.bit.data.remote.pojo.JSONGetSchedule
import com.domikado.bit.data.remote.pojo.JSONUser
import io.reactivex.Single

object BitAPI {

    private val service = BitRetrofit.createService(BitRemoteService::class.java)

    fun login(username: String, password: String): Single<JSONUser> {
        return service.postLogin(username, password)
    }

    fun getSchedules(userId: String, template: String = "full"): Single<JSONGetSchedule> {
        return service.getSchedules(userId, template)
    }
}