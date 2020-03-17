package com.domikado.bit.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = SCHEDULE_SITE_JOIN.TB_NAME,
    indices = [
        Index(value = [SCHEDULE_SITE_JOIN.SCHEDULE_ID], name = SCHEDULE_SITE_JOIN.INDEX_SCHEDULE),
        Index(value = [SCHEDULE_SITE_JOIN.SITE_ID], name = SCHEDULE_SITE_JOIN.INDEX_SITE)
    ],
    primaryKeys = [SCHEDULE_SITE_JOIN.SCHEDULE_ID, SCHEDULE_SITE_JOIN.SITE_ID],
    foreignKeys = [
        ForeignKey(
            entity = TbSchedule::class,
            parentColumns = [SCHEDULE.ID],
            childColumns = [SCHEDULE_SITE_JOIN.SCHEDULE_ID]
        ),
        ForeignKey(
            entity = TbSite::class,
            parentColumns = [SITE.ID],
            childColumns = [SCHEDULE_SITE_JOIN.SITE_ID]
        )
    ]
)
data class TbJoinScheduleSite(
    val schedule_id: Int,
    val site_id: Int
)

object SCHEDULE_SITE_JOIN {
    const val TB_NAME = "schedule_site_join"
    const val SCHEDULE_ID = "schedule_id"
    const val SITE_ID = "site_id"

    const val INDEX_SCHEDULE = "$TB_NAME-SCHEDULE_INDEX"
    const val INDEX_SITE = "$TB_NAME-SITE_INDEX"
}
