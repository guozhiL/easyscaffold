package com.guozhi.app

import android.app.Application
import com.guozhi.easyscaffold.router.RouterManager

/**
 *  author: liuguozhi
 *  create: 2022/6/12 12:32
 *  desc  :
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLib()
    }

    private fun initLib() {
        RouterManager.initRouter(this)
    }
}