package com.domikado.bit.domain.domainmodel

sealed class Result<out E, out V> {
    data class Value<out V>(val value: V): Result<Nothing, V>()
    data class Error<out E>(val error: E): Result<E, Nothing>()

    companion object Factory{

        inline fun <V> build(function: () -> V): Result<Throwable, V> =
            try {
                Value(function.invoke())
            } catch (t: Throwable) {
                Error(t)
            }
    }
}