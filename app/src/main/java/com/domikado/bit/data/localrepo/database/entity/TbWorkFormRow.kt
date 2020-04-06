package com.domikado.bit.data.localrepo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = WORK_FORM_ROW.TB_NAME
)
data class TbWorkFormRow(

    @PrimaryKey
    @ColumnInfo(name = WORK_FORM_ROW.ID)
    val id: Int,

    val position: Int,

    val ancestry: String?,

    val level: Int?
)

object WORK_FORM_ROW {
    const val TB_NAME = "work_form_row"
    const val ID = "id"
    const val PARENT_ROW_ID = "parent_row_id"
    const val WORK_FORM_GROUP_ID = "work_form_group_id"
    const val WORK_FORM_ITEM_ID = "work_form_item_id"
}