package com.tyzq.salary.model.vo.costQuery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:18 2021/3/1
 * @Description: //TODO 成本列表最外层对象VO
 **/
public class CostResultDeptVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 该条数据id*/
    private Integer id;

    /* 业务归属部门id*/
    private Long deptId;

    /* 业务归属部门名称*/
    private String deptName;

    /* 部门总收入金额*/
    private BigDecimal totalEarningMoney;

    /* 部门总成本金额*/
    private BigDecimal totalCostMoney;

    /* 部门毛利金额*/
    private BigDecimal costProfit;

    /* 部门毛利率*/
    private BigDecimal costRatio;

    /* 本月营收还是亏损*/
    private String monthType;

    /* 项目对象集合*/
    private List<CostResultProjectVO> projectVOList;

    public List<CostResultProjectVO> getProjectVOList() {
        return projectVOList;
    }

    public void setProjectVOList(List<CostResultProjectVO> projectVOList) {
        this.projectVOList = projectVOList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getTotalEarningMoney() {
        return totalEarningMoney;
    }

    public void setTotalEarningMoney(BigDecimal totalEarningMoney) {
        this.totalEarningMoney = totalEarningMoney;
    }

    public BigDecimal getTotalCostMoney() {
        return totalCostMoney;
    }

    public void setTotalCostMoney(BigDecimal totalCostMoney) {
        this.totalCostMoney = totalCostMoney;
    }

    public BigDecimal getCostProfit() {
        return costProfit;
    }

    public void setCostProfit(BigDecimal costProfit) {
        this.costProfit = costProfit;
    }

    public BigDecimal getCostRatio() {
        return costRatio;
    }

    public void setCostRatio(BigDecimal costRatio) {
        this.costRatio = costRatio;
    }

    public String getMonthType() {
        return monthType;
    }

    public void setMonthType(String monthType) {
        this.monthType = monthType;
    }
}
