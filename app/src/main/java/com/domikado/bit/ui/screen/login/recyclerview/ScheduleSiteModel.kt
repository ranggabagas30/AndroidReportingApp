package com.domikado.bit.ui.screen.login.recyclerview

data class ScheduleSiteModel(
    val siteId: Int,
    val siteName: String?,
    val siteStatus: String?,
    val siteLatitude: Double?,
    val siteLongitude: Double?,
    val siteIdCustomer: String?,
    val isCheckInAllowed: Boolean = true
)

object SiteStatus {
    const val PROGRESS = "Progress"
    const val CHECK_IN = "Check In"
    const val FINISH   = "Finish"
}
