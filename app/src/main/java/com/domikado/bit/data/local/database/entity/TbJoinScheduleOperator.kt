package com.domikado.bit.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Schedule - Operator (M:M)
 * */
@Entity(
    tableName = SCHEDULE_OPERATOR_JOIN.TB_NAME,
    primaryKeys = [SCHEDULE_OPERATOR_JOIN.SCHEDULE_ID, SCHEDULE_OPERATOR_JOIN.OPERATOR_ID],
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
    val schedule_id: Int,
    val operator_id: Int
)

object SCHEDULE_OPERATOR_JOIN {
    const val TB_NAME = "schedule_operator_join"
    const val SCHEDULE_ID = "schedule_id"
    const val OPERATOR_ID = "operator_id"
}