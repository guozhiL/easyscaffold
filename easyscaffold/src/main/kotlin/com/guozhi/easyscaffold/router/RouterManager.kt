package com.guozhi.easyscaffold.router

import android.app.Application
import androidx.core.os.bundleOf
import com.alibaba.android.arouter.launcher.ARouter
import com.guozhi.easyscaffold.BuildConfig

/**
 *  @author: liuguozhi
 *  @create: 2022/6/9 23:24
 *  @desc  : ARouter 管理类
 */
object RouterManager {

    fun initRouter(application: Application) {
        if (BuildConfig.DEBUG) {
            //打开日志
            ARouter.openDebug()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openLog()
        }
        ARouter.init(application)
    }

}