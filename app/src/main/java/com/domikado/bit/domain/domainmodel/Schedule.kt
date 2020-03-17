package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONSchedule

data class Schedule(
    val id: Int,
    val user: User?,
    val site: List<Site>?,
    val operators: List<Operator>?,
    val workType: WorkType,
    val workDate: Long,
    val progress: Int,
    val status: String = "New",
    val rejection: String?
)

internal val JSONSchedule.toSchedule
    get() = Schedule(
        this.id,
        this.user.toUser,
        listOf(this.site.toSite),
        this.operators.map { it.toOperator },
        this.work_type.toWorkType,
        this.work_date.toLong(),
        this.progress,
        this.status,
        this.rejection.toString()
    )

