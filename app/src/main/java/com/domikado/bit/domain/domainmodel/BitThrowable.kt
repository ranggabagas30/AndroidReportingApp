package com.domikado.bit.domain.domainmodel

sealed class BitThrowable : Throwable() {
    data class BitIllegalAuthException(override var message: String = "Gagal melakukan authentikasi"): BitThrowable()
    data class BitIllegalStateException(override var message: String?): IllegalStateException()
    data class BitApiResponseException(override var message: String? = "Terjadi kesalahan pada respon API"): BitThrowable()
    data class BitApiTokenNullException(override var message: String = "API TOKEN kosong"): BitThrowable()
    data class BitLocationException(override var message: String = "Terjadi kesalahan pada fitur lokasi"): BitThrowable()
    data class BitFirebaseException(override var message: String = "Terjadi kesalahan pada Firebase"): BitThrowable()
}

