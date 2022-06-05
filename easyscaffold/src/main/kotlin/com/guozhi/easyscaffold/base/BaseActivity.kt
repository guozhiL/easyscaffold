package com.guozhi.easyscaffold.base

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.guozhi.easyscaffold.extention.hideSoftInput
import com.guozhi.easyscaffold.utils.ViewBindingUtils

/**
 *  author: liuguozhi
 *  create: 2022/6/4 15:48
 *  desc  : 业务层继承此类，此类规范了activity的使用
 */
open class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val TAG = javaClass.simpleName

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewBindingUtils.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
        initSoftKeyboard()
        initVariants()
        initView()
        loadData()
    }

    /**
     * 给ContentView设置一个点击事件
     */
    private fun initSoftKeyboard() {
        // 点击外部隐藏软键盘
        getContentView()?.setOnClickListener { currentFocus?.hideSoftInput() }
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent)
    }

    /**
     * 处理关键的触摸事件,并且交给BaseFragment来处理
     */
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            // 这个 Fragment 不是 BaseFragment 的子类，或者不可见状态，就不管它
            if (fragment !is BaseFragment<*> || fragment.lifecycle.currentState != Lifecycle.State.RESUMED) {
                continue
            }
            //拦截触摸事件，交给fragment处理
            if (fragment.dispatchKeyEvent(event)) {
                return true
            }
        }

        return super.dispatchKeyEvent(event)
    }

    /**
     * 获取ContentView
     */
    private fun getContentView(): View? = findViewById(android.R.id.content)

    override fun finish() {
        super.finish()
        // 隐藏软键，避免内存泄漏
        currentFocus?.hideSoftInput()
    }

}