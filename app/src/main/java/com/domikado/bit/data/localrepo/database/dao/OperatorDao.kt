package com.domikado.bit.data.localrepo.database.dao

import androidx.room.*
import com.domikado.bit.data.localrepo.database.entity.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface OperatorDao {

    @Delete
    fun delete(operators: List<TbOperator>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(operators: List<TbOperator>): Completable

    @Query("SELECT ${OPERATOR.TB_NAME}.* FROM ${OPERATOR.TB_NAME} " +
                "LEFT JOIN ${SCHEDULE_OPERATOR_JOIN.TB_NAME} ON ${OPERATOR.TB_NAME}.${OPERATOR.ID} = ${SCHEDULE_OPERATOR_JOIN.TB_NAME}.${SCHEDULE_OPERATOR_JOIN.OPERATOR_ID} " +
                "LEFT JOIN ${SCHEDULE.TB_NAME} ON ${SCHEDULE_OPERATOR_JOIN.TB_NAME}.${SCHEDULE_OPERATOR_JOIN.SCHEDULE_ID} = ${SCHEDULE.TB_NAME}.${SCHEDULE.ID} " +
                "LEFT JOIN ${SITE_MONITORING_JOIN.TB_NAME} ON ${SITE_MONITORING_JOIN.TB_NAME}.${SITE_MONITORING_JOIN.SCHEDULE_ID} = ${SCHEDULE.TB_NAME}.${SCHEDULE.ID} " +
                "WHERE ${SITE_MONITORING_JOIN.TB_NAME}.${SITE_MONITORING_JOIN.ID} = :siteMonitorId")
    fun getOperatorsBySiteMonitorId(siteMonitorId: Int): Single<List<TbOperator>>
}