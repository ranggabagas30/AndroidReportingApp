package com.domikado.bit.data.local.database.entity

import androidx.room.*

@Entity(
    tableName = SCHEDULE.TB_NAME,
    indices = [
        Index(value = [SCHEDULE.USER_ID], name = SCHEDULE.INDEX_USER_ID)
    ],
    foreignKeys = [
        ForeignKey(
            entity = TbUser::class,
            parentColumns = [USER.ID],
            childColumns = [SCHEDULE.USER_ID]
        )
    ]
)
data class TbSchedule(

    @PrimaryKey
    @ColumnInfo(name = SCHEDULE.ID)
    val id: Int,

    @ColumnInfo(name = SCHEDULE.WORK_DATE)
    val workDate: String?,

    @ColumnInfo(name = SCHEDULE.PROGRESS)
    val progress: Int, // persentase progress

    @ColumnInfo(name = SCHEDULE.PIC_STATUS)
    val picStatus: Int,

    @ColumnInfo(name = SCHEDULE.PIC_STATUS_TEXT)
    val picStatusText: String?,

    @ColumnInfo(name = SCHEDULE.PM_STATUS)
    val pmStatus: Int,

    @ColumnInfo(name = SCHEDULE.PM_STATUS_TEXT)
    val pmStatusText: String?,

    @ColumnInfo(name = SCHEDULE.USER_ID)
    val userId: String?,

    @ColumnInfo(name = SCHEDULE.REJECTION)
    val rejection: String?
)

object SCHEDULE{
    const val TB_NAME = "schedule"
    const val ID = "id"
    const val USER_ID = "user_id"
    const val WORK_DATE = "work_date"
    const val PROGRESS = "progress"
    const val PIC_STATUS = "pic_status"
    const val PIC_STATUS_TEXT = "pic_status_text"
    const val PM_STATUS = "pm_status"
    const val PM_STATUS_TEXT = "pm_status_text"
    const val REJECTION = "rejection"

    const val INDEX_USER_ID = "${TB_NAME}-USER_INDEX"
}