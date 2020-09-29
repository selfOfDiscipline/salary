package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 14:44
 * @Description: //TODO 用户角色新增VO
 **/
public class UserRoleSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 所选中的用户ID*/
    private Long userId;

    /* 所选中的角色ids字符串，多个用英文逗号分隔*/
    private String roleIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
