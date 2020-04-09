package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Upload
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface IRemoteFormFillDataRepository {

    fun uploadData(
        userId: String,
        apiToken: String,
        firebaseId: String,
        imageBody: HashMap<String, MultipartBody.Part>,
        textBody: HashMap<String, RequestBody>
    ): Single<Upload>
}