package com.guozhi.easyscaffold.result

import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment

/**
 *  author: liuguozhi
 *  create: 2022/6/12 10:12
 *  desc  : 用来处理startActivityForResult的中间Fragment
 */
class LauncherFragment<I, O> : Fragment() {

    private lateinit var launcher: ActivityResultLauncher<I>
    private lateinit var contract: ActivityResultContract<I, O>
    private lateinit var callback: ActivityResultCallback<O>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(contract, callback)
    }

    internal fun initActivityResult(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ) {
        this.contract = contract
        this.callback = callback
    }

    fun startActivityForResult() = launcher

}