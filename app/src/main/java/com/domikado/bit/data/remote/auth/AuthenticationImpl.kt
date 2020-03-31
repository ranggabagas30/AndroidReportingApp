package com.domikado.bit.data.remote.auth

import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.remote.pojo.JSONUser
import com.domikado.bit.domain.domainmodel.*
import com.domikado.bit.domain.repository.IAuthRepository
import io.reactivex.Single

class AuthenticationImpl(private val bitAPI: BitAPI): IAuthRepository {

    override fun signInUser(username: String, password: String, firebaseToken: String): Single<User> {
        return bitAPI.login(username, password, firebaseToken)
            .map { response ->
                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
                else {
                    response.data?.let(JSONUser::toUser)?:
                            throw BitThrowable.BitApiResponseException(BitAPI.ERROR_DATA_IS_NULL)
                }
            }
    }

    override fun signOutCurrentUser(userId: String, apiToken: String, firebaseToken: String): Single<Logout> {
        return bitAPI.logout(userId, apiToken, firebaseToken)
            .map { response ->
                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
                response.toLogout
            }
    }
}