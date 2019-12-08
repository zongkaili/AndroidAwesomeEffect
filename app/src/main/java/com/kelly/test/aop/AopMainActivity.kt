//package com.kelly.test.aop
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import com.kelly.test.R
//import com.kelly.test.aop.annotation.ClickBehavior
//import com.kelly.test.aop.annotation.LoginCheck
//import kotlinx.android.synthetic.main.activity_aop_main.*
//
///**
// * author: zongkaili
// * data: 2019-11-23
// * desc:
// */
//class AopMainActivity: AppCompatActivity(), View.OnClickListener {
//    companion object {
//        const val TAG = "Aop-Main"
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_aop_main)
//        btnLogin.setOnClickListener(this)
//        btnCoupon.setOnClickListener(this)
//        SpUtils.putBoolean(this, "isLogin", false)
//    }
//
//    override fun onClick(v: View?) {
//        v?.apply {
//            when (id) {
//                R.id.btnLogin->login()
//                R.id.btnCoupon->coupon()
//            }
//        }
//
//    }
//
//    @ClickBehavior("登录操作")
//    private fun login() {
//        Log.d(TAG, "点击登录 >>> ")
//        SpUtils.putBoolean(this, "isLogin", true)
//        startActivity(Intent(this, AopLoginActivity::class.java))
//    }
//
//    @ClickBehavior("点击我的优惠券操作")
//    @LoginCheck
//    private fun coupon() {
//        Log.d(TAG, " 点击我的优惠券 >>> ")
//        startActivity(Intent(this, AopCouponActivity::class.java))
//    }
//
//}