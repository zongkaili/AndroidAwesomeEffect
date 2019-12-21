package com.kelly.effect.aop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelly.effect.R
import kotlinx.android.synthetic.main.activity_aop_test.*

/**
 * author: zongkaili
 * data: 2019-11-23
 * desc:
 */
class AopLoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop_test)
        text.text = "登录界面"
    }
}