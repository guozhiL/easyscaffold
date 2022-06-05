package com.guozhi.easyscaffold.base

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.guozhi.easyscaffold.utils.ViewBindingUtils

/**
 *  author: liuguozhi
 *  create: 2022/6/4 16:13
 *  desc  : 业务层继承此类，此类规范了Fragment的使用
 */
open class BaseFragment<VB : ViewBinding> : Fragment() {

    protected val TAG: String = javaClass.simpleName

    protected var binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewBindingUtils.inflateWithGeneric(this, layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initVariants()
        loadData()
    }

    /**
     * 初始化参数
     */
    open fun initVariants() {

    }

    /**
     * 初始化view
     */
    open fun initView() {

    }

    /**
     * 加载数据
     */
    open fun loadData() {

    }

    open fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val fragments = childFragmentManager.fragments
        for (fragment in fragments) {
            // 这个 Fragment 不是 BaseFragment 的子类，或者不可见状态，就不管它
            if (fragment !is BaseFragment<*> || fragment.lifecycle.currentState != Lifecycle.State.RESUMED) {
                continue
            }
            // 将按键事件派发给子 Fragment 进行处理
            if (fragment.dispatchKeyEvent(event)) {
                // 如果子 Fragment 拦截了这个事件，那么就不交给父 Fragment 处理
                return true
            }
        }

        return when (event?.action) {
            KeyEvent.ACTION_DOWN -> onKeyDown(event.keyCode, event)
            KeyEvent.ACTION_UP -> onKeyUp(event.keyCode, event)
            else -> false
        }
    }

    /**
     * 按键按下事件回调
     */
    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 默认不拦截按键事件
        return false
    }

    /**
     * 按键抬起事件回调
     */
    open fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        // 默认不拦截按键事件
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Fragment 的生命周期比 View 的周期长，在 destroyView 时需要将 binding 置空
    }

}