package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONSite

data class Site(
    val id: Int,
    val name: String,
    val siteLocation: String,
    val siteIdCustomer: String,
    val status: Int
)

internal val JSONSite.toSite
    get() = Site(
        this.id,
        this.name,
        this.locationStr,
        this.site_id_customer,
        this.status
    )

