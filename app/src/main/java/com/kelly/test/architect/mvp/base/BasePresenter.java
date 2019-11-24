package com.kelly.test.architect.mvp.base;

import java.lang.ref.WeakReference;

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseModel, CONTRACT> {
    protected M m;
    private WeakReference<V> weakReference;

    public BasePresenter() {
        m = getModel();
    }

    public abstract CONTRACT getContract();
    public abstract M getModel();

    public void bindView(V v) {
        weakReference = new WeakReference<>(v);
    }

    public V getView() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public void unBindView() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
            System.gc();
        }
    }
}
