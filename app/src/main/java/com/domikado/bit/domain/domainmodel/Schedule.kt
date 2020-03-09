package com.domikado.bit.domain.domainmodel

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

