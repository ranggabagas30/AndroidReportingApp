package com.domikado.bit.ui.screen.formfill.recyclerview

import android.location.Location

sealed class SectionModel {
    data class RadioModel(
        val id: Int,
        val column: String? = null,
        val operator: String? = null,
        val title: String? = "NO TITLE",
        val isMandatory: Boolean = true,
        val radioValue: RadioGroupModel
    )
    data class EditTextModel(
        val id: Int,
        val column: String? = null,
        val operator: String? = null,
        val title: String? = "NO TITLE",
        val isMandatory: Boolean = true,
        val hint: String? = null,
        val description: String? = null,
        val defaultValue: String? = null,
        val inputType: Int = 0 // 0 = "text", 1 = "numeric"
    )
    data class CheckBoxModel(
        val id: Int,
        val column: String? = null,
        val operator: String? = null,
        val title: String? = "NO TITLE",
        val isMandatory: Boolean = true,
        var items: MutableMap<String, Boolean>
    )
    data class PhotoLayoutModel(
        val id: Int,
        val column: String? = null,
        val operator: String? = null,
        val title: String? = "NO TITLE",
        val isMandatory: Boolean = true,
        val remark: String? = null,
        val photoPath: String? = null,
        val location: Location? = null
    )
}

data class RadioGroupModel(
    val onChecked: String?,
    val items: Map<Int, RadioButtonModel>
)

data class RadioButtonModel(
    val id: Int?,
    val isChecked: Boolean,
    val label: String?
)