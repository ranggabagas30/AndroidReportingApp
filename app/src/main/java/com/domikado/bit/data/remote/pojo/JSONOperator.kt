package com.domikado.bit.data.remote.pojo

data class JSONOperator(
    val company: String,
    val corrective_item_ids: List<Int>,
    val id: Int,
    val name: String,
    val without_form_item_ids: List<Any>
)