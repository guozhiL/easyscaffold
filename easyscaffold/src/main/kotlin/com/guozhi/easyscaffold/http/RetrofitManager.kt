package com.guozhi.easyscaffold.http

import androidx.annotation.MainThread
import com.guozhi.easyscaffold.config.GlobalConfig
import com.guozhi.easyscaffold.http.core.IRetrofitFactory
import com.guozhi.easyscaffold.http.core.RetrofitBaseUrl
import com.guozhi.easyscaffold.utils.AnnotationUtils

/**
 *  author: liuguozhi
 *  create: 2022/6/6 23:33
 *  desc  : Retrofit的管理类，可以管理多个Retrofit，不同的api对应不同的Retrofit
 */
object RetrofitManager {

    private val globalRetrofit = HashMap<String, IRetrofitFactory>()
    private var defaultFactor: IRetrofitFactory? = null

    /**
     * 设置默认的工厂
     */
    fun setDefaultRetrofitFactor(retrofitFactor: IRetrofitFactory) {
        this.defaultFactor = retrofitFactor
    }

    /**
     * 对外提供 ServiceApi的接口
     * @param apiClazz api接口
     * @param factor 用来解决一个api对应一个工厂，为空就用默认的工厂
     * */
    @MainThread
    fun <T> provideServiceApi(apiClazz: Class<T>, factor: IRetrofitFactory? = null): T {
        //解析baseUrl
        val baseUrl = prepareBaseUrl(apiClazz)
        //将baseUrl和Retrofit一一对应起来
        var retrofit = globalRetrofit[baseUrl]
        if (retrofit == null) {
            retrofit = factor ?: defaultFactor ?: DefaultRetrofitFactor()
            retrofit.baseUrl(baseUrl)
            globalRetrofit[baseUrl] = retrofit
        }
        return retrofit.provideServiceApi(apiClazz)
    }

    /**
     * 解析apiClazz类上面的RetrofitBaseUrl注解，如果不存在那么就用默认[GlobalConfig.instance]
     */
    private fun <T> prepareBaseUrl(apiClazz: Class<T>): String {
        return AnnotationUtils.getClassAnnotation(apiClazz, RetrofitBaseUrl::class.java)?.value
            ?: GlobalConfig.instance.baseHttpUrl
    }

}