package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.local.database.entity.TbUser
import com.domikado.bit.data.remote.pojo.JSONUser

data class User(
    val id: String, // max 35 characters
    val username: String,
    val password: String,
    val fullName: String?,
    val accessToken: String
)

internal val User.toTbUser
    get() = TbUser(
        this.id,
        this.username,
        this.fullName,
        this.accessToken
    )

internal val JSONUser.toUser
    get() = User( // local user id
        this.id,
        this.username,
        this.api_token,
        this.name,
        this.api_token
    )

internal val TbUser.toUser
    get() = User(
        this.id,
        this.username,
        this.accessToken,
        this.fullName,
        this.accessToken
    )