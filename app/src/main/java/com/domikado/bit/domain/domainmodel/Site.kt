package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONSite

data class Site(
    val id: Int,
    val name: String?,
    val alamat: String?,
    val latitude: Double?,
    val longitude: Double?,
    val siteCode: String?,
    val siteIdMonitor: String?,
    var status: Int,
    val checkInAt: String?,
    val finishAt: String?
)

internal val JSONSite.toSite
    get() = Site(
        this.id_site,
        this.site_name,
        this.site_alamat,
        this.latitude?.toDouble(),
        this.longitude?.toDouble(),
        this.site_code,
        this.id_site_monitor.toString(),
        this.status,
        this.checkin_at,
        this.finnish_at
    )

