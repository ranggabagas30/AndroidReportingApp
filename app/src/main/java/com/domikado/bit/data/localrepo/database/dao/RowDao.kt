package com.domikado.bit.data.localrepo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.domikado.bit.data.localrepo.database.entity.TbWorkFormRow
import io.reactivex.Completable

@Dao
interface RowDao {

    @Insert
    fun insert(vararg rows: TbWorkFormRow): Completable
}