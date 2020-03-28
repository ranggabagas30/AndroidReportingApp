package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * associative JSONTable FormFillData (many-to_many)
 * defined FK: schedule_id, operator_id, item_id, row_id
 * */
@Entity(
    tableName = FORM_FILL_DATA.TB_NAME
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
}