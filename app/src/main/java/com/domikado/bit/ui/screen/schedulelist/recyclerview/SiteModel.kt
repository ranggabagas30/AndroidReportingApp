package com.domikado.bit.ui.screen.schedulelist.recyclerview

data class SiteModel(
    val id: Int,
    val name: String,
    val status: String,
    val latitude: Double,
    val longitude: Double,
    val code: String,
    val siteMonitorId: Int,
    val isCheckInAllowed: Boolean = true
)
