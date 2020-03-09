package com.domikado.bit.data.remote.pojo

data class JSONSite(
    val created_at: String,
    val id: Int,
    val location: JSONLocation,
    val locationStr: String,
    val location_id: Int,
    val name: String,
    val site_id_customer: String,
    val updated_at: String
)