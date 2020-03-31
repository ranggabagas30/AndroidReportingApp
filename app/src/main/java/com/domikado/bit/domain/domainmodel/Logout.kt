package com.domikado.bit.domain.domainmodel

import com.domikado.bit.data.remote.pojo.JSONLogout

data class Logout(
    var status: Int,
    var message: String?
)

internal val JSONLogout.toLogout
    get() = Logout(
        this.status,
        this.message
    )
