package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONUser

data class User(
    val id: String, // max 35 characters
    val username: String,
    val password: String? = null,
    val fullName: String?,
    val email: String?,
    val accessToken: String
)

internal val JSONUser.toUser
    get() = User(
        this.id.toString(),
        this.username,
        fullName = this.full_name,
        email = this.email,
        accessToken = this.persistence_token
    )