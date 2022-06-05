package com.guozhi.easyscaffold.base

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *  author: liuguozhi
 *  create: 2022/6/4 17:35
 *  desc  : 业务层继承此类，此类规范了ViewModel的使用
 */
open class BaseViewModel : ViewModel() {

    protected val tag: String = javaClass.simpleName

    // 提示的消息
    private val messageStr by lazy { MutableLiveData<String>() }

    // 提示的消息
    private val message by lazy { MutableLiveData<Int>() }

    // 错误、异常的消息
    private val error by lazy { MutableLiveData<Throwable>() }

    // loading 消息
    private val progress by lazy { MutableLiveData<Boolean>() }

    // 刷新的消息
    private val refresh by lazy { MutableLiveData<Boolean>() }

    fun messageStr() = messageStr

    fun message() = message

    fun error() = error

    fun progress() = progress

    fun refresh() = refresh

    /**
     * 发送一条普通消息
     */
    protected open fun postMessageStr(m: String) {
        messageStr().postValue(m)
    }

    /**
     * 发送一条普通消息
     */
    protected open fun postMessage(@StringRes m: Int) {
        message().postValue(m)
    }

    /**
     * 显示loading
     */
    protected open fun showProgress() {
        progress().postValue(true)
    }

    /**
     * 隐藏loading
     */
    protected open fun hideProgress() {
        progress().postValue(false)
    }

    /**
     * 发送一条错误消息
     */
    protected open fun postError(e: Throwable?) {
        error().postValue(e)
    }

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(context, start, block)
    }

    /**
     * 以 io 调度器开启协程
     * @param start CoroutineStart
     * @param block 相关操作
     */
    protected fun launchIO(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO, start, block)
    }
}