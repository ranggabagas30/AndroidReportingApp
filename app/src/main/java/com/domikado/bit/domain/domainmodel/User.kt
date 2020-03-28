package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONUser

data class User(
    val id: String, // max 35 characters
    val username: String,
    val password: String,
    val fullName: String?,
    val email: String? = null,
    val accessToken: String
)

internal val JSONUser.toUser
    get() = User(
        this.id,
        this.username,
        this.api_token,
        this.name,
        accessToken = this.api_token
    )