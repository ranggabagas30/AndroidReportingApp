package com.domikado.bit.domain.domainmodel

data class CheckIn(
    val scheduleId: Int,
    val workDate: String?,
    val site: Site
)