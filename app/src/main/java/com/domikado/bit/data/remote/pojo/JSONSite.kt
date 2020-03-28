package com.domikado.bit.data.remote.pojo

data class JSONSite(
    val id_site: Int,
    val id_site_monitor: Int,
    val image1: String?,
    val image2: String?,
    val item1: String?,
    val item2: String?,
    val latitude: String?,
    val longitude: String?,
    val remark1: String?,
    val remark2: String?,
    val site_code: String?,
    val site_name: String?,
    val site_alamat: String?,
    val status: Int,
    val checkin_at: String?,
    val finnish_at: String?,
    val detail_name: String?,
    val id_schedule: Int,
    val work_date: String?
)