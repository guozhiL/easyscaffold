package com.guozhi.easyscaffold.http

import com.guozhi.easyscaffold.BuildConfig
import com.guozhi.easyscaffold.http.core.IRetrofitFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  author: liuguozhi
 *  create: 2022/6/6 22:50
 *  desc  : 默认的构造工厂
 */
internal class DefaultRetrofitFactor : IRetrofitFactory() {

    override fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in createInterceptor()) {
            builder.addInterceptor(interceptor)
        }
        return builder
            .retryOnConnectionFailure(true) // 错误重连
            .connectTimeout(5, TimeUnit.SECONDS) // 连接超时
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时
            .build()
    }

    private fun createInterceptor(): List<Interceptor> {
        val httpLogInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return arrayListOf(httpLogInterceptor)
    }

}