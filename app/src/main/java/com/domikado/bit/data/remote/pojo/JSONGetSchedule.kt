package com.domikado.bit.data.remote.pojo

data class JSONGetSchedule(
    val `data`: List<JSONSchedule>,
    val page: JSONPage,
    val respond_in: String,
    val status: Int,
    val status_code: String
)