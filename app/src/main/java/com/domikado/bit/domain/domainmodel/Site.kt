package com.domikado.bit.domain.domainmodel

import android.text.TextUtils
import com.domikado.bit.data.localrepo.database.entity.TbSite
import com.domikado.bit.data.remoterepo.pojo.JSONSite
import com.domikado.bit.external.utils.DateUtil
import com.domikado.bit.presentation.screen.schedulelist.recyclerview.SiteModel

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
    val proses: Int,
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
        this.proses,
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
        0,
        this.checkInAt,
        this.finishAt
    )

internal val Site.toSiteModel
    get() = SiteModel(
        this.id,
        this.name?: "Nama site N/A",
        this.status,
        this.status_text?: SITE_STATUS[this.status]?: "Status site N/A",
        this.proses,
        this.latitude?: 0.0,
        this.longitude?: 0.0,
        this.code?: "Kode site N/A",
        this.siteMonitorId,
        isCheckInAllowed = true
    )

internal fun isCheckInAllowed(workDate: String?): Boolean {
    return if (!TextUtils.isEmpty(workDate)) {
        val workDateISO = DateUtil.stringToDate(workDate!!)
        workDateISO.isBefore(DateUtil.getDateTimeNow()) || workDateISO.isEqual(DateUtil.getDateTimeNow())
    } else false
}

internal val SITE_STATUS = mapOf(0 to "Progress", 1 to "Check In", 2 to "Tuntas", 3 to "Reject")
internal const val PROSES = 0
internal const val CHECK_IN = 1
internal const val TUNTAS   = 2
internal const val REJECT   = 3
