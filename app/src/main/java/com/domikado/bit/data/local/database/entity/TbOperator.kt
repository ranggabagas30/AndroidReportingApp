package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = OPERATOR.TB_NAME
)
data class TbOperator(

    @PrimaryKey
    @ColumnInfo(name = OPERATOR.ID)
    val operatorId: Int,

    @ColumnInfo(name = OPERATOR.NAME)
    val name: String? // i.e. "XL", "TSEL", "ISAT" etc
) {
}

object OPERATOR {
    const val TB_NAME = "operator"
    const val ID = "id"
    const val NAME = "name"
}