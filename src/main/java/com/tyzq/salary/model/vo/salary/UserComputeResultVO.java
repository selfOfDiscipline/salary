package com.tyzq.salary.model.vo.salary;

import com.tyzq.salary.model.UserSalary;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-22 10:26
 * @Description: //TODO 员工计薪列表VO
 **/
public class UserComputeResultVO extends UserSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户名称*/
    private String userName;
    /**
     * 入职日期
     */
    private Date userEntryDate;
    /**
     * 实际转正日期
     */
    private Date realityChangeFormalDate;
    /**
     * 户口类型：0--城镇，1--农村
     */
    private Integer householdType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserEntryDate() {
        return userEntryDate;
    }

    public void setUserEntryDate(Date userEntryDate) {
        this.userEntryDate = userEntryDate;
    }

    public Date getRealityChangeFormalDate() {
        return realityChangeFormalDate;
    }

    public void setRealityChangeFormalDate(Date realityChangeFormalDate) {
        this.realityChangeFormalDate = realityChangeFormalDate;
    }

    public Integer getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(Integer householdType) {
        this.householdType = householdType;
    }
}
