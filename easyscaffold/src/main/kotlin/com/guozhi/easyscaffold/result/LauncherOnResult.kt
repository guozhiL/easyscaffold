package com.guozhi.easyscaffold.result

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 *  author: liuguozhi
 *  create: 2022/6/12 10:16
 *  desc  : 封装新 ActivityResult API，随时注册、随时用，不必在onCreate、onStart中注册
 */
class LauncherOnResult {

    private val mFragmentManager: FragmentManager

    constructor(fragment: Fragment) {
        mFragmentManager = fragment.childFragmentManager
    }

    constructor(activity: FragmentActivity) {
        mFragmentManager = activity.supportFragmentManager
    }

    private fun <I, O> getOnResultFragment(
        manager: FragmentManager,
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ): LauncherFragment<I, O> {

        val fragment = LauncherFragment<I, O>()

        fragment.initActivityResult(contract, callback)

        manager.beginTransaction()
            .add(fragment, makeFragmentName())
            .commitAllowingStateLoss()

        manager.executePendingTransactions()

        return fragment
    }

    fun <I, O> startActivityForResult(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ) = getOnResultFragment(mFragmentManager, contract, callback).startActivityForResult()

    /**
     * 唯一的FragmentName
     */
    private fun makeFragmentName(): String {
        return "com.guozhi.easyscaffold.result"
    }

}