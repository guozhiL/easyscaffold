package com.guozhi.easyscaffold.extention

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 *  author: liuguozhi
 *  create: 2022/6/4 17:47
 *  desc  : View的扩展函数
 */


/**
 * 显示EditText 软件盘
 */
fun EditText.showSoftInput() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

/**
 * 隐藏软件盘
 */
fun View.hideSoftInput() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}
