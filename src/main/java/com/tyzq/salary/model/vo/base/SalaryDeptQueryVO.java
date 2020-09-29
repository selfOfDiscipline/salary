package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 16:42
 * @Description: //TODO 薪资核算部门查询VO
 **/
public class SalaryDeptQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 薪资核算部门名称*/
    private String salaryDeptName;

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }
}
