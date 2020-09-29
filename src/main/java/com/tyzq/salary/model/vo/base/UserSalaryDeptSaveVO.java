package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-24 16:58
 * @Description: //TODO 用户薪资部门新增参数VO
 **/
public class UserSalaryDeptSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户id*/
    private Long userId;

    /* 用户账号*/
    private String userAccount;

    /* 用户名称*/
    private String userName;

    /* 薪资归属部门id*/
    private Long salaryDeptId;

    /* 薪资归属部门名称*/
    private String salaryDeptName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getSalaryDeptId() {
        return salaryDeptId;
    }

    public void setSalaryDeptId(Long salaryDeptId) {
        this.salaryDeptId = salaryDeptId;
    }

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }
}
