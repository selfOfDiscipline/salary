package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 09:29
 * @Description: //TODO 角色权限新增VO
 **/
public class RoleMenuSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 所选中的角色ID*/
    private Long roleId;

    /* 所选中的权限ids字符串，多个用英文逗号分隔*/
    private String menuIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
}
