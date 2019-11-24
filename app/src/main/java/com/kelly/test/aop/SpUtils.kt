package com.kelly.test.aop

import android.content.Context

/**
 * author: zongkaili
 * data: 2019-11-23
 * desc:
 */
class SpUtils {
    companion object {
        const val SP_NAME = "SHARE_AOP"
        fun putBoolean(context: Context, key: String, value: Boolean) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun getBoolean(context: Context, key: String): Boolean {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            return sp.getBoolean(key, false)
        }
    }
}