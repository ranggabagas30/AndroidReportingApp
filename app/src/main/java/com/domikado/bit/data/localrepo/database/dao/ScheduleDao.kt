package com.domikado.bit.data.localrepo.database.dao

import androidx.room.*
import com.domikado.bit.data.localrepo.database.entity.FORM_FILL_DATA
import com.domikado.bit.data.localrepo.database.entity.SCHEDULE
import com.domikado.bit.data.localrepo.database.entity.SITE_MONITORING_JOIN
import com.domikado.bit.data.localrepo.database.entity.TbSchedule
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(schedules: List<TbSchedule>): Completable

    @Delete
    abstract fun delete(schedules: List<TbSchedule>): Completable

    @Query("DELETE FROM ${SCHEDULE.TB_NAME}")
    abstract fun deleteAll(): Completable

    @Query("DELETE FROM ${SCHEDULE.TB_NAME} WHERE ${SCHEDULE.USER_ID} = :userId")
    abstract fun deleteByUserId(userId: String): Completable

    @Query("SELECT * FROM ${SCHEDULE.TB_NAME} WHERE ${SCHEDULE.ID} = :scheduleId")
    abstract fun getSchedule(scheduleId: Int): Single<TbSchedule>

    @Query("SELECT * FROM ${SCHEDULE.TB_NAME} WHERE ${SCHEDULE.USER_ID} = :userId")
    abstract fun getUserSchedules(userId: String): Single<List<TbSchedule>>

    @Query("SELECT COUNT(*) FROM ${FORM_FILL_DATA.TB_NAME} " +
                 "INNER JOIN ${SITE_MONITORING_JOIN.TB_NAME} " +
                 "ON ${FORM_FILL_DATA.TB_NAME}.${FORM_FILL_DATA.SITE_MONITOR_ID} = ${SITE_MONITORING_JOIN.TB_NAME}.${SITE_MONITORING_JOIN.ID} " +
                 "WHERE ${SITE_MONITORING_JOIN.TB_NAME}.${SITE_MONITORING_JOIN.SCHEDULE_ID} = :scheduleId")
    abstract fun getUserScheduleProgress(scheduleId: Int): Single<Int>
    
    @Transaction
    open fun deleteAndInsertSchedules(schedules: List<TbSchedule>) {
        deleteAll()
        insertOrReplace(schedules)
    }
}

