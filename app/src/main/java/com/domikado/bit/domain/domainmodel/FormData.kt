package com.domikado.bit.domain.domainmodel

data class FormData(
    val id: Int,
    val scheduleId: Int,
    val operatorId: Int,
    val itemId: Int,
    val rowId: Int,
    val gpsAccuracy: Double,
    val latitude: Double,
    val longitude: Double,
    val value: String?,
    val remark: String?,
    val uploadStatus: Int?,
    val photoDate: String,
    val createdAt: String
) {
}