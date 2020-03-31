package com.domikado.bit.data.remote.formfill

import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.domain.repository.IRemoteFormFillDataRepository
import io.reactivex.Completable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteFormFillImpl(private val bitApi: BitAPI): IRemoteFormFillDataRepository {

    override fun uploadData(
        userId: String,
        apiToken: String,
        firebaseId: String,
        image1: MultipartBody.Part,
        image2: MultipartBody.Part,
        textBody: HashMap<String, RequestBody>
    ): Completable {
        return bitApi.uploadFormData(userId, apiToken, firebaseId, image1, image2, textBody)
    }
}