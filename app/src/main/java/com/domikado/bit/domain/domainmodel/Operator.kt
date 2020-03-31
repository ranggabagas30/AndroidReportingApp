package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.local.database.entity.TbOperator

data class Operator(
    val id: Int,
    val name: String?,
    val detailName: String?,
    val code: String?
)

internal val TbOperator.toOperator
    get() = Operator(
        this.id,
        this.name,
        this.detailName,
        this.code
    )

internal val Operator.toTbOperator
    get() = TbOperator(
        this.id,
        this.name,
        this.detailName,
        this.code
    )