package com.guozhi.app

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.guozhi.easyscaffold.base.BaseActivity
import com.guozhi.easyscaffold.config.GlobalConfig
import com.guozhi.easyscaffold.databinding.ActivityMainBinding
import com.guozhi.easyscaffold.http.RetrofitManager
import com.guozhi.easyscaffold.http.core.IRetrofitFactory
import com.guozhi.easyscaffold.result.launcherActivityForResult
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Route(path = "/app/main")
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testCache()
        testNetWork()
        initListener()
    }

    private fun initListener() {
        binding.run {
            btRouter.setOnClickListener {
                ARouter.getInstance().build("/app/main").navigation(this@MainActivity)
            }
            btResult.setOnClickListener {
                launcherActivityForResult(Intent(this@MainActivity, MainActivity::class.java)) {
                    Log.e(TAG, "launcherActivityForResult: ${it.data?.getStringExtra("Result")}")
                }
            }
        }
    }

    override fun finish() {
        val rst = Intent()
        rst.putExtra("Result", "返回结果111")
        setResult(RESULT_OK, rst)
        super.finish()
    }

    private fun testNetWork() {
        //全局的url
        GlobalConfig.instance.baseHttpUrl = "https://www.wanAndroid.com"
        //设置默认的构造工厂
        RetrofitManager.setDefaultRetrofitFactor(object : IRetrofitFactory() {
            override fun createRetrofit(): Retrofit {
                return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        })
        //用默认的构造工厂来生产出userApi1
        val userApi1 =
            RetrofitManager.provideServiceApi(UserApi::class.java)
        //用自定义的构造工厂来生产出userApi2
        val userApi2 =
            RetrofitManager.provideServiceApi(UserApi::class.java, object : IRetrofitFactory() {
                override fun createRetrofit(): Retrofit {
                    return Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(OkHttpClient.Builder().build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            })
    }

    private fun testCache() {
        MMKV.initialize(applicationContext)

        Log.e(TAG, "onCreate: ${CachePreference.name}")
        Log.e(TAG, "onCreate: ${CachePreference.age}")
        Log.e(TAG, "onCreate: ${CachePreference.list}")
        Log.e(TAG, "onCreate: ${CachePreference.map}")
        Log.e(TAG, "onCreate: ${CachePreference.student}")

        CachePreference.name = "大帅逼"
        CachePreference.age = 21
        CachePreference.list = listOf(99, 22)
        CachePreference.map = mapOf(Pair("ke2", 2))
        CachePreference.student = Student("张三", "红花小学")

    }

    class Student(var name: String?, var school: String?) : Parcelable {

        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(school)
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun toString(): String {
            return "Student(name=$name, school=$school)"
        }

        companion object CREATOR : Parcelable.Creator<Student> {
            override fun createFromParcel(parcel: Parcel): Student {
                return Student(parcel)
            }

            override fun newArray(size: Int): Array<Student?> {
                return arrayOfNulls(size)
            }
        }


    }

}