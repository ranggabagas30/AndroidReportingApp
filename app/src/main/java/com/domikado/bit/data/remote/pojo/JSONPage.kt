package com.domikado.bit.data.remote.pojo

data class JSONPage(
    val current: Int,
    val limit: Int,
    val records: Int,
    val total: Int
)