package com.domikado.bit.data.local.database.entity

import androidx.room.*

@Entity(
    tableName = SITE_MONITORING_JOIN.TB_NAME,
    indices = [
        Index(value = [SITE_MONITORING_JOIN.SCHEDULE_ID], name = SITE_MONITORING_JOIN.INDEX_SCHEDULE),
        Index(value = [SITE_MONITORING_JOIN.SITE_ID], name = SITE_MONITORING_JOIN.INDEX_SITE)
    ],
    //primaryKeys = [SITE_MONITORING_JOIN.ID, SITE_MONITORING_JOIN.SCHEDULE_ID, SITE_MONITORING_JOIN.SITE_ID],
    foreignKeys = [
        ForeignKey(
            entity = TbSchedule::class,
            parentColumns = [SCHEDULE.ID],
            childColumns = [SITE_MONITORING_JOIN.SCHEDULE_ID]
        ),
        ForeignKey(
            entity = TbSite::class,
            parentColumns = [SITE.ID],
            childColumns = [SITE_MONITORING_JOIN.SITE_ID]
        )
    ]
)
data class TbJoinSiteMonitoring(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SITE_MONITORING_JOIN.ID)
    val id: Int = 0,
    val schedule_id: Int,
    val site_id: Int
)

object SITE_MONITORING_JOIN {
    const val TB_NAME = "site_monitoring_join"
    const val ID = "site_monitor_id"
    const val SCHEDULE_ID = "schedule_id"
    const val SITE_ID = "site_id"

    const val INDEX_SCHEDULE = "$TB_NAME-SCHEDULE_INDEX"
    const val INDEX_SITE = "$TB_NAME-SITE_INDEX"
}
