package com.domikado.bit.data.localrepo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.domikado.bit.data.localrepo.database.entity.TbWorkType
import com.domikado.bit.data.localrepo.database.entity.WORK_TYPE
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WorkTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workTypes: List<TbWorkType>): Completable

    @Query("SELECT * FROM ${WORK_TYPE.TB_NAME} WHERE ${WORK_TYPE.ID} = :workTypeId")
    fun getWorkType(workTypeId: Int): Single<TbWorkType>
}