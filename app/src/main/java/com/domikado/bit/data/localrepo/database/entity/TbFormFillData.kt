package com.domikado.bit.data.localrepo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 *
 * */
@Entity(
    tableName = FORM_FILL_DATA.TB_NAME,
    indices = [
        Index(value = [FORM_FILL_DATA.FORM_FILL_MODEL_ID, FORM_FILL_DATA.SITE_MONITOR_ID], name = FORM_FILL_DATA.INDEX_1, unique = true)
    ]
//    foreignKeys = [
//        ForeignKey(
//            entity = TbJoinSiteMonitoring::class,
//            parentColumns = [SITE_MONITORING_JOIN.ID],
//            childColumns = [FORM_FILL_DATA.SITE_MONITOR_ID]
//        )
//    ]
)
data class TbFormFillData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FORM_FILL_DATA.ID)
    val id: Int = 0,

    @ColumnInfo(name = FORM_FILL_DATA.FORM_FILL_MODEL_ID)
    val formFillModelId: Int,

    @ColumnInfo(name = FORM_FILL_DATA.SITE_MONITOR_ID)
    val siteMonitorId: Int,

    @ColumnInfo(name = FORM_FILL_DATA.TITLE)
    val title: String? = null,

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

    @ColumnInfo(name = FORM_FILL_DATA.IS_MANDATORY)
    val isMandatory: Boolean,

    @ColumnInfo(name = FORM_FILL_DATA.TYPE)
    val type: String,

    @ColumnInfo(name = FORM_FILL_DATA.MODIFIED_AT)
    val modifiedAt: Long? = null,

    @ColumnInfo(name = FORM_FILL_DATA.LAST_UPLOAD_AT)
    val lastUploadAt: String? = null

)

object FORM_FILL_DATA {
    const val TB_NAME = "form_fill_data"
    const val ID = "id"
    const val SITE_MONITOR_ID = "site_monitor_id"
    const val TITLE = "title"
    const val FORM_FILL_MODEL_ID = "form_fill_model_id"
    const val ITEM_VALUE = "item_value"
    const val REMARK = "remark"
    const val IMAGE = "image"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val IS_MANDATORY = "is_mandatory"
    const val MODIFIED_AT = "modified_at"
    const val LAST_UPLOAD_AT = "last_upload_at"
    const val TYPE = "type"
    const val INDEX_1 = "$TB_NAME-INDEX1"
}