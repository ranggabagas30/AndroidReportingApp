package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.Logout
import com.domikado.bit.domain.domainmodel.User
import io.reactivex.Single

interface IAuthRepository {

    fun signInUser(username: String, password: String, firebaseToken: String): Single<User>

    fun signOutCurrentUser(userId: String, apiToken: String, firebaseToken: String): Single<Logout>

}