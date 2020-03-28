package com.domikado.bit.data.remote.auth

import com.domikado.bit.abstraction.network.BitAPI
import com.domikado.bit.data.remote.pojo.JSONUser
import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.domainmodel.toUser
import com.domikado.bit.domain.repository.IAuthRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthenticationImpl(private val bitAPI: BitAPI): IAuthRepository {

    override fun signInUser(username: String, password: String): Single<User> {
        return bitAPI.login(username, password)
            .map { response ->
                if (response.status == 0) throw BitThrowable.BitApiResponseException(response.message)
                else {
                    response.data?.let(JSONUser::toUser)?:
                            throw BitThrowable.BitApiResponseException(BitAPI.ERROR_DATA_IS_NULL)
                }
            }
    }

    override fun saveUser(user: User): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentUser(): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // get current user from shared pref
    }

    override fun signOutCurrentUser(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        // signout user by hitting logout endpoint
        // delete user data on local prefs
    }
}