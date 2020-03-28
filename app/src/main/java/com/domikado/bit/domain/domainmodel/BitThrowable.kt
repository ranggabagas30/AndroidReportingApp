package com.domikado.bit.domain.domainmodel

sealed class BitThrowable : Throwable() {
    data class BitIllegalStateException(val additionalMessage: String?): IllegalStateException()
    data class BitApiResponseException(val additionalMessage: String? = "Terjadi kesalahan"): BitThrowable()
    data class BitApiTokenNullException(val errorMessage: String = "API TOKEN kosong"): BitThrowable()
    data class BitLocationNullException(val errorMessage: String = "Gagal mendapatkan GPS location"): BitThrowable()
}

