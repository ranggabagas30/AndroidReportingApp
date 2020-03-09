package com.domikado.bit.data.remote.pojo

data class JSONRowColumn(
    val column_id: Int,
    val id: Int,
    val items: List<JSONItem>,
    val row_id: Int,
    val work_form_group_id: Int
)