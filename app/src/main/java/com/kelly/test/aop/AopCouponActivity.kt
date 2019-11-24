package com.kelly.test.aop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelly.test.R
import kotlinx.android.synthetic.main.activity_aop_test.*

/**
 * author: zongkaili
 * data: 2019-11-23
 * desc:
 */
class AopCouponActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop_test)
        text.text = "我的优惠券界面"
    }
}