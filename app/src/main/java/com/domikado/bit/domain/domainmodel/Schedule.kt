package com.domikado.bit.domain.domainmodel

import android.text.TextUtils
import com.domikado.bit.data.localrepo.database.entity.TbSchedule
import com.domikado.bit.data.remoterepo.pojo.JSONSchedule
import com.domikado.bit.presentation.screen.schedulelist.recyclerview.ScheduleModel

data class Schedule(
    val id: Int,
    val user: User?,
    val site: List<Site>?,
    val operators: List<Operator>?,
    val workDate: String?,
    val progress: Int? = null, // percentage of work
    val picStatus: Int,
    val pmStatus: Int,
    val picStatusText: String?,
    val pmStatusText: String?,
    val rejection: String?,
    val scheduleType: Int,
    val scheduleTypeText: String?,
    val ringId: String,
    val ringName: String?,
    val clusterName: String?
)

internal val Schedule.toTbSchedule
    get() = TbSchedule(
        this.id, // ignored
        this.workDate,
        this.progress?: 0,
        this.picStatus,
        this.picStatusText,
        this.pmStatus,
        this.pmStatusText,
        this.user?.id,
        this.rejection
    )

internal val TbSchedule.toSchedule
    get() = Schedule(
        this.id,
        null,
        null,
        null,
        this.workDate,
        this.progress,
        this.picStatus,
        this.pmStatus,
        this.picStatusText,
        this.pmStatusText,
        this.rejection,
        -1,
        null,
        "n/a",
        "n/a",
        "n/a"
    )

internal val JSONSchedule.toSchedule
    get() = Schedule(
        this.id_schedule,
        null,
        this.site?.map {
            it.toSite
        },
        listOf(
            Operator(
                0, // ignored
                this.operator_name,
                this.operator_detail_name,
                this.operator_code
            )
        ),
        this.work_date,
        picStatus = this.pic_status,
        picStatusText = this.pic_status_text,
        pmStatus = this.pm_status,
        pmStatusText = this.pm_status_text,
        rejection = toReadableRejection(
            this.pic_status,
            this.pm_status,
            this.pic_reject_at,
            this.pm_reject_at,
            this.remark_schedule
        ),
        scheduleType = this.schedule_type,
        scheduleTypeText = this.schedule_type_text,
        ringId = this.ring_id,
        ringName = this.ring_name,
        clusterName = this.cluster_name
    )

internal val Schedule.toScheduleModel
    get() = ScheduleModel(
        this.id,
        this.progress,
        this.picStatus,
        this.pmStatus,
        this.picStatusText?: "N/A",
        this.pmStatusText?: "N/A",
        this.rejection,
        this.workDate,
        this.site?.map { it.toSiteModel },
        this.operators,
        isAllowed = true,
        scheduleType = this.scheduleType,
        scheduleTypeText = this.scheduleTypeText?: "N/A tipe schedule",
        ringId = this.ringId,
        ringName = this.ringName?: "N/A nama ring",
        clusterName = this.clusterName?: "N/A nama cluster"
    )

private fun toReadableStatus(code: Int): String {
    return when(code) {
        0 -> "Progress"
        1 -> "Check In"
        2 -> "Finish"
        else -> "Unknown status"
    }
}

private fun toReadableRejection(
    picStatus: Int,
    pmStatus: Int,
    picRejectedAt: String?,
    pmRejectedAt: String?,
    remarkSchedule: String?
): String? {
    return if (!TextUtils.isEmpty(remarkSchedule)) {
        String.format(
            "Rejected by :  \n" +
            if (picStatus != 0) {
                "PIC pada tanggal $picRejectedAt\n"
            } else "" +
            if (pmStatus != 0) {
                "PM pada tanggal $pmRejectedAt\n"
            } else "" +
            remarkSchedule?.let {
                "dengan alasan $it"
            }
        )
    } else null
}

