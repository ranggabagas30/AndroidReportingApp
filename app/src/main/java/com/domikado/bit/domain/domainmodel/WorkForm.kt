package com.domikado.bit.domain.domainmodel

data class WorkForm(
    val id: Int,
    val name: String?,
    val workFormGroups: List<WorkFormGroup>?
)