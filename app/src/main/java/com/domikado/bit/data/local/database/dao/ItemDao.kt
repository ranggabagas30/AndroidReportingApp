package com.domikado.bit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.local.database.entity.TbWorkFormItem
import com.domikado.bit.data.local.database.entity.WORK_FORM_ITEM
import io.reactivex.Completable

@Dao
interface ItemDao {

    @Insert
    fun insert(formItem: TbWorkFormItem): Completable

    @Query("DELETE FROM ${WORK_FORM_ITEM.TB_NAME}")
    fun deleteAll()
}