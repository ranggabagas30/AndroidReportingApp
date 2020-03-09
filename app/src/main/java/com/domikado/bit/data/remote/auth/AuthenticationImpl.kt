package com.domikado.bit.data.remote.auth

import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.repository.IAuthRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthenticationImpl(): IAuthRepository {

    override fun createCurrentUser(user: User): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentUser(): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signOutCurrentUser(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}