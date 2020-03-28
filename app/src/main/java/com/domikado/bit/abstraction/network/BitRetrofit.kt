package com.domikado.bit.abstraction.network

import com.domikado.bit.BuildConfig
import com.domikado.bit.utility.PREF_KEY_ACCESS_TOKEN
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.pixplicity.easyprefs.library.Prefs
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BitRetrofit {

    companion object {
        private const val CONNECTION_TIMEOUT = 1L // minute
        private const val READ_TIMEOUT = 30L // seconds
        private const val WRITE_TIMEOUT = 15L // seconds
        private const val API_BASE_URL = BuildConfig.BASE_URL + "/api/"

        private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val httpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

        private val gson = GsonBuilder().setLenient().create()

        private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        private lateinit var retrofit: Retrofit

        fun <T> createService(serviceClass: Class<T>): T {

            if (!httpClient.interceptors().contains(httpLoggingInterceptor)) {
                httpClient.addInterceptor(httpLoggingInterceptor)
            }

            val authenticationInterceptor = AuthenticationInterceptor()
            if (!httpClient.interceptors().contains(authenticationInterceptor)) {
                httpClient.addInterceptor(authenticationInterceptor)
            }

            retrofit = retrofitBuilder
                .client(httpClient.build())
                .build()

            return retrofit.create(serviceClass)
        }

        private class AuthenticationInterceptor: Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalHttpUrl = original.url()
                val modifiedHttpUrl: HttpUrl
                val modifiedHttpUrlBuilder = originalHttpUrl.newBuilder()

                Prefs.getString(PREF_KEY_ACCESS_TOKEN, null).also { accessToken ->
                    accessToken?.let { modifiedHttpUrlBuilder.addQueryParameter("access_token", accessToken) }
                }

                modifiedHttpUrl = modifiedHttpUrlBuilder.build()
                val modifiedReqBuilder = original.newBuilder()
                    .url(modifiedHttpUrl)

                val modified = modifiedReqBuilder.build()

                return chain.proceed(modified)
            }
        }
    }
}