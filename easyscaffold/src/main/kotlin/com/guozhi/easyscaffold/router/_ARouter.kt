package com.guozhi.easyscaffold.router

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.facade.Postcard
import com.guozhi.easyscaffold.result.launcherActivityForResult

/**
 *  author: liuguozhi
 *  create: 2022/6/11 16:31
 *  desc  : ARouter的扩展函数
 */

fun Fragment.routerForResult(
    postcard: Postcard,
    callback: ActivityResultCallback<ActivityResult>
) {
    //寻找路径对应的目标Class
    LogisticsCenter.completion(postcard)

    val intent = Intent(requireActivity(), postcard.destination)
        .putExtras(postcard.extras)

    launcherActivityForResult(intent, callback)
}