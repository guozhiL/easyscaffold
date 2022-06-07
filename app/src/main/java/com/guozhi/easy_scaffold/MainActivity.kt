package com.guozhi.easy_scaffold

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.guozhi.easyscaffold.R
import com.guozhi.easyscaffold.config.GlobalConfig
import com.guozhi.easyscaffold.http.RetrofitManager
import com.guozhi.easyscaffold.http.core.IRetrofitFactory
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testCache()
        testNetWork()
    }

    private fun testNetWork() {
        //全局的url
        GlobalConfig.instance.baseHttpUrl = "www.wanAndroid.com"
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