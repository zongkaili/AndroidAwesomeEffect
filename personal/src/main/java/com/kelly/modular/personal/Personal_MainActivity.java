package com.kelly.modular.personal;

import android.os.Bundle;
import android.view.View;

import com.kelly.common.base.BaseActivity;
import com.kelly.modular.annotation.ARouter;

@ARouter(path = "/personal/Personal_MainActivity")
public class Personal_MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_main);
    }

    public void jumpApp(View view) {
    }

    public void jumpPersonal(View view) {
    }
}
