package com.kelly.test.architect.mvp.login

import com.kelly.test.architect.mvp.base.BaseEntity
import java.lang.Exception

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
class LoginContract {

    interface Model {
        @Throws(Exception::class)
        fun executeLogin(name: String?, pwd: String?)
    }

    interface View<T : BaseEntity> {
        fun handleResult(t: T?)
    }

    interface Presenter<T: BaseEntity> {
        fun requestLogin(name: String?, pwd: String?)
        fun responseResult(t: T?)
    }

}