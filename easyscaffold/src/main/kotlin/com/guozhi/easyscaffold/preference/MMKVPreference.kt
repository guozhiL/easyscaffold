package com.guozhi.easyscaffold.preference

import android.os.Parcelable
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *  author: liuguozhi
 *  create: 2022/6/5 17:46
 *  desc  : mmkv的委托属性代理
 */
abstract class MMKVPreference(name: String) {
    val innerMMKV: MMKV by lazy { MMKV.mmkvWithID(name, MMKV.SINGLE_PROCESS_MODE) }
    val gson by lazy { Gson() }

    /**
     * by bind
     */
    inline fun <reified T> bind(default: T): ReadWriteProperty<MMKVPreference, T> {
        return object : ReadWriteProperty<MMKVPreference, T> {
            override fun getValue(thisRef: MMKVPreference, property: KProperty<*>): T {
                return getPreferences(property.name, default)
            }

            override fun setValue(thisRef: MMKVPreference, property: KProperty<*>, value: T) {
                putPreferences(property.name, value)
            }
        }
    }

    inline fun <reified T> getPreferences(key: String, default: T): T {
        val value: Any? = when (default) {
            is Int -> innerMMKV.decodeInt(key, default)
            is Long -> innerMMKV.decodeLong(key, default)
            is String -> innerMMKV.decodeString(key, default)
            is Boolean -> innerMMKV.decodeBool(key, default)
            is Double -> innerMMKV.decodeDouble(key, default)
            is Float -> innerMMKV.decodeFloat(key, default)
            is Parcelable -> innerMMKV.decodeParcelable(key, default.javaClass, default)
            else -> {
                val objStr = innerMMKV.decodeString(key, "")
                if (objStr.isNullOrEmpty()) {
                    default
                } else {
                    gson.fromJson(objStr, T::class.java)
                }
            }
        }
        return value as T
    }

    inline fun <reified T> putPreferences(key: String, value: T) {
        when (value) {
            is Int -> innerMMKV.encode(key, value)
            is Long -> innerMMKV.encode(key, value)
            is String -> innerMMKV.encode(key, value)
            is Boolean -> innerMMKV.encode(key, value)
            is Double -> innerMMKV.encode(key, value)
            is Float -> innerMMKV.encode(key, value)
            is Parcelable -> innerMMKV.encode(key, value)
            else -> {
                val gsonStr = gson.toJson(value)
                innerMMKV.putString(key, gsonStr)
            }
        }
    }

    /**
     * 获取所有的key
     */
    fun getAllKeys(): Array<String>? = innerMMKV.allKeys()

    /**
     * 是否包含key
     */
    fun contains(key: String) = innerMMKV.contains(key)

    /**
     * 删除key对应的数据
     */
    fun remove(key: String) {
        innerMMKV.remove(key)
    }

    /**
     * 删除所有的数据
     */
    fun clearAll() {
        innerMMKV.clearAll()
    }

}