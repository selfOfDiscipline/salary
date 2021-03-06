package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-24 16:35
 * @Description: //TODO 核算人员部门关联表查询参数VO
 **/
public class UserSalaryDeptQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 核算人员名称*/
    private String userName;

    /* 薪资归属部门Id*/
    private Long salaryDeptId;

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
}
