package com.kelly.modular.personal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kelly.common.base.BaseActivity;
import com.kelly.modular.annotation.ARouter;
import com.kelly.modular.annotation.model.RouterBean;
import com.kelly.modular.api.core.ARouterLoadGroup;
import com.kelly.modular.api.core.ARouterLoadPath;
import com.kelly.modular.apt.ARouter$$Group$$personal;

import java.util.Map;

@ARouter(path = "/personal/Personal_MainActivity")
public class Personal_MainActivity extends BaseActivity {

    private static final String TAG = Personal_MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_main);
    }

    public void jumpApp(View view) {
        ARouterLoadGroup loadGroup = new ARouter$$Group$$personal();
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

    public void jumpOrder(View view) {
        ARouterLoadGroup loadGroup = new ARouter$$Group$$personal();
        Map<String, Class<? extends ARouterLoadPath>> groupMap = loadGroup.loadGroup();
        Class<? extends ARouterLoadPath> clazz = groupMap.get("order");
        if (clazz == null) {
            Log.e(TAG, " jumpPersonal >>> clazz is null.");
            return;
        }
        try {
            ARouterLoadPath path = clazz.newInstance();
            Map<String, RouterBean> pathMap = path.loadPath();
            RouterBean routerBean = pathMap.get("/order/Order_MainActivity");
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
