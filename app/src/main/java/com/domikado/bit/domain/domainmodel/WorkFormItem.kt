package com.domikado.bit.domain.domainmodel

data class WorkFormItem(
    val id: Int,
    val position: Int,
    val isSearchable: Boolean,
    val isMandatory: Boolean,
    val isVisible: Boolean,
    val isListable: Boolean,
    val fieldType: String,
    val defaultValue: String?,
    val scopeType: String,
    val label: String?,
    val labelKey: String?,
    val isDisabled: Boolean,
    val isExpanded: Boolean,
    val picture: String?,
    val description: String?,
    val options: List<WorkFormOption>?
) {
}