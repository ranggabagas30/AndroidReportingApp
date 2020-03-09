package com.domikado.bit.domain.domainmodel

data class WorkFormRowColumn(
    val id: Int,
    val workFormRow: WorkFormRow?,
    val workFormColumn: WorkFormColumn?,
    val workFormItems: List<WorkFormItem>?
)