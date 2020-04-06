package com.domikado.bit.data.localrepo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.domikado.bit.data.localrepo.database.entity.TbJoinWorkFormRowColumn
import com.domikado.bit.data.localrepo.database.entity.TbWorkFormColumn
import com.domikado.bit.data.localrepo.database.entity.WORK_FORM_COLUMN
import com.domikado.bit.data.localrepo.database.entity.WORK_FORM_ROW_COLUMN_JOIN
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RowColumnJoinDao {

    @Insert
    fun insert(vararg rowColumnJoinDao: TbJoinWorkFormRowColumn): Completable

    @Query("SELECT ${WORK_FORM_COLUMN.TB_NAME}.* FROM ${WORK_FORM_COLUMN.TB_NAME} INNER JOIN ${WORK_FORM_ROW_COLUMN_JOIN.TB_NAME} " +
            "ON ${WORK_FORM_COLUMN.ID} = ${WORK_FORM_ROW_COLUMN_JOIN.COLUMN_ID} WHERE ${WORK_FORM_ROW_COLUMN_JOIN.ROW_ID} = :rowId")
    fun getRowColumns(rowId: Int): Single<List<TbWorkFormColumn>>
}