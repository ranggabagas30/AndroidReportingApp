package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONOperator

data class Operator(
    val id: Int,
    val name: String = "Unknown"
)

internal val JSONOperator.toOperator
    get() = Operator(
        this.id,
        this.name
    )