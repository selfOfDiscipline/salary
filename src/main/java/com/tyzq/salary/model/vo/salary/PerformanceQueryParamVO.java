package com.tyzq.salary.model.vo.salary;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 11:43 2021/3/5
 * @Description: //TODO 绩效核算查询列表
 **/
public class PerformanceQueryParamVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户名称*/
    private String userName;

    /* 用户所属薪资核算部门ID*/
    private Long userSalaryDeptId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserSalaryDeptId() {
        return userSalaryDeptId;
    }

    public void setUserSalaryDeptId(Long userSalaryDeptId) {
        this.userSalaryDeptId = userSalaryDeptId;
    }
}
