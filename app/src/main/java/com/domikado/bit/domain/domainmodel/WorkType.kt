package com.domikado.bit.domain.domainmodel

data class WorkType(
    val id: Int,
    val name: String?,
    val workForms: List<WorkForm>?
)