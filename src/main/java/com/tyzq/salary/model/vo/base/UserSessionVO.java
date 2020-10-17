package com.tyzq.salary.model.vo.base;

import java.io.Serializable;
import java.util.List;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-08 14:27
 * @Description: //TODO 用户session实体
 **/
public class UserSessionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户id*/
    private Long id;

    /* 用户账号*/
    private String userAccount;

    /* 用户名称*/
    private String userName;

    /* 用户拥有的角色ID集合*/
    private List<Long> roleIdList;

    /* 用户拥有的角色名称集合*/
    private List<String> roleNameList;

    /* 用户登录token*/
    private String tokenKey;

    /* 是否可汇总流程标识  allowCollectFlag == true允许汇总；allowCollectFlag == false不允许汇总，默认为false*/
    private boolean allowCollectFlag = false;

    /* 用户的最后登录时间（也就是token的最后赋值时间）*/
    private String lastLoginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public boolean isAllowCollectFlag() {
        return allowCollectFlag;
    }

    public void setAllowCollectFlag(boolean allowCollectFlag) {
        this.allowCollectFlag = allowCollectFlag;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
