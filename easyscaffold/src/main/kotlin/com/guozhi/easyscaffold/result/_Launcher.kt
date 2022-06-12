package com.guozhi.easyscaffold.result

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 *  author: liuguozhi
 *  create: 2022/6/12 15:56
 *  desc  : 简化StartActivityForResult的使用
 */

fun Fragment.launcherActivityForResult(
    intent: Intent,
    callback: ActivityResultCallback<ActivityResult>
) {
    LauncherOnResult(this)
        .startActivityForResult(ActivityResultContracts.StartActivityForResult(), callback)
        .launch(intent)
}

fun FragmentActivity.launcherActivityForResult(
    intent: Intent,
    callback: ActivityResultCallback<ActivityResult>
) {
    LauncherOnResult(this)
        .startActivityForResult(ActivityResultContracts.StartActivityForResult(), callback)
        .launch(intent)
}