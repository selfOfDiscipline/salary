package com.tyzq.salary.model.vo.salary;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-28 09:58
 * @Description: //TODO 查询历史工资单列表
 **/
public class SalaryHistoryQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 薪资归属日期，默认查上个月*/
    private Date salaryDate;

    /* 薪资归属部门id*/
    private Long salaryDeptId;

    /* 员工岗位类型：0--管理岗，1--成本岗，2--技术岗*/
    private Integer userPostType;

    /* 员工名称*/
    private String userName;

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    public Long getSalaryDeptId() {
        return salaryDeptId;
    }

    public void setSalaryDeptId(Long salaryDeptId) {
        this.salaryDeptId = salaryDeptId;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
