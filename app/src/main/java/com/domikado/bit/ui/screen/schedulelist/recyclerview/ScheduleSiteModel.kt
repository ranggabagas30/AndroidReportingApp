package com.domikado.bit.ui.screen.schedulelist.recyclerview

data class ScheduleSiteModel(
    val siteId: Int,
    val siteName: String?,
    val siteStatus: Int? = null,
    val siteLatitude: Double?,
    val siteLongitude: Double?,
    val siteCode: String?,
    val siteMonitorId: Int,
    val isCheckInAllowed: Boolean = true
)

internal val SITE_STATUS = arrayOf("Progress", "Check In", "Finish")
