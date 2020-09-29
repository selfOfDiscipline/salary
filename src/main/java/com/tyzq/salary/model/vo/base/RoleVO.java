package com.tyzq.salary.model.vo.base;

import com.tyzq.salary.model.Role;

import java.io.Serializable;
import java.util.List;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 14:02
 * @Description: //TODO 用户角色VO
 **/
public class RoleVO extends Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 该角色是否被选中，true代表选中，false代表未选中*/
    private boolean checkFlag = false;

    /* 角色子类集合*/
    private List<RoleVO> childList;

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public List<RoleVO> getChildList() {
        return childList;
    }

    public void setChildList(List<RoleVO> childList) {
        this.childList = childList;
    }
}
