package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = OPERATOR.TB_NAME
)
data class TbOperator(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = OPERATOR.ID)
    val id: Int = 0,

    @ColumnInfo(name = OPERATOR.NAME)
    val name: String?, // i.e. "Indosat, Telkomsel, dsb"

    @ColumnInfo(name = OPERATOR.DETAIL_NAME)
    val detailName: String?, // i.e. "PT. XL Axiata, PT. Telkomsel, dsb

    @ColumnInfo(name = OPERATOR.CODE)
    val code: String? // i.e. "XL", "TSEL", "ISAT" etc
)

object OPERATOR {
    const val TB_NAME = "operator"
    const val ID = "id"
    const val NAME = "name"
    const val DETAIL_NAME = "detail_name"
    const val CODE = "code"
}