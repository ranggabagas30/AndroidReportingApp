package com.domikado.bit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.local.database.entity.OPERATOR
import com.domikado.bit.data.local.database.entity.SCHEDULE
import com.domikado.bit.data.local.database.entity.TbOperator
import com.domikado.bit.data.local.database.entity.SCHEDULE_OPERATOR_JOIN
import com.domikado.bit.data.local.database.entity.TbJoinScheduleOperator
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ScheduleOperatorJoinDao {

    @Insert
    fun insert(scheduleOperatorJoin: TbJoinScheduleOperator): Completable

    @Query("SELECT * FROM ${OPERATOR.TB_NAME} INNER JOIN ${SCHEDULE_OPERATOR_JOIN.TB_NAME} " +
            "ON ${OPERATOR.ID} = ${SCHEDULE_OPERATOR_JOIN.OPERATOR_ID} WHERE ${SCHEDULE.ID} = :scheduleId")
    fun getScheduleOperators(scheduleId: Int): Single<List<TbOperator>>
}