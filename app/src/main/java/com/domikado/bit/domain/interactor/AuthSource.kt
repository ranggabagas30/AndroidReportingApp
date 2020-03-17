package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import io.reactivex.Completable
import io.reactivex.Single

class AuthSource {

    fun signInUser(username: String, password: String, locator: UserServiceLocator): Single<User>
            = locator.authRepository.signInUser(username, password)

    fun saveUser(user: User, locator: UserServiceLocator): Completable
            = locator.authRepository.saveUser(user)

    fun getCurrentUser(locator: UserServiceLocator): User
            = locator.authRepository.getCurrentUser()

    fun signOutCurrentUser(locator: UserServiceLocator): Completable
            = locator.authRepository.signOutCurrentUser()
}