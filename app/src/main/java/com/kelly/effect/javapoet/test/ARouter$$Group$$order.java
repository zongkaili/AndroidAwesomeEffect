package com.kelly.effect.javapoet.test;

import com.kelly.modular.api.core.ARouterLoadGroup;
import com.kelly.modular.api.core.ARouterLoadPath;

import java.util.HashMap;
import java.util.Map;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
public class ARouter$$Group$$order implements ARouterLoadGroup {
    @Override
    public Map<String, Class<? extends ARouterLoadPath>> loadGroup() {
        Map<String, Class<? extends ARouterLoadPath>> map = new HashMap<>();
        map.put("order", ARouter$$Path$$order.class);
        return map;
    }
}
