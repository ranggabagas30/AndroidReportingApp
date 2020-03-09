package com.domikado.bit.data.remote.pojo

data class JSONItem(
    val default_value: Any,
    val description: Any,
    val disable: Boolean,
    val expand: Boolean,
    val field_type: String,
    val id: Int,
    val label: String,
    val label_key: Any,
    val mandatory: Boolean,
    val periodic: Int,
    val picture: Any,
    val position: Int,
    val scope_type: String,
    val search: Boolean,
    val visible: Boolean,
    val work_form_group_id: Int,
    val work_form_row_column_id: Int
)