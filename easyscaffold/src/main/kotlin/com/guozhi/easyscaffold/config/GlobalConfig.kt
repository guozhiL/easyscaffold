package com.guozhi.easyscaffold.config

/**
 *  author: liuguozhi
 *  create: 2022/6/6 22:27
 *  desc  : 全局的配置常量
 */
class GlobalConfig {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { GlobalConfig() }
    }

    /**
     * 服务器前缀的url
     */
    var baseHttpUrl: String = "www.baidu.com"

}