package com.tyzq.salary.model.vo.user;

import com.tyzq.salary.model.UserDetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-21 15:37
 * @Description: //TODO 用户详情表VO
 **/
public class UserDetailVO extends UserDetail implements Serializable {

    private static final long serialVersinUID = 1L;

    /* 用户名*/
    private String userName;

    /* 薪资归属部门id*/
    private Long salaryDeptId;

    /* 薪资归属部门名称*/
    private String salaryDeptName;

    /* 用户角色名称拼接*/
    private String roleName;

    /* 户口类型：0--城镇，1--农村*/
    private Integer householdType;

    /* 用户的社保开始缴纳日期*/
    private Date socialSecurityStartDate;

    /* 员工标准出勤天数*/
    private BigDecimal standardAttendanceDays;

    public BigDecimal getStandardAttendanceDays() {
        return standardAttendanceDays;
    }

    public void setStandardAttendanceDays(BigDecimal standardAttendanceDays) {
        this.standardAttendanceDays = standardAttendanceDays;
    }

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

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(Integer householdType) {
        this.householdType = householdType;
    }

    public Date getSocialSecurityStartDate() {
        return socialSecurityStartDate;
    }

    public void setSocialSecurityStartDate(Date socialSecurityStartDate) {
        this.socialSecurityStartDate = socialSecurityStartDate;
    }
}
