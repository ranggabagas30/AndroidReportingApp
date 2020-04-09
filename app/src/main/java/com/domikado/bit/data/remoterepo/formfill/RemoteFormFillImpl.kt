package com.domikado.bit.data.remoterepo.formfill

import com.domikado.bit.data.remoterepo.retrofit.BitAPI
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.Upload
import com.domikado.bit.domain.domainmodel.toUpload
import com.domikado.bit.domain.repository.IRemoteFormFillDataRepository
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteFormFillImpl(private val bitApi: BitAPI): IRemoteFormFillDataRepository {

    override fun uploadData(
        userId: String,
        apiToken: String,
        firebaseId: String,
        imageBody: HashMap<String, MultipartBody.Part>,
        textBody: HashMap<String, RequestBody>
    ): Single<Upload> {
        return bitApi.uploadFormData(userId, apiToken, firebaseId, imageBody, textBody)
            .map { response ->
                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
                else {
                    if (response.data == null) throw BitThrowable.BitApiResponseException("Response API upload kosong")
                    response.toUpload
                }
            }
    }
}