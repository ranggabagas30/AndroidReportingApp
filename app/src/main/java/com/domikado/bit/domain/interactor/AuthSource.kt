package com.domikado.bit.domain.interactor

import com.domikado.bit.domain.domainmodel.BitThrowable
import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.servicelocator.UserServiceLocator
import io.reactivex.Completable

class AuthSource {

    fun signInUser(username: String, password: String, firebaseToken: String, locator: UserServiceLocator): Completable {
        return locator.authRepository.signInUser(username, password, firebaseToken)
            .flatMapCompletable { saveUser(it, locator) }
    }

    fun saveUser(user: User, locator: UserServiceLocator) =
        locator.localAuthRepository.saveUser(user)

    fun getCurrentUser(locator: UserServiceLocator) =
        locator.localAuthRepository.getCurrentUser()


    fun deleteUserSession(locator: UserServiceLocator) =
        locator.localAuthRepository.deleteUserSession()

    fun signOutCurrentUser(userId: String, apiToken: String, firebaseToken: String, locator: UserServiceLocator): Completable {
        return locator.authRepository.signOutCurrentUser(userId, apiToken, firebaseToken)
            .flatMapCompletable {
                if (it.status == 1) {
                    deleteUserSession(locator)
                    Completable.complete()
                }
                else Completable.error(BitThrowable.BitIllegalAuthException("Gagal melakukan logout"))
            }
    }
}