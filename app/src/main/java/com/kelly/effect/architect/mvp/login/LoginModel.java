package com.kelly.effect.architect.mvp.login;

import com.kelly.effect.architect.mvp.base.BaseModel;
import com.kelly.effect.architect.mvp.bean.UserInfo;

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
public class LoginModel extends BaseModel<LoginPresenter, LoginContract.Model> {


    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return new LoginContract.Model() {
            @Override
            public void executeLogin(String name, String pwd) throws Exception {
                //todo 只是做一个简单的验证，模拟登录操作
                if ("kelly".equals(name) && "111".equals(pwd)) {
                    //模拟登录成功
                    p.getContract().responseResult(new UserInfo("kelly", "阿里巴巴"));
                } else {
                    //模拟登录失败
                    p.getContract().responseResult(null);
                }
            }
        };
    }
}
