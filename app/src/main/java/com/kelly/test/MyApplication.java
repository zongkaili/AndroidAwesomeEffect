package com.kelly.test;

import android.app.Application;
import com.kelly.test.neteasy.musiclist.ui.UIUtils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.getInstance(this);
    }
}
