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
    val id: Int,

    @ColumnInfo(name = SITE.SITE_MONITOR_ID)
    val siteMonitorId: Int,

    @ColumnInfo(name = SITE.NAME)
    val name: String?,

    @ColumnInfo(name = SITE.ALAMAT)
    val alamat: String?,

    @ColumnInfo(name = SITE.CODE)
    val code: String?,

    @ColumnInfo(name = SITE.LATITUDE)
    val latitude: Double?,  // using format pair of LONG, LAT.  i.e, "-6.21312, 108.000"

    @ColumnInfo(name = SITE.LONGITUDE)
    val longitude: Double?,

    @ColumnInfo(name = SITE.STATUS)
    val status: Int,

    @ColumnInfo(name = SITE.CHECKIN_AT)
    val checkInAt: String?,

    @ColumnInfo(name = SITE.FINISH_AT)
    val finishAt: String?
)

object SITE {
    const val TB_NAME = "site"
    const val ID = "id"
    const val REMOTE_SITE_ID = "remote_site_id"
    const val ALAMAT = "alamat"
    const val NAME = "name"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val CODE = "code"
    const val SITE_MONITOR_ID = "site_monitor_id"
    const val STATUS = "status"
    const val CHECKIN_AT = "checkin_at"
    const val FINISH_AT = "finish_at"
}

