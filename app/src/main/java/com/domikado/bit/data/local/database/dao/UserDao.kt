package com.domikado.bit.data.local.database.dao

import androidx.room.*
import com.domikado.bit.data.local.database.entity.TbUser
import com.domikado.bit.data.local.database.entity.USER
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Delete
    fun delete(user: TbUser): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(user: TbUser): Completable

    @Query("SELECT * FROM ${USER.TB_NAME} WHERE ${USER.ID} = :userId")
    fun getCurrentUser(userId: String): Single<TbUser>
}