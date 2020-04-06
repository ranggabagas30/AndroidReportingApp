package com.domikado.bit.data.localrepo.database.dao

import androidx.room.*
import com.domikado.bit.data.localrepo.database.entity.FORM_FILL_DATA
import com.domikado.bit.data.localrepo.database.entity.TbFormFillData
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FormFillDataDao {

    @Delete
    fun delete(formFillData: List<TbFormFillData>): Completable

    @Query("DELETE FROM ${FORM_FILL_DATA.TB_NAME} WHERE ${FORM_FILL_DATA.FORM_FILL_MODEL_ID} = :formFillModelId AND ${FORM_FILL_DATA.SITE_MONITOR_ID} = :siteMonitorId")
    fun delete(formFillModelId: Int, siteMonitorId: Int): Completable

    // insert or replace form fill data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(formFillData: List<TbFormFillData>): Completable

    // get all form fill data by siteMonitorId
    @Query("SELECT * FROM ${FORM_FILL_DATA.TB_NAME} WHERE ${FORM_FILL_DATA.SITE_MONITOR_ID} = :siteMonitorId ORDER BY ${FORM_FILL_DATA.FORM_FILL_MODEL_ID} ASC")
    fun getFormFillData(siteMonitorId: Int): Single<List<TbFormFillData>>

//    @Query("SELECT * FROM ${FORM_FILL_DATA.TB_NAME} WHERE ${FORM_FILL_DATA.SITE_MONITOR_ID} = :siteMonitorId ORD")
//    fun getUploadItems(siteMonitorId: Int): Single<List<TbFormFillData>>
}