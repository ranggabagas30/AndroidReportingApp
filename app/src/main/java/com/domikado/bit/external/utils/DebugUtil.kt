package com.domikado.bit.external.utils

import com.github.ajalt.timberkt.Timber
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

object DebugUtil {
    fun getReadableErrorMessage(error: Throwable): String {
        val API_STATUS_CODE_LOCAL_ERROR = 0
        val errorMessage: String
        errorMessage = when (error) {
            is HttpException -> {
                when (error.code()) {
                    HttpsURLConnection.HTTP_BAD_REQUEST -> "Bad Request (Code: 400)"
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> "Tidak mendapatkan hak akses (Code: 401)"
                    HttpsURLConnection.HTTP_FORBIDDEN -> "Forbidden access (Code: 404)"
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> "Masalah pada server (Code: 500)"
                    HttpURLConnection.HTTP_BAD_GATEWAY -> "Masalah pada server (Code: 502)"
                    HttpURLConnection.HTTP_UNAVAILABLE -> "Service tidak tersedia (Code: 503)"
                    HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> "Masalah pada server (Code: 504)"
                    API_STATUS_CODE_LOCAL_ERROR -> "Masalah pada API (Code: 0)"
                    else -> error.getLocalizedMessage()
                }
            }
            is JsonSyntaxException -> {
                "Masalah pada format API (Code: 666)"
            }
            is UnknownHostException -> {
                "Koneksi terputus. Pastikan koneksi stabil"
            }
            is SocketTimeoutException -> {
                "Koneksi ke server terputus (Socket Timeout)"
            }
            is ConnectException -> {
                "Tidak ada koneksi ke server. Pastikan jaringan internet aktif dan stabil"
            }
            else -> {
                error.message?: "Terjadi kesalahan"
            }
        }
        Timber.e(error) { errorMessage }
        return errorMessage
    }
}