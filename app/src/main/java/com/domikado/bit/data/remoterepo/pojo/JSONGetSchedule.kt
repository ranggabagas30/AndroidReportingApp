package com.domikado.bit.data.remoterepo.pojo

data class JSONGetSchedule(
    val `data`: List<JSONSchedule>?,
    val message: String?,
    val status: Int
)