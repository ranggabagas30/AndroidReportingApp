package com.domikado.bit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.domikado.bit.data.local.database.entity.TbWorkFormRow
import io.reactivex.Completable

@Dao
interface RowDao {

    @Insert
    fun insert(vararg rows: TbWorkFormRow): Completable
}