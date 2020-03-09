package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = SITE.TB_NAME
)
data class TbSite(

    @PrimaryKey
    @ColumnInfo(name = SITE.ID)
    val siteId: Int,

    @ColumnInfo(name = SITE.NAME)
    val name: String?,

    @ColumnInfo(name = SITE.LOCATION)
    val location: String?, // using format pair of LONG, LAT.  i.e, "-6.21312, 108.000"

    @ColumnInfo(name = SITE.CUSTOMER_ID)
    val customerId: String?
)

object SITE {
    const val TB_NAME = "site"
    const val ID = "id"
    const val NAME = "name"
    const val LOCATION = "location"
    const val CUSTOMER_ID = "customer_id"
}

