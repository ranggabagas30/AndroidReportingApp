package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = WORK_FORM_ROW_COLUMN_JOIN.TB_NAME,
    indices = [
        Index(value = [WORK_FORM_ROW_COLUMN_JOIN.ROW_ID], name = WORK_FORM_ROW_COLUMN_JOIN.INDEX_ROW),
        Index(value = [WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID], name = WORK_FORM_ROW_COLUMN_JOIN.INDEX_COLUMN),
        Index(value = [WORK_FORM_ROW_COLUMN_JOIN.ITEM_ID], name = WORK_FORM_ROW_COLUMN_JOIN.INDEX_ITEM)
    ],
    primaryKeys = [WORK_FORM_ROW_COLUMN_JOIN.ID, WORK_FORM_ROW_COLUMN_JOIN.ROW_ID, WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID],
    foreignKeys = [
        ForeignKey(
            entity = TbWorkFormRow::class,
            parentColumns = [WORK_FORM_ROW.ID],
            childColumns = [WORK_FORM_ROW_COLUMN_JOIN.ROW_ID]
        ),
        ForeignKey(
            entity = TbWorkFormColumn::class,
            parentColumns = [WORK_FORM_COLUMN.ID],
            childColumns = [WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID]
        ),
        ForeignKey(
            entity = TbWorkFormItem::class,
            parentColumns = [WORK_FORM_ITEM.ID],
            childColumns = [WORK_FORM_ROW_COLUMN_JOIN.ITEM_ID]
        )
    ]
)
data class TbJoinWorkFormRowColumn(

    @ColumnInfo(name = WORK_FORM_ROW_COLUMN_JOIN.ID)
    val id: Int,

    @ColumnInfo(name = WORK_FORM_ROW_COLUMN_JOIN.ROW_ID)
    val rowId: Int,

    @ColumnInfo(name = WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID)
    val columnId: Int,

    @ColumnInfo(name = WORK_FORM_ROW_COLUMN_JOIN.ITEM_ID)
    val itemId: Int
)

object WORK_FORM_ROW_COLUMN_JOIN {
    const val TB_NAME = "row_column_join"
    const val ID = "row_column_id"
    const val ROW_ID = "row_id"
    const val COLUMN_ID = "column_id"
    const val ITEM_ID = "item_id"

    const val INDEX_ROW = "${TB_NAME}-ROW_INDEX"
    const val INDEX_COLUMN = "${TB_NAME}-COLUMN_INDEX"
    const val INDEX_ITEM = "${TB_NAME}-ITEM_INDEX"
}