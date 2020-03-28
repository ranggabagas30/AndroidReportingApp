package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONSchedule

data class Schedule(
    val id: Int,
    val user: User?,
    val site: List<Site>?,
    val operators: List<Operator>?,
    val workType: WorkType?,
    val workDate: String?,
    val progress: Int = 0,
    val status: String?,
    val rejection: String?
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
                this.operator_code,
                this.operator_name,
                this.operator_detail_name
            )
        ),
        null,
        this.work_date,
        status = null,
        rejection = toReadableRejection(
            this.pic_status,
            this.pm_status,
            this.pic_reject_at,
            this.pm_reject_at,
            this.remark_schedule
        )
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
    return if (picStatus == 0 || pmStatus == 0) {
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

