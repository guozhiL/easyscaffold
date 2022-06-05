package com.guozhi.easy_scaffold

import com.guozhi.easyscaffold.preference.MMKVPreference

/**
 *  author: liuguozhi
 *  create: 2022/6/5 18:24
 *  desc  :
 */
object CachePreference : MMKVPreference("cache") {

    var name by bind("guozhi")

    var age by bind(18)

    var list by bind(listOf(1, 2, 3))

    var map by bind(mapOf(Pair("key1", 1)))

    var student by bind(MainActivity.Student("张三", "太阳小学"))
}