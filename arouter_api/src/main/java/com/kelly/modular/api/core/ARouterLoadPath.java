package com.kelly.modular.api.core;

import com.kelly.modular.annotation.model.RouterBean;

import java.util.Map;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc: 路由组Group对应的详细Path加载数据接口
 * 比如：app分组对应有哪些类需要加载
 */
public interface ARouterLoadPath {

    /**
     * 加载路由组Group中的Path详细数据
     * 比如："app"分组下有详细信息
     * @return key: "/app/MainActivity", value: MainActivity信息封装到RouterBean对象中
     */
    Map<String, RouterBean> loadPath();
}
