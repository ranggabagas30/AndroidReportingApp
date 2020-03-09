package com.domikado.bit.domain.domainmodel

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val fullName: String?,
    val email: String?,
    val accessToken: String
)