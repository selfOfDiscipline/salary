package com.tyzq.salary.model.vo.user;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-10 14:03
 * @Description: //TODO 用户登录VO
 **/
public class UserLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户账号*/
    private String account;

    /* 用户密码*/
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
