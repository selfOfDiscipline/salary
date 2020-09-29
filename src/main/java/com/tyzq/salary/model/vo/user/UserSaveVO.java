package com.tyzq.salary.model.vo.user;

import com.tyzq.salary.model.User;
import com.tyzq.salary.model.UserDetail;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-09 14:32
 * @Description: //TODO 员工新增修改接口入参VO
 **/
public class UserSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户实体*/
    private User user;

    /* 用户明细实体*/
    private UserDetail userDetail;

    /* 所选角色ids字符串，多个角色id用英文逗号分隔*/
    private String roleIds;

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
}
