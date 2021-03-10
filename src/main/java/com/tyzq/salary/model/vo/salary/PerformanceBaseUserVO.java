package com.tyzq.salary.model.vo.salary;


import java.io.Serializable;
import java.math.BigDecimal;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 9:30 2021/3/5
 * @Description: //TODO 一键生成绩效基础工资需要的  用户信息结果VO
 **/
public class PerformanceBaseUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户角色
     */
    private String userRoleName;
    /**
     * 薪资归属部门名称
     */
    private String salaryDeptName;
    /**
     * 员工标准薪资
     */
    private BigDecimal standardSalary;
    /**
     * 员工绩效系数
     */
    private BigDecimal performanceRatio;
    /**
     * 薪资归属部门id
     */
    private Long salaryDeptId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }

    public BigDecimal getStandardSalary() {
        return standardSalary;
    }

    public void setStandardSalary(BigDecimal standardSalary) {
        this.standardSalary = standardSalary;
    }

    public BigDecimal getPerformanceRatio() {
        return performanceRatio;
    }

    public void setPerformanceRatio(BigDecimal performanceRatio) {
        this.performanceRatio = performanceRatio;
    }

    public Long getSalaryDeptId() {
        return salaryDeptId;
    }

    public void setSalaryDeptId(Long salaryDeptId) {
        this.salaryDeptId = salaryDeptId;
    }
}
