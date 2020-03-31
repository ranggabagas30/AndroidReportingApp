package com.domikado.bit.domain.repository

import com.domikado.bit.domain.domainmodel.User
import io.reactivex.Completable

interface ILocalAuthRepository {

    fun saveUser(user: User): Completable

    fun getCurrentUser(): User? // load from sharedpref

    fun deleteUserSession()
}