package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.User
import io.reactivex.Completable
import io.reactivex.Single

interface IAuthRepository {

    fun signInUser(username: String, password: String): Single<User>

    fun saveUser(user: User): Completable

    fun getCurrentUser(): User

    fun signOutCurrentUser(): Completable

    //fun deleteUser(user: User): Completable

}