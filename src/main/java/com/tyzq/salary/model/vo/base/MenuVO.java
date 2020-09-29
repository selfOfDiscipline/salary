package com.tyzq.salary.model.vo.base;

import com.tyzq.salary.model.Menu;

import java.io.Serializable;
import java.util.List;

/**
 * @ProJectName: SALARY
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-28 10:52
 * @Description: //TODO 菜单子类
 **/
public class MenuVO extends Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 该菜单是否被选中，true代表选中，false代表未选中*/
    private boolean checkFlag = false;

    /* 菜单子类集合*/
    private List<MenuVO> childList;

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public List<MenuVO> getChildList() {
        return childList;
    }

    public void setChildList(List<MenuVO> childList) {
        this.childList = childList;
    }
}
