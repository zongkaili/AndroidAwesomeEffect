package com.kelly.effect.javapoet.test;

import com.kelly.modular.annotation.model.RouterBean;
import com.kelly.modular.api.core.ARouterLoadPath;
import com.kelly.modular.order.Order_MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
public class ARouter$$Path$$order implements ARouterLoadPath {
    @Override
    public Map<String, RouterBean> loadPath() {
        Map<String, RouterBean> pathMap = new HashMap<>();
        pathMap.put("/order/Order_MainActivity", RouterBean.create(
                RouterBean.Type.ACTIVITY,
                Order_MainActivity.class,
                "order",
                "/order/Order_MainActivity"));
        return pathMap;
    }
}
