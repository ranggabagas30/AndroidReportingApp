package com.domikado.bit.data.remote.auth

import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.repository.IAuthRepository
import io.reactivex.Completable
import io.reactivex.Single

class AuthenticationImpl(): IAuthRepository {

    override fun signInUser(username: String, password: String): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // call sign in retrofit
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