package com.domikado.bit.data.remoterepo.pojo

data class JSONSite(
    val id_site_monitor: Int,
    val user_latitude: String?,
    val user_longitude: String?,
    val checkin_latitude: String?,
    val checkin_longitude: String?,

    val image1: String?,
    val item1: String?,
    val remark1: String?,
    val image2: String?,
    val item2: String?,
    val remark2: String?,
    val image3: String?,
    val item3: String?,
    val remark3: String?,
    val image4: String?,
    val item4: String?,
    val remark4: String?,
    val image5: String?,
    val item5: String?,
    val remark5: String?,

    val status: Int,
    val site_id: Int,
    val status_text: String?,
    val site_name: String?,
    val site_code: String?,
    val site_alamat: String?,
    val site_kota: String?,
    val siteprovinsi: String?,
    val site_latitude: String?,
    val site_longitude: String?,
    val checkin_at: String?,
    val finnish_at: String?,

    val android_id: String?
)