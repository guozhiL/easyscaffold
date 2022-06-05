package com.guozhi.easy_scaffold

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.guozhi.easyscaffold.R
import com.tencent.mmkv.MMKV

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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