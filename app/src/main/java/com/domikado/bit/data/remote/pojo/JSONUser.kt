package com.domikado.bit.data.remote.pojo

data class JSONUser(
    val created_at: String,
    val email: String,
    val full_name: String,
    val id: Int,
    val persistence_token: String,
    val role_name: String,
    val updated_at: String,
    val username: String
)