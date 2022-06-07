package com.guozhi.easyscaffold.http.core

import retrofit2.Retrofit

/**
 *  author: liuguozhi
 *  create: 2022/6/6 22:47
 *  desc  : Retrofit的工厂类
 */
abstract class IRetrofitFactory {

    lateinit var baseUrl: String

    // 当前Retrofit工厂的所有服务器的 api 接口 Map
    private val globalServiceApi = HashMap<Any, Any?>()

    private val retrofit: Retrofit by lazy { createRetrofit() }

    /**
     * 提供api接口的方法
     */
    internal fun <T> provideServiceApi(apiClazz: Class<T>): T {
        var serviceApi = globalServiceApi[apiClazz]
        if (serviceApi == null) {
            serviceApi = retrofit.create(apiClazz)
            globalServiceApi[apiClazz] = serviceApi
        }
        return serviceApi as T
    }

    fun baseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    abstract fun createRetrofit(): Retrofit

}