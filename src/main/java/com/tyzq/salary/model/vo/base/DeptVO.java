package com.tyzq.salary.model.vo.base;

import com.tyzq.salary.model.Dept;

import java.io.Serializable;
import java.util.List;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-18 14:02
 * @Description: //TODO 用户角色VO
 **/
public class DeptVO extends Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 该部门是否被选中，true代表选中，false代表未选中*/
    private boolean checkFlag = false;

    /* 部门子类集合*/
    private List<DeptVO> childList;

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public List<DeptVO> getChildList() {
        return childList;
    }

    public void setChildList(List<DeptVO> childList) {
        this.childList = childList;
    }
}
