package com.domikado.bit.data.remote.pojo

data class JSONGetSchedule(
    val `data`: List<JSONSchedule>?,
    val message: String?,
    val status: Int
)