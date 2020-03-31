package com.domikado.bit.domain.repository

import io.reactivex.Completable
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IRemoteFormFillDataRepository {

    fun uploadData(
        userId: String,
        apiToken: String,
        firebaseId: String,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part,
        textBody: HashMap<String, RequestBody>
    ): Completable
}