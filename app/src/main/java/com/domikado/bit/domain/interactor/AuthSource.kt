package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import io.reactivex.Completable
import io.reactivex.Single

class AuthSource {

    fun createCurrentUser(user: User, locator: UserServiceLocator): Completable
            = locator.authRepository.createCurrentUser(user)

    fun getCurrentUser(locator: UserServiceLocator): Single<User>
            = locator.authRepository.getCurrentUser()

    fun signOutCurrentUser(locator: UserServiceLocator): Completable
            = locator.authRepository.signOutCurrentUser()
}