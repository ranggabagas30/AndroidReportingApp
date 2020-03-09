package com.domikado.bit.domain.domainmodel

data class WorkFormRow(
    val id: Int,
    val position: Int,
    val ancestry: String?,
    val level: Int
)