package com.kelly.effect.architect.mvp.bean

import com.kelly.effect.architect.mvp.base.BaseEntity

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
class UserInfo(var name: String, var company: String) : BaseEntity() {
    override fun toString(): String {
        return "UserInfo(name='$name', company='$company')"
    }
}