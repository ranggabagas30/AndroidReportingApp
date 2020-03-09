package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * associative JSONTable FormData (many-to_many)
 * defined FK: schedule_id, operator_id, item_id, row_id
 * */
@Entity(
    tableName = "form_data"
)
data class TbFormData(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "schedule_id")
    val scheduleId: Int,

    @ColumnInfo(name = "operator_id")
    val operatorId: Int,

    @ColumnInfo(name = "item_id")
    val itemId: Int,

    @ColumnInfo(name = "row_id")
    val rowId: Int,

    @ColumnInfo(name = "gps_accuracy")
    val gpsAccuracy: Double?,

    val latitude: Double?,

    val longitude: Double?,

    val value: String?, // isian data form

    val remark: String?, // isian data remark untuk item form bertipe "Photo"

    @ColumnInfo(name = "upload_status")
    val uploadStatus: Int?, // status upload { SUCCESS, FAILED }

    @ColumnInfo(name = "photo_date")
    val photoDate: String?, // tanggal pengambilan foto untuk item form bertipe "Photo"

    @ColumnInfo(name = "created_at")
    val createdAt: String? // tanggal pertama kali pengisian data untuk item form ini


)