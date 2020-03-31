package com.domikado.bit.domain.domainmodel

sealed class BitThrowable : Throwable() {
    data class BitIllegalAuthException(override var message: String = "Gagal melakukan authentikasi"): BitThrowable()
    data class BitIllegalStateException(override var message: String?): IllegalStateException()
    data class BitApiResponseException(override var message: String? = "Terjadi kesalahan"): BitThrowable()
    data class BitApiTokenNullException(override var message: String = "API TOKEN kosong"): BitThrowable()
    data class BitLocationNullException(override var message: String = "Gagal mendapatkan GPS location"): BitThrowable()
}

