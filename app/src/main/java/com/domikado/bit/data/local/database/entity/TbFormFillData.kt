package com.domikado.bit.data.local.database.entity

import androidx.room.*

/**
 *
 * */
@Entity(
    tableName = FORM_FILL_DATA.TB_NAME,
    indices = [
        Index(value = [FORM_FILL_DATA.SITE_MONITOR_ID], name = FORM_FILL_DATA.INDEX_SITE_MONITOR_ID)
    ],
    foreignKeys = [
        ForeignKey(
            entity = TbJoinSiteMonitoring::class,
            parentColumns = [SITE_MONITORING_JOIN.ID],
            childColumns = [FORM_FILL_DATA.SITE_MONITOR_ID]
        )
    ]
)
data class TbFormFillData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FORM_FILL_DATA.ID)
    val id: Int = 0,

    @ColumnInfo(name = FORM_FILL_DATA.SITE_MONITOR_ID)
    val siteMonitorId: Int,

    @ColumnInfo(name = FORM_FILL_DATA.FORM_FILL_ID)
    val formFillId: Int,

    @ColumnInfo(name = FORM_FILL_DATA.ITEM_VALUE)
    val itemValue: String? = null,

    @ColumnInfo(name = FORM_FILL_DATA.REMARK)
    val remark: String? = null,

    @ColumnInfo(name = FORM_FILL_DATA.IMAGE)
    val image: String? = null,

    @ColumnInfo(name = FORM_FILL_DATA.LATITUDE)
    val latitude: Double? = null,

    @ColumnInfo(name = FORM_FILL_DATA.LONGITUDE)
    val longitude: Double? = null,

    @ColumnInfo(name = FORM_FILL_DATA.LAST_UPLOAD_AT)
    val lastUploadAt: String? = null
)

object FORM_FILL_DATA {
    const val TB_NAME = "form_fill_data"
    const val ID = "id"
    const val SITE_MONITOR_ID = "site_monitor_id"
    const val FORM_FILL_ID = "form_fill_id"
    const val ITEM_VALUE = "item_value"
    const val REMARK = "remark"
    const val IMAGE = "image"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val LAST_UPLOAD_AT = "last_upload_at"

    const val INDEX_SITE_MONITOR_ID = "$TB_NAME-SITE_MONITOR_INDEX"
}