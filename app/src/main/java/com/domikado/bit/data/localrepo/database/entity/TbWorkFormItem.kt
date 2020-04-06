package com.domikado.bit.data.localrepo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = WORK_FORM_ITEM.TB_NAME
)
data class TbWorkFormItem(

    @PrimaryKey
    @ColumnInfo(name = WORK_FORM_ITEM.ID)
    val id: Int,

    val position: Int?,

    @ColumnInfo(name = WORK_FORM_ITEM.IS_SEARCHABLE)
    val isSearchable: Boolean?,

    @ColumnInfo(name = WORK_FORM_ITEM.IS_MANDATORY)
    val isMandatory: Boolean?, // ketika item isMandatory = true, maka item ini harus diisi sebelum diupload ke server

    @ColumnInfo(name = WORK_FORM_ITEM.IS_VISIBLE)
    val isVisible: Boolean?, // jika isVisible = true, maka item muncul (terlihat) di dalam form.

    @ColumnInfo(name = WORK_FORM_ITEM.IS_LISTABLE)
    val isListable: Boolean?,

    @ColumnInfo(name = WORK_FORM_ITEM.FIELD_TYPE)
    val fieldType: String?, // i.e. "Photo", "Label", "TextField", "Radio"...

    @ColumnInfo(name = WORK_FORM_ITEM.DEFAULT_VALUE)
    val defaultValue: String?, // default value dari server

    @ColumnInfo(name = WORK_FORM_ITEM.SCOPE_TYPE)
    val scopeType: String? = "all", // jika "all", maka item ini berisi value untuk semua operator di schedule

    val label: String?,

    @ColumnInfo(name = WORK_FORM_ITEM.LABEL_KEY)
    val labelKey: String?,

    @ColumnInfo(name = WORK_FORM_ITEM.IS_DISABLED)
    val isDisabled: Boolean?, // jika isDisabled = true, maka item tidak dapat diinput untuk item yang bertipe inputan seperti "TextField", "Radio", "Checkbox", ...

    @ColumnInfo(name = WORK_FORM_ITEM.IS_EXPANDED)
    val isExpanded: Boolean?,

    val picture: String?,

    val description: String?
)

object WORK_FORM_ITEM {
    const val TB_NAME = "work_form_item"
    const val ID = "id"
    const val WORK_FORM_ROW_ID = "work_form_row_id"
    const val WORK_FORM_ROW_COLUMN_ID = "work_form_row_column_id"
    const val WORK_FORM_GROUP_ID = "work_form_group_id"
    const val IS_SEARCHABLE = "is_searchable"
    const val IS_MANDATORY = "is_mandatory"
    const val IS_VISIBLE = "is_visible"
    const val IS_LISTABLE = "is_listable"
    const val IS_DISABLED = "is_disabled"
    const val IS_EXPANDED = "is_expanded"
    const val FIELD_TYPE = "field_type"
    const val DEFAULT_VALUE = "default_value"
    const val SCOPE_TYPE = "scope_type"
    const val LABEL_KEY = "label_key"

    const val INDEX_WORK_FORM_ROW_COLUMN = "ROW_COLUMN_INDEX"
}