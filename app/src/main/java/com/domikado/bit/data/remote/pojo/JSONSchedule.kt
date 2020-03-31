package com.domikado.bit.data.remote.pojo

data class JSONSchedule(
    val created_at: String?,
    val id_schedule: Int,
    val name_teknisi: String?,
    val operator_code: String?,
    val operator_detail_name: String?,
    val operator_name: String?,
    val pic_approve_at: String?,
    val pic_reject_at: String?,
    val pic_status: Int,
    val pic_status_text: String?,
    val pm_approve_at: String?,
    val pm_reject_at: String?,
    val pm_status: Int,
    val pm_status_text: String?,
    val remark_schedule: String?,
    val site: List<JSONSite>?,
    val username: String?,
    val work_date: String?
)