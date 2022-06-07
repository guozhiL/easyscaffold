package com.guozhi.easyscaffold.http.core

/**
 *  author: liuguozhi
 *  create: 2022/6/7 21:40
 *  desc  : Retrofit的BaseUrl，作用于每个api接口类上面，用来表示这个api接口的base url是什么
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitBaseUrl(val value: String)