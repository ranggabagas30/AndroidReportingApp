package com.domikado.bit.data.remote.pojo

data class JSONSchedule(
    val id: Int,
    val user: JSONUser,
    val operator_number: Int,
    val operators: List<JSONOperator>,
    val periode: String,
    val progress: Int,
    val rejection: JSONRejection,
    val schedule_values: List<JSONScheduleValue>,
    val site: JSONSite,
    val site_id: Int,
    val status: String,
    val total_fields: Any,
    val created_at: String,
    val updated_at: String,
    val work_date: Int,
    val work_date_str: String,
    val work_form_id: Int,
    val work_type: JSONWorkType,
    val work_type_id: Int
)