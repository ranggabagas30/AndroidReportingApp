package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = WORK_FORM_COLUMN.TB_NAME
//    foreignKeys = [
//        ForeignKey(
//            entity = TbWorkFormGroup::class,
//            parentColumns = [WORK_FORM_GROUP.ID],
//            childColumns = [WORK_FORM_COLUMN.WORK_FORM_GROUP_ID]
//        )
//    ]
)
data class TbWorkFormColumn(

    @PrimaryKey
    @ColumnInfo(name = WORK_FORM_COLUMN.ID)
    val id: Int,

    val name: String?,

//    @ColumnInfo(name = WORK_FORM_COLUMN.WORK_FORM_GROUP_ID)
//    val workFormGroupId: Int,

    val position: Int?


)

object WORK_FORM_COLUMN {
    const val TB_NAME = "work_form_column"
    const val ID = "id"
    const val NAME = "name"
    const val POSITION = "position"
    const val WORK_FORM_GROUP_ID = "work_form_group_id"
}