package com.domikado.bit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.local.database.entity.TbWorkFormColumn
import com.domikado.bit.data.local.database.entity.WORK_FORM_COLUMN
import com.domikado.bit.data.local.database.entity.WORK_FORM_ROW_COLUMN_JOIN
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RowColumnJoinDao {

    @Insert
    fun insert(vararg rowColumnJoinDao: RowColumnJoinDao): Completable

    @Query("SELECT * FROM ${WORK_FORM_COLUMN.TB_NAME} INNER JOIN ${WORK_FORM_ROW_COLUMN_JOIN.TB_NAME} " +
            "ON ${WORK_FORM_COLUMN.ID} = ${WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID} WHERE ${WORK_FORM_ROW_COLUMN_JOIN.ROW_ID} = :rowId")
    fun getRowColumns(rowId: Int): Single<List<TbWorkFormColumn>>
}