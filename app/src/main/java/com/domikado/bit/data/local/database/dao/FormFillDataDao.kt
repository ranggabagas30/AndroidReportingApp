package com.domikado.bit.data.local.database.dao

import androidx.room.*
import com.domikado.bit.data.local.database.entity.FORM_FILL_DATA
import com.domikado.bit.data.local.database.entity.TbFormFillData
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FormFillDataDao {

    @Delete
    fun delete(formFillData: List<TbFormFillData>): Completable

    // insert or replace form fill data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(formFillData: List<TbFormFillData>): Completable

    // get all form fill data by siteMonitorId
    @Query("SELECT * FROM ${FORM_FILL_DATA.TB_NAME} WHERE ${FORM_FILL_DATA.SITE_MONITOR_ID} = :siteMonitorId")
    fun getFormFillData(siteMonitorId: Int): Single<List<TbFormFillData>>


}