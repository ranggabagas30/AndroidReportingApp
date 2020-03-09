package com.domikado.bit.data.remote.pojo

data class JSONTable(
    val headers: List<JSONHeader>,
    val rows: List<JSONRow>
)