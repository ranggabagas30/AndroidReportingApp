package com.domikado.bit.data.remote.pojo

data class JSONScheduleValue(
    val createdAt: String,
    val disable: Boolean,
    val gpsAccuracy: Int,
    val itemId: Int,
    val latitude: String,
    val longitude: String,
    val operatorId: Int,
    val photoStatus: String,
    val picture: String,
    val picture_updated_at: String,
    val remark: String,
    val row_id: Int,
    val scheduleId: Int,
    val siteId: Int,
    val typePhoto: Boolean,
    val uploadStatus: Int,
    val value: String
)