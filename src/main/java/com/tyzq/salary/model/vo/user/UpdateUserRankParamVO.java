package com.tyzq.salary.model.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-23 11:36
 * @Description: //TODO 给员工转正或离职参数VO
 **/
public class UpdateUserRankParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户ID*/
    private Long id;
    /**
     * 员工在职类型：0--试用期，1--正式，2--离职
     */
    private Integer userRankType;
    /**
     * 薪资发放比例
     */
    private BigDecimal salaryGrantRatio;
    /**
     * 实际转正日期
     */
    private Date realityChangeFormalDate;
    /**
     * 离职日期
     */
    private Date userLeaveDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserRankType() {
        return userRankType;
    }

    public void setUserRankType(Integer userRankType) {
        this.userRankType = userRankType;
    }

    public BigDecimal getSalaryGrantRatio() {
        return salaryGrantRatio;
    }

    public void setSalaryGrantRatio(BigDecimal salaryGrantRatio) {
        this.salaryGrantRatio = salaryGrantRatio;
    }

    public Date getRealityChangeFormalDate() {
        return realityChangeFormalDate;
    }

    public void setRealityChangeFormalDate(Date realityChangeFormalDate) {
        this.realityChangeFormalDate = realityChangeFormalDate;
    }

    public Date getUserLeaveDate() {
        return userLeaveDate;
    }

    public void setUserLeaveDate(Date userLeaveDate) {
        this.userLeaveDate = userLeaveDate;
    }
}
