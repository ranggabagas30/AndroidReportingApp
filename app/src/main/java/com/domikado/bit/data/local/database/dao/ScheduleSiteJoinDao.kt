package com.domikado.bit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.local.database.entity.SITE
import com.domikado.bit.data.local.database.entity.SITE_MONITORING_JOIN
import com.domikado.bit.data.local.database.entity.TbJoinSiteMonitoring
import com.domikado.bit.data.local.database.entity.TbSite
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ScheduleSiteJoinDao {

    @Insert
    fun insert(vararg scheduleSiteJoin: TbJoinSiteMonitoring): Completable

    @Query("SELECT ${SITE.TB_NAME}.* FROM ${SITE.TB_NAME} " +
                 "LEFT JOIN ${SITE_MONITORING_JOIN.TB_NAME} " +
                 "ON ${SITE.TB_NAME}.${SITE.ID} = ${SITE_MONITORING_JOIN.TB_NAME}.${SITE_MONITORING_JOIN.SITE_ID} " +
                 "WHERE ${SITE_MONITORING_JOIN.TB_NAME}.${SITE_MONITORING_JOIN.SCHEDULE_ID} = :scheduleId")
    fun getScheduleSites(scheduleId: Int): Single<List<TbSite>>
}