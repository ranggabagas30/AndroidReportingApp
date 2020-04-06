package com.domikado.bit.data.localrepo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = WORK_FORM_COLUMN.TB_NAME
)
data class TbWorkFormColumn(

    @PrimaryKey
    @ColumnInfo(name = WORK_FORM_COLUMN.ID)
    val id: Int,

    val name: String?,

    val position: Int?
)

object WORK_FORM_COLUMN {
    const val TB_NAME = "work_form_column"
    const val ID = "id"
    const val NAME = "name"
    const val POSITION = "position"
    const val WORK_FORM_GROUP_ID = "work_form_group_id"
}