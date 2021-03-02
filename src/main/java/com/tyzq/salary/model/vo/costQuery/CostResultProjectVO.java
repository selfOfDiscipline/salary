package com.tyzq.salary.model.vo.costQuery;

import com.tyzq.salary.model.ProjectCost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 15:38 2021/3/1
 * @Description: //TODO 成本列表 项目级别数据VO
 **/
public class CostResultProjectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 数据id*/
    private Long id;

    /* 业务归属部门id*/
    private Long deptId;

    /* 业务归属部门名称*/
    private String deptName;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 客户公司id
     */
    private Long companyId;

    /**
     * 客户公司名称
     */
    private String companyName;

    /**
     * 项目类型：0--外包，1--总包，2--自研，3--委托制
     */
    private Integer projectType;

    /**
     * 项目状态：0--正常，1--完结，2--废弃
     */
    private Integer projectStatus;

    /**
     * 项目总金额
     */
    private BigDecimal totalMoney;

    /**
     * 委托方金额
     */
    private BigDecimal clientMoney;

    /**
     * 项目完成总进度
     */
    private BigDecimal projectFinishRatio;

    /* 项目总收入金额*/
    private BigDecimal projectTotalEarningMoney;

    /* 项目总成本金额*/
    private BigDecimal projectTotalCostMoney;

    /* 项目毛利金额*/
    private BigDecimal projectCostProfit;

    /* 项目毛利率*/
    private BigDecimal projectCostRatio;

    /* 项目营收还是亏损*/
    private String projectMonthType;

    /* 项目成本明细*/
    private List<ProjectCost> projectCostList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getClientMoney() {
        return clientMoney;
    }

    public void setClientMoney(BigDecimal clientMoney) {
        this.clientMoney = clientMoney;
    }

    public BigDecimal getProjectFinishRatio() {
        return projectFinishRatio;
    }

    public void setProjectFinishRatio(BigDecimal projectFinishRatio) {
        this.projectFinishRatio = projectFinishRatio;
    }

    public BigDecimal getProjectTotalEarningMoney() {
        return projectTotalEarningMoney;
    }

    public void setProjectTotalEarningMoney(BigDecimal projectTotalEarningMoney) {
        this.projectTotalEarningMoney = projectTotalEarningMoney;
    }

    public BigDecimal getProjectTotalCostMoney() {
        return projectTotalCostMoney;
    }

    public void setProjectTotalCostMoney(BigDecimal projectTotalCostMoney) {
        this.projectTotalCostMoney = projectTotalCostMoney;
    }

    public BigDecimal getProjectCostProfit() {
        return projectCostProfit;
    }

    public void setProjectCostProfit(BigDecimal projectCostProfit) {
        this.projectCostProfit = projectCostProfit;
    }

    public BigDecimal getProjectCostRatio() {
        return projectCostRatio;
    }

    public void setProjectCostRatio(BigDecimal projectCostRatio) {
        this.projectCostRatio = projectCostRatio;
    }

    public String getProjectMonthType() {
        return projectMonthType;
    }

    public void setProjectMonthType(String projectMonthType) {
        this.projectMonthType = projectMonthType;
    }

    public List<ProjectCost> getProjectCostList() {
        return projectCostList;
    }

    public void setProjectCostList(List<ProjectCost> projectCostList) {
        this.projectCostList = projectCostList;
    }
}
