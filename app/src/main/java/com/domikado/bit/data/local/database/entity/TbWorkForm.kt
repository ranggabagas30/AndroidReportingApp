package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = WORK_FORM.TB_NAME,
    foreignKeys = [
        ForeignKey(
            entity = TbWorkType::class,
            parentColumns = [WORK_TYPE.ID],
            childColumns = [WORK_FORM.WORK_TYPE_ID]
        )
    ]
)
data class TbWorkForm(

    @PrimaryKey
    @ColumnInfo(name = WORK_FORM.ID)
    val id: Int,

    @ColumnInfo(name = WORK_FORM.NAME)
    val name: String?,

    @ColumnInfo(name = WORK_FORM.WORK_TYPE_ID)
    val workTypeId: Int
)

object WORK_FORM {
    const val TB_NAME = "work_form"
    const val ID = "id"
    const val NAME = "name"
    const val WORK_TYPE_ID = "work_type_id"
}