package com.domikado.bit.data.localrepo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.localrepo.database.entity.TbWorkFormItem
import com.domikado.bit.data.localrepo.database.entity.WORK_FORM_ITEM
import io.reactivex.Completable

@Dao
interface ItemDao {

    @Insert
    fun insert(formItem: TbWorkFormItem): Completable

    @Query("DELETE FROM ${WORK_FORM_ITEM.TB_NAME}")
    fun deleteAll()
}