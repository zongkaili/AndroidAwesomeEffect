package com.kelly.effect;

import android.app.Application;
import com.kelly.effect.neteasy.musiclist.ui.UIUtils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.getInstance(this);
    }
}
