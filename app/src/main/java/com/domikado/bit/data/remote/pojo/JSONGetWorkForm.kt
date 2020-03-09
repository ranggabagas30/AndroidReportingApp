package com.domikado.bit.data.remote.pojo

data class JSONGetWorkForm(
    val `data`: List<JSONWorkForm>,
    val page: JSONPage,
    val respond_in: String,
    val status: Int,
    val status_code: String
)