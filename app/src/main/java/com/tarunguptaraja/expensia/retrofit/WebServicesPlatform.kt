package com.tarunguptaraja.expensia.retrofit

import com.google.gson.GsonBuilder
import com.tarunguptaraja.expensia.BuildConfig
import com.tarunguptaraja.expensia.extensions.GenericParamInterceptor
import com.tarunguptaraja.expensia.extensions.retrieveString
import com.tarunguptaraja.expensia.retrofit.SocketFactory.sslSocketFactory
import com.tarunguptaraja.expensia.retrofit.SocketFactory.trustManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WebServicesPlatform {
    const val baseUrl = BuildConfig.BASE_URL
    private val okhttpClient = OkHttpClient.Builder().addInterceptor(GenericParamInterceptor())
        .connectTimeout(1, TimeUnit.MINUTES).writeTimeout(1000, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.MINUTES).sslSocketFactory(sslSocketFactory!!, trustManager!!)
        .addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .addHeader("Authorization", "Token " + retrieveString("jwtToken"))
                .method(original.method, original.body).build()

            chain.proceedByCheckingCache(request)
        }

    val retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .client(okhttpClient.apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
        }.build()).build()
}