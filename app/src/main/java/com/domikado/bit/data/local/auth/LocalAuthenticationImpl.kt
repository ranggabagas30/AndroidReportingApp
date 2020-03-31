package com.domikado.bit.data.local.auth

import com.domikado.bit.data.local.database.dao.UserDao
import com.domikado.bit.domain.domainmodel.User
import com.domikado.bit.domain.domainmodel.toTbUser
import com.domikado.bit.domain.repository.ILocalAuthRepository
import com.domikado.bit.utility.PREF_KEY_USER
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.Completable

class LocalAuthenticationImpl(private val userDao: UserDao): ILocalAuthRepository {

    override fun saveUser(user: User): Completable {
        return userDao.insertOrReplace(user.toTbUser)
            .doOnComplete {
                val isExist = Prefs.contains(PREF_KEY_USER)
                if (!isExist){
                    val userPref = Gson().toJson(user, User::class.java)
                    Prefs.putString(PREF_KEY_USER, userPref)
                }
            }
    }

    override fun getCurrentUser(): User? {
        val userPref = Prefs.getString(PREF_KEY_USER, null)
        return Gson().fromJson(userPref, User::class.java)
    }

    override fun deleteUserSession() {
        Prefs.remove(PREF_KEY_USER)
    }
}