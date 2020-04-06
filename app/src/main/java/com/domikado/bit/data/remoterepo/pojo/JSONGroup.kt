package com.domikado.bit.data.remoterepo.pojo

data class JSONGroup(
    val ancestry: Any,
    val created_at: String,
    val description: String,
    val form_version: String,
    val id: Int,
    val name: String,
    val parent_id: Any,
    val parent_name: String,
    val position: Any,
    val root_id: Int,
    val root_name: String,
    val table: JSONTable,
    val updated_at: String,
    val version: String,
    val work_form: JSONWorkForm,
    val work_form_id: Int
)