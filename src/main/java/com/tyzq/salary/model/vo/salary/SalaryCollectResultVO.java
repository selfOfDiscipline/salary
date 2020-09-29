package com.tyzq.salary.model.vo.salary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-28 14:29
 * @Description: //TODO 汇总数据对象VO
 **/
public class SalaryCollectResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 序号*/
    private Integer thisNumber;

    /* 薪资归属部门id*/
    private Long salaryDeptId;

    /* 薪资归属部门名称*/
    private String salaryDeptName;

    /* 薪资归属日期*/
    private Date salaryDate;

    /* 管理岗汇总*/
    private BigDecimal salaryDeptManageTotal;

    /* 成本岗汇总*/
    private BigDecimal salaryDeptCostTotal;

    /* 技术岗汇总*/
    private BigDecimal salaryDeptSkillTotal;

    /* 部门汇总*/
    private BigDecimal salaryDeptMoneyTotal;

    public Integer getThisNumber() {
        return thisNumber;
    }

    public void setThisNumber(Integer thisNumber) {
        this.thisNumber = thisNumber;
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

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    public BigDecimal getSalaryDeptManageTotal() {
        return salaryDeptManageTotal;
    }

    public void setSalaryDeptManageTotal(BigDecimal salaryDeptManageTotal) {
        this.salaryDeptManageTotal = salaryDeptManageTotal;
    }

    public BigDecimal getSalaryDeptCostTotal() {
        return salaryDeptCostTotal;
    }

    public void setSalaryDeptCostTotal(BigDecimal salaryDeptCostTotal) {
        this.salaryDeptCostTotal = salaryDeptCostTotal;
    }

    public BigDecimal getSalaryDeptSkillTotal() {
        return salaryDeptSkillTotal;
    }

    public void setSalaryDeptSkillTotal(BigDecimal salaryDeptSkillTotal) {
        this.salaryDeptSkillTotal = salaryDeptSkillTotal;
    }

    public BigDecimal getSalaryDeptMoneyTotal() {
        return salaryDeptMoneyTotal;
    }

    public void setSalaryDeptMoneyTotal(BigDecimal salaryDeptMoneyTotal) {
        this.salaryDeptMoneyTotal = salaryDeptMoneyTotal;
    }
}
