package com.kelly.test.aop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kelly.test.R;
import com.kelly.test.aop.annotation.ClickBehavior;
import com.kelly.test.aop.annotation.LoginCheck;

/**
 * author: zongkaili
 * data: 2019-12-07
 * desc:
 */
public class AopMainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Aop-Main";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop_main);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnCoupon).setOnClickListener(this);
        SpUtils.Companion.putBoolean(this, "isLogin", false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnCoupon:
                coupon();
                break;
            default:
                break;
        }
    }

    @ClickBehavior("登录操作")
    private void login() {
        Log.d(TAG, "点击登录 >>> 登录成功");
        SpUtils.Companion.putBoolean(this, "isLogin", true);
        startActivity(new Intent(this, AopLoginActivity.class));
    }

    @ClickBehavior("点击我的优惠券操作")
    @LoginCheck
    private void coupon() {
        Log.d(TAG, " 点击我的优惠券 >>> ");
        startActivity(new Intent(this, AopCouponActivity.class));
    }


}
