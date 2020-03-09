package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = WORK_TYPE.TB_NAME
)
data class TbWorkType(

    @PrimaryKey
    @ColumnInfo(name = WORK_TYPE.ID)
    val id: Int,

    @ColumnInfo(name = WORK_TYPE.NAME)
    val name: String?
)

object WORK_TYPE {
    const val TB_NAME = "work_type"
    const val ID = "id"
    const val NAME = "name"
}