package com.tyzq.salary.model.vo.cost;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:53 2021/2/2
 * @Description: //TODO 条查 公司所有员工基础信息
 **/
public class UserCostQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户名称*/
    private String userName;

    /* 用户业务归属部门id*/
    private Long userDeptId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }
}
