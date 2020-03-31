package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = USER.TB_NAME
)
data class TbUser(

    @PrimaryKey
    @ColumnInfo(name = USER.ID)
    val id: String,

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
    const val USERNAME = "username"
    const val FULLNAME = "full_name"
    const val ACCESS_TOKEN = "access_token"
}