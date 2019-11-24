package com.kelly.test.architect.mvp.login;

import com.kelly.test.architect.mvp.base.BasePresenter;
import com.kelly.test.architect.mvp.bean.UserInfo;

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel, LoginContract.Presenter> {
    @Override
    public LoginContract.Presenter getContract() {
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String pwd) {
                try {
                    m.getContract().executeLogin(name, pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                getView().getContract().handleResult(userInfo);
            }
        };
    }

    @Override
    public LoginModel getModel() {
        return new LoginModel(this);
    }
}
