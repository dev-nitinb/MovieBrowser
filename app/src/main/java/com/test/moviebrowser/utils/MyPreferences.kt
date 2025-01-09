package com.test.moviebrowser.utils

import android.content.Context
import android.content.SharedPreferences

class MyPreferences(context: Context) {
    private val sharedPreferences:SharedPreferences?

    init {
        sharedPreferences= context.getSharedPreferences(
            "MovieApp",
            Context.MODE_PRIVATE

        )
    }

    var isFirstTime:Boolean
        get()=sharedPreferences!!.getBoolean("isFirstTime",false) ?: false
        set(value) {
            val prefeditor=sharedPreferences!!.edit()
            prefeditor.putBoolean("isFirstTime",value)
            prefeditor.apply()
        }

    companion object{
        private var myPreferences: MyPreferences?=null
        val TAG="Preferences"
        fun getInstance(context: Context): MyPreferences {
            if(myPreferences ==null){
                myPreferences =
                    MyPreferences(context)
            }
            return myPreferences!!
        }

    }
}