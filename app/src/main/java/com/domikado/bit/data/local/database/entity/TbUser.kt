package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = USER.TB_NAME
)
data class TbUser(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER.ID)
    val id: Int = 0,

    @ColumnInfo(name = USER.REMOTE_USER_ID)
    val remoteUserId: String,

    @ColumnInfo(name = USER.USERNAME)
    val username: String,

//    @ColumnInfo(name = USER.PASSWORD)
//    val password: String,

    @ColumnInfo(name = USER.FULLNAME)
    val fullName: String?,

//    @ColumnInfo(name = USER.EMAIL)
//    val email: String?,

    @ColumnInfo(name = USER.ACCESS_TOKEN)
    val accessToken: String
)

object USER {
    const val TB_NAME = "user"
    const val ID = "id"
    const val REMOTE_USER_ID = "remote_user_id"
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val FULLNAME = "full_name"
    const val EMAIL    = "email"
    const val ACCESS_TOKEN = "access_token"
}