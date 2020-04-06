package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.localrepo.database.entity.TbFormFillData
import com.domikado.bit.presentation.screen.formfill.recyclerview.FormFillModel
import com.domikado.bit.presentation.screen.formfill.recyclerview.SectionModel
import com.domikado.bit.presentation.screen.formfill.recyclerview.image
import com.domikado.bit.presentation.screen.formfill.recyclerview.type

data class FormFillData(
    val id: Int,
    val formFillModelId: Int,
    val siteMonitorId: Int,
    val title: String? = null,
    var itemValue: String? = null,
    var remark: String? = null,
    var image: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isMandatory: Boolean = true,
    val type: String,
    val lastUploadAt: String?
)

internal val TbFormFillData.toFormFillData
    get() = FormFillData(
        this.id,
        this.formFillModelId,
        this.siteMonitorId,
        this.title,
        this.itemValue,
        this.remark,
        this.image,
        this.latitude,
        this.longitude,
        this.isMandatory,
        this.type,
        this.lastUploadAt
    )

internal val FormFillData.toTbFormFillData
    get() = TbFormFillData(
        id = 0, // ignored
        formFillModelId = this.formFillModelId,
        siteMonitorId = this.siteMonitorId,
        title = this.title,
        itemValue = this.itemValue,
        remark = this.remark,
        image = this.image,
        latitude = this.latitude,
        longitude = this.longitude,
        isMandatory = this.isMandatory,
        type = this.type,
        lastUploadAt = this.lastUploadAt
    )

internal val FormFillModel.toFormFillData
    get() = FormFillData(
        0, // ignored
        this.id,
        this.siteMonitorId,
        this.header.title,
        null,
        (this.body.sections[0] as SectionModel.PhotoLayoutModel).remark,
        (this.body.sections[0] as SectionModel.PhotoLayoutModel).image,
        (this.body.sections[0] as SectionModel.PhotoLayoutModel).latitude,
        (this.body.sections[0] as SectionModel.PhotoLayoutModel).longitude,
        (this.body.sections[0] as SectionModel.PhotoLayoutModel).isMandatory,
        (this.body.sections[0] as SectionModel.PhotoLayoutModel).type,
        this.lastUploadAt
    )