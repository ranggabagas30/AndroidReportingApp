package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remoterepo.pojo.JSONWorkType

data class WorkType(
    val id: Int,
    val name: String?,
    val workForms: List<WorkForm>?
)

internal val JSONWorkType.toWorkType
    get() = WorkType(
        this.id,
        this.name,
        null
    )