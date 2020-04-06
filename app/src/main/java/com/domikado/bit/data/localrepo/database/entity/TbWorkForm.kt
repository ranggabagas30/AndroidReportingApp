package com.domikado.bit.data.localrepo.database.entity

import androidx.room.*

@Entity(
    tableName = WORK_FORM.TB_NAME,
    indices = [
        Index(value = [WORK_FORM.WORK_TYPE_ID], name = WORK_FORM.INDEX_WORK_TYPE)
    ],
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

    const val INDEX_WORK_TYPE = "${TB_NAME}-INDEX_WORK_TYPE"
}