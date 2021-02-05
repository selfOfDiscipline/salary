package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 项目成本表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2021-02-05
 */
public class ProjectCost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * OA账号
     */
    private String userAccount;
    /**
     * 员工名称
     */
    private String userName;
    /**
     * 成本归属部门id
     */
    private Long userDeptId;
    /**
     * 成本归属部门名称
     */
    private String userDeptName;
    /**
     * 员工角色，多个英文逗号分开
     */
    private String userRoleNames;
    /**
     * 项目编号
     */
    private String projectCode;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目类型：0--外包，1--总包，2--自研，3--委托制
     */
    private Integer projectType;
    /**
     * 员工岗位类型：0--管理岗，1--成本岗，2--技术岗，3--外协
     */
    private Integer userPostType;
    /**
     * 成本归属日期
     */
    private Date salaryDate;
    /**
     * 收入类型：0--天结，1--月结
     */
    private Integer earningType;
    /**
     * 出勤天数
     */
    private BigDecimal attendanceDays;
    /**
     * 本月奖惩金额（可为正负）
     */
    private BigDecimal monthRewordsMoney;
    /**
     * 总收入金额
     */
    private BigDecimal totalEarningMoney;
    /**
     * 总成本金额
     */
    private BigDecimal totalCostMoney;
    /**
     * 工资成本
     */
    private BigDecimal costSalary;
    /**
     * 社保成本
     */
    private BigDecimal costSocialSecurity;
    /**
     * 税务成本
     */
    private BigDecimal costTax;
    /**
     * 报销成本
     */
    private BigDecimal costReimburse;
    /**
     * 毛利金额
     */
    private BigDecimal costProfit;
    /**
     * 毛利率
     */
    private BigDecimal costRatio;
    /**
     * 项目本月完成进度
     */
    private BigDecimal projectMonthFinishRatio;
    /**
     * 项目完成总进度
     */
    private BigDecimal projectTotalFinishRatio;
    /**
     * 冗余字段：用于前端做增减逻辑
     */
    private String redundancyCode;
    /**
     * 是否删除：0--正常，1--已删除
     */
    private Integer deleteFlag;
    /**
     * 创建人账号
     */
    private String createId;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人账号
     */
    private String editId;
    /**
     * 修改人名称
     */
    private String editName;
    /**
     * 修改时间
     */
    private Date editTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getUserRoleNames() {
        return userRoleNames;
    }

    public void setUserRoleNames(String userRoleNames) {
        this.userRoleNames = userRoleNames;
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

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
    }

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    public Integer getEarningType() {
        return earningType;
    }

    public void setEarningType(Integer earningType) {
        this.earningType = earningType;
    }

    public BigDecimal getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(BigDecimal attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public BigDecimal getMonthRewordsMoney() {
        return monthRewordsMoney;
    }

    public void setMonthRewordsMoney(BigDecimal monthRewordsMoney) {
        this.monthRewordsMoney = monthRewordsMoney;
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

    public BigDecimal getCostSalary() {
        return costSalary;
    }

    public void setCostSalary(BigDecimal costSalary) {
        this.costSalary = costSalary;
    }

    public BigDecimal getCostSocialSecurity() {
        return costSocialSecurity;
    }

    public void setCostSocialSecurity(BigDecimal costSocialSecurity) {
        this.costSocialSecurity = costSocialSecurity;
    }

    public BigDecimal getCostTax() {
        return costTax;
    }

    public void setCostTax(BigDecimal costTax) {
        this.costTax = costTax;
    }

    public BigDecimal getCostReimburse() {
        return costReimburse;
    }

    public void setCostReimburse(BigDecimal costReimburse) {
        this.costReimburse = costReimburse;
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

    public BigDecimal getProjectMonthFinishRatio() {
        return projectMonthFinishRatio;
    }

    public void setProjectMonthFinishRatio(BigDecimal projectMonthFinishRatio) {
        this.projectMonthFinishRatio = projectMonthFinishRatio;
    }

    public BigDecimal getProjectTotalFinishRatio() {
        return projectTotalFinishRatio;
    }

    public void setProjectTotalFinishRatio(BigDecimal projectTotalFinishRatio) {
        this.projectTotalFinishRatio = projectTotalFinishRatio;
    }

    public String getRedundancyCode() {
        return redundancyCode;
    }

    public void setRedundancyCode(String redundancyCode) {
        this.redundancyCode = redundancyCode;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    @Override
    public String toString() {
        return "ProjectCost{" +
        "id=" + id +
        ", userAccount=" + userAccount +
        ", userName=" + userName +
        ", userDeptId=" + userDeptId +
        ", userDeptName=" + userDeptName +
        ", userRoleNames=" + userRoleNames +
        ", projectCode=" + projectCode +
        ", projectName=" + projectName +
        ", projectType=" + projectType +
        ", userPostType=" + userPostType +
        ", salaryDate=" + salaryDate +
        ", earningType=" + earningType +
        ", attendanceDays=" + attendanceDays +
        ", monthRewordsMoney=" + monthRewordsMoney +
        ", totalEarningMoney=" + totalEarningMoney +
        ", totalCostMoney=" + totalCostMoney +
        ", costSalary=" + costSalary +
        ", costSocialSecurity=" + costSocialSecurity +
        ", costTax=" + costTax +
        ", costReimburse=" + costReimburse +
        ", costProfit=" + costProfit +
        ", costRatio=" + costRatio +
        ", projectMonthFinishRatio=" + projectMonthFinishRatio +
        ", projectTotalFinishRatio=" + projectTotalFinishRatio +
        ", redundancyCode=" + redundancyCode +
        ", deleteFlag=" + deleteFlag +
        ", createId=" + createId +
        ", createName=" + createName +
        ", createTime=" + createTime +
        ", editId=" + editId +
        ", editName=" + editName +
        ", editTime=" + editTime +
        "}";
    }
}
