package com.kelly.effect.architect.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
public abstract class BaseView<P extends BasePresenter, CONTRACT> extends AppCompatActivity {
    protected P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        p = getPresenter();
        if (p != null) {
            p.bindView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (p != null) {
            p.unBindView();
        }
    }

    public abstract CONTRACT getContract();

    public abstract P getPresenter();

    public void error(Exception e) {}
}
