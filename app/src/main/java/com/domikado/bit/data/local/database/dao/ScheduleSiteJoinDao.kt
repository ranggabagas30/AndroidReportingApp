package com.domikado.bit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.local.database.entity.SCHEDULE_SITE_JOIN
import com.domikado.bit.data.local.database.entity.SITE
import com.domikado.bit.data.local.database.entity.TbJoinScheduleSite
import com.domikado.bit.data.local.database.entity.TbSite
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ScheduleSiteJoinDao {

    @Insert
    fun insert(vararg scheduleSiteJoin: TbJoinScheduleSite): Completable

    @Query("SELECT ${SITE.TB_NAME}.* FROM ${SITE.TB_NAME} INNER JOIN ${SCHEDULE_SITE_JOIN.TB_NAME} " +
            "ON ${SITE.ID} = ${SCHEDULE_SITE_JOIN.SITE_ID} WHERE ${SCHEDULE_SITE_JOIN.SCHEDULE_ID} = :scheduleId")
    fun getScheduleSites(scheduleId: Int): Single<List<TbSite>>
}