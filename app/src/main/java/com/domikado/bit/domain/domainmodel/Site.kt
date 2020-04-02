package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.local.database.entity.TbSite
import com.domikado.bit.data.remote.pojo.JSONSite
import com.domikado.bit.ui.screen.schedulelist.recyclerview.SiteModel

data class Site(
    val id: Int,
    val name: String?,
    val alamat: String?,
    val code: String?,
    val latitude: Double?,
    val longitude: Double?,
    val siteMonitorId: Int,
    var status: Int,
    val status_text: String?,
    val checkInAt: String?,
    val finishAt: String?
)

internal val JSONSite.toSite
    get() = Site(
        this.site_id,
        this.site_name,
        this.site_alamat,
        this.site_code,
        this.site_latitude?.toDouble(),
        this.site_longitude?.toDouble(),
        this.id_site_monitor,
        this.status,
        this.status_text,
        this.checkin_at,
        this.finnish_at
    )

internal val Site.toTbSite
    get() = TbSite(
        this.id,
        this.siteMonitorId,
        this.name,
        this.alamat,
        this.code,
        this.latitude,
        this.longitude,
        this.status,
        this.checkInAt,
        this.finishAt
    )

internal val TbSite.toSite
    get() = Site(
        this.id,
        this.name,
        this.alamat,
        this.code,
        this.latitude,
        this.longitude,
        this.siteMonitorId,
        this.status,
        null,
        this.checkInAt,
        this.finishAt
    )

internal val Site.toSiteModel
    get() = SiteModel(
        this.id,
        this.name?: "Nama site N/A",
        this.status,
        this.status_text?: SITE_STATUS[this.status],
        this.latitude?: 0.0,
        this.longitude?: 0.0,
        this.code?: "Kode site N/A",
        this.siteMonitorId,
        isCheckInAllowed = true
    )

internal val SITE_STATUS = arrayOf("Progress", "Check In", "Tuntas")
