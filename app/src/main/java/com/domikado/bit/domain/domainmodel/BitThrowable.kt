package com.domikado.bit.domain.domainmodel

sealed class BitThrowable : Throwable() {
    data class BitIllegalStateException(val additionalMessage: String?): IllegalStateException()
}

