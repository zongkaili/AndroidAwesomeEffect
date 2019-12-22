package com.kelly.effect.javapoet;

import android.app.Activity;

import com.kelly.modular.api.core.ParameterLoad;

/**
 * author: zongkaili
 * data: 2019-12-22
 * desc:
 */
public class XActivity$$Parameter implements ParameterLoad {
    @Override
    public void loadParameter(Object target) {
         JPMain2Activity t = (JPMain2Activity) target;
         t.name = t.getIntent().getStringExtra("name");
         t.age = t.getIntent().getIntExtra("age", t.age);
    }
}
