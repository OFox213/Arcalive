package com.devstudio.arcalive.util

import android.content.Context
import android.content.SharedPreferences

class pref(context: Context) {
    private val pref = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    fun set(key:String, value:String){
        pref.edit().putString(key, value).apply()
    }

    fun set(key:String, value:Int){
        pref.edit().putInt(key, value).apply()
    }

    fun set(key:String, value:Boolean){
        pref.edit().putBoolean(key, value).apply()
    }

    fun get(key:String,value :String) : String? {
        return pref.getString(key, value)
    }

    fun get(key:String,value :Int) : Int {
        return pref.getInt(key, value)
    }

    fun get(key:String,value :Boolean) :Boolean{
        return pref.getBoolean(key, value)
    }
}