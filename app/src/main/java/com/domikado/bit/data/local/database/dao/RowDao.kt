package com.domikado.bit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.local.database.entity.TbWorkFormRow
import com.domikado.bit.data.local.database.entity.WORK_FORM_ROW
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RowDao {

    @Insert
    fun insert(vararg rows: TbWorkFormRow): Completable

    @Query("SELECT * FROM ${WORK_FORM_ROW.TB_NAME} WHERE ${WORK_FORM_ROW.WORK_FORM_ITEM_ID} = :itemId ")
    fun getItemRows(itemId: Int): Single<List<TbWorkFormRow>>
}