package com.kelly.modular.order;

import android.os.Bundle;
import android.view.View;

import com.kelly.common.base.BaseActivity;
import com.kelly.modular.annotation.ARouter;

@ARouter(path = "/order/Order_Main2Activity")
public class Order_Main2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);
    }

    public void jumpApp(View view) {
    }

    public void jumpPersonal(View view) {
    }
}