package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.domikado.bit.data.local.database.entity.unused.TbWorkFormRowColumn

@Entity(
    tableName = WORK_FORM_ROW_COLUMN_JOIN.TB_NAME,
    primaryKeys = [WORK_FORM_ROW_COLUMN_JOIN.ID, WORK_FORM_ROW_COLUMN_JOIN.ROW_ID, WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID],
    foreignKeys = [
        ForeignKey(
            entity = TbWorkFormRow::class,
            parentColumns = [WORK_FORM_ROW.ID],
            childColumns = [WORK_FORM_ROW_COLUMN_JOIN.ROW_ID]
        ),
        ForeignKey(
            entity = TbWorkFormRowColumn::class,
            parentColumns = [WORK_FORM_COLUMN.ID],
            childColumns = [WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID]
        )
    ]
)
data class TbJoinWorkFormRowColumn(

    @ColumnInfo(name = WORK_FORM_ROW_COLUMN_JOIN.ID)
    val id: Int,

    @ColumnInfo(name = WORK_FORM_ROW_COLUMN_JOIN.ROW_ID)
    val rowId: Int,

    @ColumnInfo(name = WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID)
    val columnId: Int
)

object WORK_FORM_ROW_COLUMN_JOIN {
    const val TB_NAME = "row_column_join"
    const val ID = "row_column_id"
    const val ROW_ID = "row_id"
    const val COLUMN_ID = "column_id"
}