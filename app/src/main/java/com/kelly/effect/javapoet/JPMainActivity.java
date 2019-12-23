package com.kelly.effect.javapoet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kelly.effect.R;
import com.kelly.modular.annotation.ARouter;
import com.kelly.modular.annotation.Parameter;
import com.kelly.modular.annotation.model.RouterBean;
import com.kelly.modular.api.core.ARouterLoadGroup;
import com.kelly.modular.api.core.ARouterLoadPath;
import com.kelly.modular.apt.ARouter$$Group$$order;
import com.kelly.modular.apt.ARouter$$Group$$personal;

import java.util.Map;

@ARouter(path = "/app/JPMainActivity")
public class JPMainActivity extends AppCompatActivity {
    private static final String TAG = JPMainActivity.class.getSimpleName();

    @Parameter
    String name;
    @Parameter(name = "agex")
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpmain);
    }

    public void jumpOrder(View view) {
        ARouterLoadGroup loadGroup = new ARouter$$Group$$order();
        Map<String, Class<? extends ARouterLoadPath>> groupMap = loadGroup.loadGroup();
        Class<? extends ARouterLoadPath> clazz = groupMap.get("order");
        if (clazz == null) {
            Log.e(TAG, " jumpOrder >>> clazz is null.");
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

    public void jumpPersonal(View view) {
        ARouterLoadGroup loadGroup = new ARouter$$Group$$personal();
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
