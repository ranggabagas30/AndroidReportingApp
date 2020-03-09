package com.domikado.bit.data.local.database.dao

import androidx.room.*
import com.domikado.bit.data.local.database.entity.SCHEDULE
import com.domikado.bit.data.local.database.entity.TbSchedule
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(schedules: List<TbSchedule>): Completable

    @Delete
    abstract fun delete(schedules: List<TbSchedule>): Completable

    @Query("DELETE FROM ${SCHEDULE.TB_NAME}")
    abstract fun deleteAll()

    @Query("SELECT * FROM ${SCHEDULE.TB_NAME} WHERE ${SCHEDULE.ID} = :scheduleId")
    abstract fun getSchedule(scheduleId: Int): Single<TbSchedule>

    @Query("SELECT * FROM ${SCHEDULE.TB_NAME} WHERE ${SCHEDULE.USER_ID} = :userId")
    abstract fun getUserSchedules(userId: Int): Single<List<TbSchedule>>

    @Query("SELECT * FROM ${SCHEDULE.TB_NAME} WHERE ${SCHEDULE.WORK_TYPE_ID} = :workTypeId")
    abstract fun getWorkTypeSchedules(workTypeId: Int): Single<List<TbSchedule>>

    @Query("SELECT * FROM ${SCHEDULE.TB_NAME} WHERE ${SCHEDULE.USER_ID} = :userId AND ${SCHEDULE.WORK_TYPE_ID} = :workTypeId")
    abstract fun getUserWorkTypeSchedules(userId: Int, workTypeId: Int): Single<List<TbSchedule>>

    @Transaction
    open fun deleteAndInsertSchedules(schedules: List<TbSchedule>) {
        deleteAll()
        insertOrReplace(schedules)
    }
}

