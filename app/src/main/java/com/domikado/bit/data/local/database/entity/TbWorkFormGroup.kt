package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = WORK_FORM_GROUP.WORK_FORM_ID,
    foreignKeys = [
        ForeignKey(
            entity = TbWorkForm::class,
            parentColumns = [WORK_FORM.ID],
            childColumns = [WORK_FORM_GROUP.WORK_FORM_ID]
        )
    ]
)
data class TbWorkFormGroup(

    @PrimaryKey
    @ColumnInfo(name = WORK_FORM_GROUP.ID)
    val id: Int,

    val name: String?,

    val position: Int?,

    @ColumnInfo(name = WORK_FORM_GROUP.WORK_FORM_ID)
    val workFormId: Int,

    val description: String?,

    val ancestry: String?
)

object WORK_FORM_GROUP {
    const val TB_NAME = "work_form_group"
    const val ID = "id"
    const val NAME = "name"
    const val POSITION = "position"
    const val WORK_FORM_ID = "work_form_id"
    const val DESCRIPTION = "description"
    const val ANCESTRY = "ancestry"
}