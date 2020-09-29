package com.tyzq.salary.model.vo.user;

import com.tyzq.salary.model.User;
import com.tyzq.salary.model.UserDetail;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 14:44
 * @Description: //TODO 单个用户详情封装VO
 **/
public class UserOneResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户实体*/
    private User user;

    /* 用户明细实体*/
    private UserDetail userDetail;

    /* 所选角色ids字符串，多个角色id用英文逗号分隔*/
    private String roleIds;

    /* 用户角色名称字符串，多个角色名称用英文逗号分隔*/
    private String roleNames;

    /* 业务归属部门名称*/
    private String deptName;

    /* 薪资归属部门名称*/
    private String salaryDeptName;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }
}
