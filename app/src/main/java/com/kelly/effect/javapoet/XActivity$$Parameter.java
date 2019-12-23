package com.kelly.effect.javapoet;

import com.kelly.modular.api.core.ParameterLoad;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
public class XActivity$$Parameter implements ParameterLoad {
    @Override
    public void loadParameter(Object target) {
         JPMainActivity t = (JPMainActivity) target;
         t.name = t.getIntent().getStringExtra("name");
         t.age = t.getIntent().getIntExtra("age", t.age);
    }
}
