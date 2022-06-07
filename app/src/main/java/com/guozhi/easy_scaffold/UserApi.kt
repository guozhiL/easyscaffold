package com.guozhi.easy_scaffold

import com.guozhi.easyscaffold.http.core.RetrofitBaseUrl
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  author: liuguozhi
 *  create: 2022/6/7 22:30
 *  desc  :
 */
@RetrofitBaseUrl("www.baidu.com")
interface UserApi {

    @GET("/user/userName")
    suspend fun getUserName(@Query("id") id: String): String

}