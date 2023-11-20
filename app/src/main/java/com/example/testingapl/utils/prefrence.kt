package com.example.testingapl.utils

import android.content.Context
import android.content.SharedPreferences

class prefrence (val context: Context) {
    companion object {
        const val USER_PREF = "USER_PREF"
    }

    var sharedPreferences = context.getSharedPreferences(USER_PREF, 0)
    fun setvalues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getvalues(key: String): String? {
        return sharedPreferences.getString(key,"")
    }
}