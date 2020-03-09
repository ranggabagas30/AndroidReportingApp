package com.domikado.bit.data.remote.pojo

data class JSONRow(
    val ancestry: String,
    val id: Int,
    val parent_id: Int,
    val position: Int,
    val report_position: Int,
    val row_columns: List<JSONRowColumn>,
    val work_form_group_id: Int
)