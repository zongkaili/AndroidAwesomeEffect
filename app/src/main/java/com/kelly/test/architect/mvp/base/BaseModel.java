package com.kelly.test.architect.mvp.base;

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
public abstract class BaseModel<P extends BasePresenter, CONTRACT> {
    protected P p;

    public BaseModel(P p) {
        this.p = p;
    }

    public abstract CONTRACT getContract();
}
