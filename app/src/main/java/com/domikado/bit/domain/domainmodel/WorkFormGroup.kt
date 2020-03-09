package com.domikado.bit.domain.domainmodel

data class WorkFormGroup(
    val id: Int,
    val name: String?,
    val position: Int?,
    val description: String?,
    val ancestry: String?,
    val workFormRowColumns: List<WorkFormRowColumn>?
)