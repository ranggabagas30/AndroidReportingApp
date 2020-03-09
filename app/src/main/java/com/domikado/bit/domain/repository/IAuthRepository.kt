package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.User
import io.reactivex.Completable
import io.reactivex.Single

interface IAuthRepository {

    fun createCurrentUser(user: User): Completable

    fun getCurrentUser(): Single<User>

    fun signOutCurrentUser(): Completable

    //fun deleteUser(user: User): Completable

}