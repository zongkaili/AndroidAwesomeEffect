package com.kelly.modular.order;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kelly.common.base.BaseActivity;
import com.kelly.modular.annotation.ARouter;
import com.kelly.modular.annotation.Parameter;
import com.kelly.modular.annotation.model.RouterBean;
import com.kelly.modular.api.core.ARouterLoadGroup;
import com.kelly.modular.api.core.ARouterLoadPath;
import com.kelly.modular.apt.ARouter$$Group$$order;

import java.util.Map;

@ARouter(path = "/order/Order_MainActivity")
public class Order_MainActivity extends BaseActivity {
    private static final String TAG = Order_MainActivity.class.getSimpleName();

    @Parameter
    String name;
    @Parameter(name = "agex")
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);

        Order_MainActivity$$Parameter parameter = new Order_MainActivity$$Parameter();
        parameter.loadParameter(this);

        if (getIntent() != null) {
            Log.d(TAG, " >>> name == " + name + ", age == " + age);
        }
    }

    public void jumpApp(View view) {
        ARouterLoadGroup loadGroup = new ARouter$$Group$$order();
        Map<String, Class<? extends ARouterLoadPath>> groupMap = loadGroup.loadGroup();
        Class<? extends ARouterLoadPath> clazz = groupMap.get("app");
        if (clazz == null) {
            Log.e(TAG, " jumpApp >>> clazz is null.");
            return;
        }
        try {
            ARouterLoadPath path = clazz.newInstance();
            Map<String, RouterBean> pathMap = path.loadPath();
            RouterBean routerBean = pathMap.get("/app/JPMainActivity");
            if (routerBean != null) {
                Intent intent = new Intent(this, routerBean.getClazz());
                intent.putExtra("name", "Kelly");
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jumpPersonal(View view) {
        ARouterLoadGroup loadGroup = new ARouter$$Group$$order();
        Map<String, Class<? extends ARouterLoadPath>> groupMap = loadGroup.loadGroup();
        Class<? extends ARouterLoadPath> clazz = groupMap.get("personal");
        if (clazz == null) {
            Log.e(TAG, " jumpPersonal >>> clazz is null.");
            return;
        }
        try {
            ARouterLoadPath path = clazz.newInstance();
            Map<String, RouterBean> pathMap = path.loadPath();
            RouterBean routerBean = pathMap.get("/personal/Personal_MainActivity");
            if (routerBean != null) {
                Intent intent = new Intent(this, routerBean.getClazz());
                intent.putExtra("name", "Kelly");
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
