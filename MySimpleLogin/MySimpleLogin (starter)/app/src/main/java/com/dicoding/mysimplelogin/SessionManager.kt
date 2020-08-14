package com.dicoding.mysimplelogin

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    companion object {
        const val KEY_LOGIN = "isLogin"
        const val KEY_USERNAME = "username"
    }

    private var pref: SharedPreferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    fun createLoginSession() {
        editor.putBoolean(KEY_LOGIN, true)
            .commit()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    val isLogin: Boolean = pref.getBoolean(KEY_LOGIN, false)

    fun saveToPreference(key: String, value: String) = editor.putString(key, value).commit()

    fun getFromPreference(key: String) = pref.getString(key, "")

}
