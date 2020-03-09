package com.domikado.bit.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = SCHEDULE.TB_NAME,
    foreignKeys = [
        ForeignKey(
            entity = TbUser::class,
            parentColumns = [USER.ID],
            childColumns = [SCHEDULE.USER_ID]
        ),
        ForeignKey(
            entity = TbWorkType::class,
            parentColumns = [WORK_TYPE.ID],
            childColumns = [SCHEDULE.WORK_TYPE_ID]
        )
    ]
)
data class TbSchedule(

    @PrimaryKey
    @ColumnInfo(name = SCHEDULE.ID)
    val id: Int,

    @ColumnInfo(name = SCHEDULE.WORK_DATE)
    val workDate: Long?,

    val progress: Int?, // persentase progress

    val status: String?, // i.e. "ON PROGRESS", "ON REVIEW", etc

    @ColumnInfo(name = SCHEDULE.USER_ID)
    val userId: Int,

    @ColumnInfo(name = SCHEDULE.WORK_TYPE_ID)
    val workTypeId: Int,

    val rejection: String?
)

object SCHEDULE{
    const val TB_NAME = "schedule"
    const val PREFIX = TB_NAME + "_"
    const val ID = "id"
    const val WORK_DATE = "work_date"
    const val USER_ID = "user_id"
    const val WORK_TYPE_ID = "work_type_id"
    const val WORK_TYPE = "work_type"
}