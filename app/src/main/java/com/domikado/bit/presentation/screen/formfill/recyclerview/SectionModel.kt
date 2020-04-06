package com.domikado.bit.presentation.screen.formfill.recyclerview

sealed class SectionModel {
    data class RadioModel(
        val id: Int,
        val column: String? = null,
        val operator: String? = null,
        val title: String? = "NO TITLE",
        val isMandatory: Boolean = true,
        val radioValue: RadioGroupModel
    ): SectionModel()
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
    ): SectionModel()
    data class CheckBoxModel(
        val id: Int,
        val column: String? = null,
        val operator: String? = null,
        val title: String? = "NO TITLE",
        val isMandatory: Boolean = true,
        var items: MutableMap<String, Boolean>
    ): SectionModel()
    data class PhotoLayoutModel(
        val id: Int,
        var column: String? = null,
        var operator: String? = null,
        var title: String? = "NO TITLE",
        var isMandatory: Boolean = true,
        var remark: String? = null,
        var photoPath: String? = null,
        var latitude: Double? = null,
        var longitude: Double? = null
    ): SectionModel()
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

internal val SectionModel.remark
    get() = when(this) {
        is SectionModel.PhotoLayoutModel -> this.remark
        is SectionModel.EditTextModel -> null
        is SectionModel.CheckBoxModel -> this.items.toString()
        is SectionModel.RadioModel -> this.radioValue.items.toString()
    }

internal val SectionModel.image
    get() = when(this) {
        is SectionModel.PhotoLayoutModel -> this.photoPath
        is SectionModel.EditTextModel -> null
        is SectionModel.CheckBoxModel -> null
        is SectionModel.RadioModel -> null
    }

internal val SectionModel.latitude
    get() = when(this) {
        is SectionModel.PhotoLayoutModel -> this.latitude
        else -> null
    }

internal val SectionModel.longitude
    get() = when(this) {
        is SectionModel.PhotoLayoutModel -> this.longitude
        else -> null
    }

internal val SectionModel.isMandatory
    get() = when(this) {
        is SectionModel.PhotoLayoutModel -> this.isMandatory
        is SectionModel.EditTextModel -> this.isMandatory
        is SectionModel.CheckBoxModel -> this.isMandatory
        is SectionModel.RadioModel -> this.isMandatory
    }

internal val SectionModel.type
    get() = when(this) {
        is SectionModel.PhotoLayoutModel -> PhotoLayoutType
        is SectionModel.EditTextModel -> EditTextType
        is SectionModel.CheckBoxModel -> CheckBoxType
        is SectionModel.RadioModel -> RadioType
    }

internal val PhotoLayoutType = "Photograph"
internal val EditTextType = "EditText"
internal val CheckBoxType = "CheckBox"
internal val RadioType = "Radio"