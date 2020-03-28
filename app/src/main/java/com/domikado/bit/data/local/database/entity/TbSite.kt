package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = SITE.TB_NAME
)
data class TbSite(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SITE.ID)
    val id: Int = 0,

    @ColumnInfo(name = SITE.REMOTE_SITE_ID)
    val remoteSiteId: Int,

    @ColumnInfo(name = SITE.SITE_MONITOR_ID)
    val siteMonitorId: Int,

    @ColumnInfo(name = SITE.NAME)
    val name: String?,

    @ColumnInfo(name = SITE.CODE)
    val code: String?,

    @ColumnInfo(name = SITE.LATITUDE)
    val latitude: Double?,  // using format pair of LONG, LAT.  i.e, "-6.21312, 108.000"

    @ColumnInfo(name = SITE.LONGITUDE)
    val longitude: Double?,

    @ColumnInfo(name = SITE.STATUS)
    val status: Int
)

object SITE {
    const val TB_NAME = "Site"
    const val ID = "id"
    const val REMOTE_SITE_ID = "remote_site_id"
    const val NAME = "name"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val CODE = "code"
    const val SITE_MONITOR_ID = "site_monitor_id"
    const val STATUS = "status"
}

