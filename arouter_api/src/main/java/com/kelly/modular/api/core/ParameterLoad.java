package com.kelly.modular.api.core;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc: 参数Parameter加载接口
 */
public interface ParameterLoad {
    /**
     * 目标对象.属性名 = target.getIntent().属性类型("注解值or属性名")；
     * @param target 目标对象，如：MainActivity中的某些属性
     */
    void loadParameter(Object target);
}
