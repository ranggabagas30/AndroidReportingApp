package com.domikado.bit.data.local.database.entity

import androidx.room.*

/**
 * Schedule - Operator (M:M)
 * */
@Entity(
    tableName = SCHEDULE_OPERATOR_JOIN.TB_NAME,
    indices = [
        Index(value = [SCHEDULE_OPERATOR_JOIN.SCHEDULE_ID], name = SCHEDULE_OPERATOR_JOIN.INDEX_SCHEDULE_ID),
        Index(value = [SCHEDULE_OPERATOR_JOIN.OPERATOR_ID], name = SCHEDULE_OPERATOR_JOIN.INDEX_OPERATOR_ID)
    ],
    //primaryKeys = [SCHEDULE_OPERATOR_JOIN.ID, SCHEDULE_OPERATOR_JOIN.SCHEDULE_ID, SCHEDULE_OPERATOR_JOIN.OPERATOR_ID],
    foreignKeys = [
        ForeignKey(
            entity = TbSchedule::class,
            parentColumns = [SCHEDULE.ID],
            childColumns = [SCHEDULE_OPERATOR_JOIN.SCHEDULE_ID]
        ),
        ForeignKey(
            entity = TbOperator::class,
            parentColumns = [OPERATOR.ID],
            childColumns = [SCHEDULE_OPERATOR_JOIN.OPERATOR_ID]
        )
    ]
)
data class TbJoinScheduleOperator(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SCHEDULE_OPERATOR_JOIN.ID)
    val id: Int = 0,
    val schedule_id: Int,
    val operator_id: Int
)

object SCHEDULE_OPERATOR_JOIN {
    const val TB_NAME = "schedule_operator_join"
    const val ID = "schedule_operator_join_id"
    const val SCHEDULE_ID = "schedule_id"
    const val OPERATOR_ID = "operator_id"

    const val INDEX_SCHEDULE_ID = "$TB_NAME-SCHEDULE_INDEX"
    const val INDEX_OPERATOR_ID = "$TB_NAME-OPERATOR_INDEX"
}