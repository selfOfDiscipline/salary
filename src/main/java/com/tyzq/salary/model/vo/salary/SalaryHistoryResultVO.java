package com.tyzq.salary.model.vo.salary;

import com.tyzq.salary.model.UserSalary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-28 10:34
 * @Description: //TODO 查询结果VO
 **/
public class SalaryHistoryResultVO extends UserSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String userName;
    /**
     * 用户证件号
     */
    private String userCard;
    /**
     * 用户手机号
     */
    private String userTel;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 入职日期
     */
    private Date userEntryDate;
    /**
     * 实际转正日期
     */
    private Date realityChangeFormalDate;
    /**
     * 离职日期
     */
    private Date userLeaveDate;
    /**
     * 用户业务归属部门id
     */
    private Long userDeptId;
    /**
     * 薪资核算归属部门id
     */
    private Long salaryDeptId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 户口类型：0--城镇，1--农村
     */
    private Integer householdType;
    /**
     * 代发银行卡号
     */
    private String salaryBankCard;
    /**
     * 代发银行开户行
     */
    private String salaryBankOpen;
    /**
     * 代发银行开户行名称
     */
    private String salaryBankOpenName;
    /**
     * 代发银行开户行省份
     */
    private String salaryBankOpenProvince;
    /**
     * 代发银行开户行城市
     */
    private String salaryBankOpenCity;
    /**
     * 他行卡号
     */
    private String otherBankCard;
    /**
     * 他行开户行
     */
    private String otherBankOpen;
    /**
     * 他行开户行名称
     */
    private String otherBankOpenName;
    /**
     * 他行开户行省份
     */
    private String otherBankOpenProvince;
    /**
     * 他行开户行城市
     */
    private String otherBankOpenCity;
    /**
     * 社保卡号
     */
    private String socialSecurityCard;
    /**
     * 社保开始缴纳日期
     */
    private Date socialSecurityStartDate;
    /**
     * 工作城市
     */
    private String workCity;
    /**
     * 员工岗位类型：0--管理岗，1--成本岗，2--技术岗
     */
    private Integer userPostType;
    /**
     * 员工在职类型：0--试用期，1--正式，2--离职
     */
    private Integer userRankType;

    /**
     * 累计收入金额
     */
    private BigDecimal totalIncomeMoney;
    /**
     * 累计应纳税所得额
     */
    private BigDecimal totalTaxableSelfMoney;
    /**
     * 累计已纳税额
     */
    private BigDecimal totalAlreadyTaxableMoney;
    /**
     * 累计减除费用金额
     */
    private BigDecimal totalDeductMoney;
    /**
     * 累计专项扣除金额
     */
    private BigDecimal totalSpecialDeductMoney;
    /**
     * 累计子女教育扣除
     */
    private BigDecimal totalChildEducation;
    /**
     * 累计继续教育扣除
     */
    private BigDecimal totalContinueEducation;
    /**
     * 累计住房贷款利息扣除
     */
    private BigDecimal totalHomeLoanInterest;
    /**
     * 累计住房租金扣除
     */
    private BigDecimal totalHomeRents;
    /**
     * 累计赡养老人扣除
     */
    private BigDecimal totalSupportParents;
    /**
     * 累计其他款项扣除
     */
    private BigDecimal totalOtherDeduct;
    /**
     * 计算后的员工计薪工资=员工标准薪资*薪资发放比例
     */
    private BigDecimal computeStandardSalary;

    /**
     * 专扣--子女教育
     */
    private BigDecimal childEducation;
    /**
     * 专扣--继续教育
     */
    private BigDecimal continueEducation;
    /**
     * 专扣--住房贷款利息
     */
    private BigDecimal homeLoanInterest;
    /**
     * 专扣--住房租金
     */
    private BigDecimal homeRents;
    /**
     * 专扣--赡养老人
     */
    private BigDecimal supportParents;
    /**
     * 专项扣除的总计金额
     */
    private BigDecimal specialDeductTotal;

    /* 序号*/
    private Integer thisNumber;

    /* 入职日期*/
    private String entryDate;

    /* 社保开始缴纳日期*/
    private String socialDate;

    /* 用户在职状态*/
    private String userStatus;

    /* 基本工资*/
    private BigDecimal newBaseMoney;

    /* 绩效工资*/
    private BigDecimal newPerformanceMoney;

    /* 税前工资总额*/
    private BigDecimal taxBeforeTotal;

    /* 税前应发总额*/
    private BigDecimal taxBeforeShouldTotal;

    public BigDecimal getTaxBeforeTotal() {
        return taxBeforeTotal;
    }

    public void setTaxBeforeTotal(BigDecimal taxBeforeTotal) {
        this.taxBeforeTotal = taxBeforeTotal;
    }

    public BigDecimal getTaxBeforeShouldTotal() {
        return taxBeforeShouldTotal;
    }

    public void setTaxBeforeShouldTotal(BigDecimal taxBeforeShouldTotal) {
        this.taxBeforeShouldTotal = taxBeforeShouldTotal;
    }

    public Integer getThisNumber() {
        return thisNumber;
    }

    public void setThisNumber(Integer thisNumber) {
        this.thisNumber = thisNumber;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getSocialDate() {
        return socialDate;
    }

    public void setSocialDate(String socialDate) {
        this.socialDate = socialDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public BigDecimal getNewBaseMoney() {
        return newBaseMoney;
    }

    public void setNewBaseMoney(BigDecimal newBaseMoney) {
        this.newBaseMoney = newBaseMoney;
    }

    public BigDecimal getNewPerformanceMoney() {
        return newPerformanceMoney;
    }

    public void setNewPerformanceMoney(BigDecimal newPerformanceMoney) {
        this.newPerformanceMoney = newPerformanceMoney;
    }

    public BigDecimal getChildEducation() {
        return childEducation;
    }

    public void setChildEducation(BigDecimal childEducation) {
        this.childEducation = childEducation;
    }

    public BigDecimal getContinueEducation() {
        return continueEducation;
    }

    public void setContinueEducation(BigDecimal continueEducation) {
        this.continueEducation = continueEducation;
    }

    public BigDecimal getHomeLoanInterest() {
        return homeLoanInterest;
    }

    public void setHomeLoanInterest(BigDecimal homeLoanInterest) {
        this.homeLoanInterest = homeLoanInterest;
    }

    public BigDecimal getHomeRents() {
        return homeRents;
    }

    public void setHomeRents(BigDecimal homeRents) {
        this.homeRents = homeRents;
    }

    public BigDecimal getSupportParents() {
        return supportParents;
    }

    public void setSupportParents(BigDecimal supportParents) {
        this.supportParents = supportParents;
    }

    public BigDecimal getSpecialDeductTotal() {
        return specialDeductTotal;
    }

    public void setSpecialDeductTotal(BigDecimal specialDeductTotal) {
        this.specialDeductTotal = specialDeductTotal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    @Override
    public String getUserAccount() {
        return userAccount;
    }

    @Override
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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

    public Date getUserLeaveDate() {
        return userLeaveDate;
    }

    public void setUserLeaveDate(Date userLeaveDate) {
        this.userLeaveDate = userLeaveDate;
    }

    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }

    @Override
    public Long getSalaryDeptId() {
        return salaryDeptId;
    }

    @Override
    public void setSalaryDeptId(Long salaryDeptId) {
        this.salaryDeptId = salaryDeptId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(Integer householdType) {
        this.householdType = householdType;
    }

    public String getSalaryBankCard() {
        return salaryBankCard;
    }

    public void setSalaryBankCard(String salaryBankCard) {
        this.salaryBankCard = salaryBankCard;
    }

    public String getSalaryBankOpen() {
        return salaryBankOpen;
    }

    public void setSalaryBankOpen(String salaryBankOpen) {
        this.salaryBankOpen = salaryBankOpen;
    }

    public String getSalaryBankOpenName() {
        return salaryBankOpenName;
    }

    public void setSalaryBankOpenName(String salaryBankOpenName) {
        this.salaryBankOpenName = salaryBankOpenName;
    }

    public String getSalaryBankOpenProvince() {
        return salaryBankOpenProvince;
    }

    public void setSalaryBankOpenProvince(String salaryBankOpenProvince) {
        this.salaryBankOpenProvince = salaryBankOpenProvince;
    }

    public String getSalaryBankOpenCity() {
        return salaryBankOpenCity;
    }

    public void setSalaryBankOpenCity(String salaryBankOpenCity) {
        this.salaryBankOpenCity = salaryBankOpenCity;
    }

    public String getOtherBankCard() {
        return otherBankCard;
    }

    public void setOtherBankCard(String otherBankCard) {
        this.otherBankCard = otherBankCard;
    }

    public String getOtherBankOpen() {
        return otherBankOpen;
    }

    public void setOtherBankOpen(String otherBankOpen) {
        this.otherBankOpen = otherBankOpen;
    }

    public String getOtherBankOpenName() {
        return otherBankOpenName;
    }

    public void setOtherBankOpenName(String otherBankOpenName) {
        this.otherBankOpenName = otherBankOpenName;
    }

    public String getOtherBankOpenProvince() {
        return otherBankOpenProvince;
    }

    public void setOtherBankOpenProvince(String otherBankOpenProvince) {
        this.otherBankOpenProvince = otherBankOpenProvince;
    }

    public String getOtherBankOpenCity() {
        return otherBankOpenCity;
    }

    public void setOtherBankOpenCity(String otherBankOpenCity) {
        this.otherBankOpenCity = otherBankOpenCity;
    }

    public String getSocialSecurityCard() {
        return socialSecurityCard;
    }

    public void setSocialSecurityCard(String socialSecurityCard) {
        this.socialSecurityCard = socialSecurityCard;
    }

    public Date getSocialSecurityStartDate() {
        return socialSecurityStartDate;
    }

    public void setSocialSecurityStartDate(Date socialSecurityStartDate) {
        this.socialSecurityStartDate = socialSecurityStartDate;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
    }

    public Integer getUserRankType() {
        return userRankType;
    }

    public void setUserRankType(Integer userRankType) {
        this.userRankType = userRankType;
    }

    public BigDecimal getTotalIncomeMoney() {
        return totalIncomeMoney;
    }

    public void setTotalIncomeMoney(BigDecimal totalIncomeMoney) {
        this.totalIncomeMoney = totalIncomeMoney;
    }

    public BigDecimal getTotalTaxableSelfMoney() {
        return totalTaxableSelfMoney;
    }

    public void setTotalTaxableSelfMoney(BigDecimal totalTaxableSelfMoney) {
        this.totalTaxableSelfMoney = totalTaxableSelfMoney;
    }

    public BigDecimal getTotalAlreadyTaxableMoney() {
        return totalAlreadyTaxableMoney;
    }

    public void setTotalAlreadyTaxableMoney(BigDecimal totalAlreadyTaxableMoney) {
        this.totalAlreadyTaxableMoney = totalAlreadyTaxableMoney;
    }

    public BigDecimal getTotalDeductMoney() {
        return totalDeductMoney;
    }

    public void setTotalDeductMoney(BigDecimal totalDeductMoney) {
        this.totalDeductMoney = totalDeductMoney;
    }

    public BigDecimal getTotalSpecialDeductMoney() {
        return totalSpecialDeductMoney;
    }

    public void setTotalSpecialDeductMoney(BigDecimal totalSpecialDeductMoney) {
        this.totalSpecialDeductMoney = totalSpecialDeductMoney;
    }

    public BigDecimal getTotalChildEducation() {
        return totalChildEducation;
    }

    public void setTotalChildEducation(BigDecimal totalChildEducation) {
        this.totalChildEducation = totalChildEducation;
    }

    public BigDecimal getTotalContinueEducation() {
        return totalContinueEducation;
    }

    public void setTotalContinueEducation(BigDecimal totalContinueEducation) {
        this.totalContinueEducation = totalContinueEducation;
    }

    public BigDecimal getTotalHomeLoanInterest() {
        return totalHomeLoanInterest;
    }

    public void setTotalHomeLoanInterest(BigDecimal totalHomeLoanInterest) {
        this.totalHomeLoanInterest = totalHomeLoanInterest;
    }

    public BigDecimal getTotalHomeRents() {
        return totalHomeRents;
    }

    public void setTotalHomeRents(BigDecimal totalHomeRents) {
        this.totalHomeRents = totalHomeRents;
    }

    public BigDecimal getTotalSupportParents() {
        return totalSupportParents;
    }

    public void setTotalSupportParents(BigDecimal totalSupportParents) {
        this.totalSupportParents = totalSupportParents;
    }

    public BigDecimal getTotalOtherDeduct() {
        return totalOtherDeduct;
    }

    public void setTotalOtherDeduct(BigDecimal totalOtherDeduct) {
        this.totalOtherDeduct = totalOtherDeduct;
    }

    public BigDecimal getComputeStandardSalary() {
        return computeStandardSalary;
    }

    public void setComputeStandardSalary(BigDecimal computeStandardSalary) {
        this.computeStandardSalary = computeStandardSalary;
    }
}
