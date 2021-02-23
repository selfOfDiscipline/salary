package com.tyzq.salary.model.vo.cost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 9:55 2021/2/23
 * @Description: //TODO 项目成本返回结果VO，用于生成项目成本表数据
 **/
public class ProjectSalaryResultVO implements Serializable {

    /**
     * OA账号
     */
    private String userAccount;
    /**
     * 员工名称
     */
    private String userName;
    /**
     * 员工业务归属部门id
     */
    private Long userDeptId;
    /**
     * 员工业务归属部门名称
     */
    private String userDeptName;
    /**
     * 项目编号
     */
    private String projectCode;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 员工角色，多个英文逗号分开
     */
    private String userRoleNames;
    /**
     * 员工岗位类型：0--管理岗，1--成本岗，2--技术岗，3--外协
     */
    private Integer userPostType;
    /**
     * 收入类型：0--天结，1--月结
     */
    private Integer earningType;
    /**
     * 收入金额
     */
    private BigDecimal earningMoney;


    /**
     * 项目类型：0--外包，1--总包，2--自研，3--委托制
     */
    private Integer projectType;
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


    /**
     * 员工在职类型：0--试用期，1--正式，2--离职
     */
    private Integer userRankType;
    /**
     * 入职日期
     */
    private Date userEntryDate;
    /**
     * 实际转正日期
     */
    private Date realityChangeFormalDate;


    /**
     * 该薪资所属日期
     */
    private Date salaryDate;
    /**
     * 新入职出勤天数
     */
    private BigDecimal newEntryAttendanceDays;
    /**
     * 转正前应出勤天数
     */
    private BigDecimal positiveBeforeAttendanceDays;
    /**
     * 转成后应出勤天数
     */
    private BigDecimal positiveAfterAttendanceDays;
    /**
     * 转正后其他缺勤天数
     */
    private BigDecimal positiveAfterOtherAttendanceDays;
    /**
     * 转正后病假缺勤天数
     */
    private BigDecimal positiveAfterSickAttendanceDays;
    /**
     * 转正前其他缺勤天数
     */
    private BigDecimal positiveBeforeOtherAttendanceDays;
    /**
     * 转正前病假缺勤天数
     */
    private BigDecimal positiveBeforeSickAttendanceDays;
    /**
     * 其他缺勤天数
     */
    private BigDecimal otherAbsenceDays;
    /**
     * 病假缺勤天数
     */
    private BigDecimal sickAdsenceDays;
    /**
     * 本月费用公司总计
     */
    private BigDecimal monthCompanyPayTotal;
    /**
     * 他行发放部分个税（计算）
     */
    private BigDecimal otherBankShouldTaxMoney;
    /**
     * 本月总工资实发总计（银行代发实发+他行实发）
     */
    private BigDecimal monthSalaryRealityTotal;

    public BigDecimal getEarningMoney() {
        return earningMoney;
    }

    public void setEarningMoney(BigDecimal earningMoney) {
        this.earningMoney = earningMoney;
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

    public String getUserRoleNames() {
        return userRoleNames;
    }

    public void setUserRoleNames(String userRoleNames) {
        this.userRoleNames = userRoleNames;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
    }

    public Integer getEarningType() {
        return earningType;
    }

    public void setEarningType(Integer earningType) {
        this.earningType = earningType;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
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

    public Integer getUserRankType() {
        return userRankType;
    }

    public void setUserRankType(Integer userRankType) {
        this.userRankType = userRankType;
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

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    public BigDecimal getNewEntryAttendanceDays() {
        return newEntryAttendanceDays;
    }

    public void setNewEntryAttendanceDays(BigDecimal newEntryAttendanceDays) {
        this.newEntryAttendanceDays = newEntryAttendanceDays;
    }

    public BigDecimal getPositiveBeforeAttendanceDays() {
        return positiveBeforeAttendanceDays;
    }

    public void setPositiveBeforeAttendanceDays(BigDecimal positiveBeforeAttendanceDays) {
        this.positiveBeforeAttendanceDays = positiveBeforeAttendanceDays;
    }

    public BigDecimal getPositiveAfterAttendanceDays() {
        return positiveAfterAttendanceDays;
    }

    public void setPositiveAfterAttendanceDays(BigDecimal positiveAfterAttendanceDays) {
        this.positiveAfterAttendanceDays = positiveAfterAttendanceDays;
    }

    public BigDecimal getPositiveAfterOtherAttendanceDays() {
        return positiveAfterOtherAttendanceDays;
    }

    public void setPositiveAfterOtherAttendanceDays(BigDecimal positiveAfterOtherAttendanceDays) {
        this.positiveAfterOtherAttendanceDays = positiveAfterOtherAttendanceDays;
    }

    public BigDecimal getPositiveAfterSickAttendanceDays() {
        return positiveAfterSickAttendanceDays;
    }

    public void setPositiveAfterSickAttendanceDays(BigDecimal positiveAfterSickAttendanceDays) {
        this.positiveAfterSickAttendanceDays = positiveAfterSickAttendanceDays;
    }

    public BigDecimal getPositiveBeforeOtherAttendanceDays() {
        return positiveBeforeOtherAttendanceDays;
    }

    public void setPositiveBeforeOtherAttendanceDays(BigDecimal positiveBeforeOtherAttendanceDays) {
        this.positiveBeforeOtherAttendanceDays = positiveBeforeOtherAttendanceDays;
    }

    public BigDecimal getPositiveBeforeSickAttendanceDays() {
        return positiveBeforeSickAttendanceDays;
    }

    public void setPositiveBeforeSickAttendanceDays(BigDecimal positiveBeforeSickAttendanceDays) {
        this.positiveBeforeSickAttendanceDays = positiveBeforeSickAttendanceDays;
    }

    public BigDecimal getOtherAbsenceDays() {
        return otherAbsenceDays;
    }

    public void setOtherAbsenceDays(BigDecimal otherAbsenceDays) {
        this.otherAbsenceDays = otherAbsenceDays;
    }

    public BigDecimal getSickAdsenceDays() {
        return sickAdsenceDays;
    }

    public void setSickAdsenceDays(BigDecimal sickAdsenceDays) {
        this.sickAdsenceDays = sickAdsenceDays;
    }

    public BigDecimal getMonthCompanyPayTotal() {
        return monthCompanyPayTotal;
    }

    public void setMonthCompanyPayTotal(BigDecimal monthCompanyPayTotal) {
        this.monthCompanyPayTotal = monthCompanyPayTotal;
    }

    public BigDecimal getOtherBankShouldTaxMoney() {
        return otherBankShouldTaxMoney;
    }

    public void setOtherBankShouldTaxMoney(BigDecimal otherBankShouldTaxMoney) {
        this.otherBankShouldTaxMoney = otherBankShouldTaxMoney;
    }

    public BigDecimal getMonthSalaryRealityTotal() {
        return monthSalaryRealityTotal;
    }

    public void setMonthSalaryRealityTotal(BigDecimal monthSalaryRealityTotal) {
        this.monthSalaryRealityTotal = monthSalaryRealityTotal;
    }
}
