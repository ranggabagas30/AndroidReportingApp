package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.local.database.entity.TbFormFillData

data class FormFillData(
    val id: Int,
    val siteMonitorId: Int,
    val formFillId: Int,
    var itemValue: String?,
    var remark: String?,
    var image: String?,
    val latitude: Double?,
    val longitude: Double?,
    val lastUploadAt: String?
)

internal val TbFormFillData.toFormFillData
    get() = FormFillData(
        this.id,
        this.siteMonitorId,
        this.formFillId,
        this.itemValue,
        this.remark,
        this.image,
        this.latitude,
        this.longitude,
        this.lastUploadAt
    )

internal val FormFillData.toTbFormFillData
    get() = TbFormFillData(
        siteMonitorId = this.siteMonitorId,
        formFillId = this.formFillId,
        itemValue = this.itemValue,
        remark = this.remark,
        image = this.image,
        latitude = this.latitude,
        longitude = this.longitude,
        lastUploadAt = this.lastUploadAt
    )