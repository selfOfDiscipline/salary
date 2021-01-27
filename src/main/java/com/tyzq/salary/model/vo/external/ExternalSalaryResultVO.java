package com.tyzq.salary.model.vo.external;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExternalSalaryResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 姓名*/
    private String userName;

    /* 用户账号*/
    private String userAccount;

    /* 薪资归属部门*/
    private String salaryDeptName;

    /* 用户角色*/
    private String userRoleName;

    /* 本月奖惩金额（可为正负）*/
    private BigDecimal monthRewordsMoney;

    /* 本月银行代发工资金额*/
    private BigDecimal monthBankSalary;

    /* 本月他行代发金额*/
    private BigDecimal monthOtherBankSalary;

    /* 本月基本工资*/
    private BigDecimal monthBaseSalary;

    /* 本月绩效工资*/
    private BigDecimal monthPerformanceSalary;

    /* 社保个人承担合计*/
    private BigDecimal socialSecurityPersonPayTotal;

    /* 公积金个人承担合计*/
    private BigDecimal housingFundPersonPayTotal;

    /* 本月费用个人总计*/
    private BigDecimal monthPersonPayTotal;

    /* 银行代发税前应发金额*/
    private BigDecimal bankTaxBeforeShouldSalary;

    /* 银行代发税率*/
    private BigDecimal bankTaxRatio;

    /* 银行代发本月实际应缴税额*/
    private BigDecimal bankRealityShouldTaxMoney;

    /* 银行代发工资实发总计*/
    private BigDecimal bankRealitySalary;

    /* 他行发放部分个税（计算）*/
    private BigDecimal otherBankShouldTaxMoney;

    /* 他行实发小计*/
    private BigDecimal otherBankRealitySalary;

    /* 增加项：电脑补*/
    private BigDecimal addComputerSubsidy;

    /* 增加项：其他*/
    private BigDecimal addOtherSubsidy;

    /* 扣款项：病假*/
    private BigDecimal deductSick;

    /* 扣款项：事假*/
    private BigDecimal deductThing;

    /* 扣款项：其他*/
    private BigDecimal deductOther;

    /* 扣款项：代缴手续费*/
    private BigDecimal deductServiceFee;

    /* 本月总工资实发总计（银行代发实发+他行实发）*/
    private BigDecimal monthSalaryRealityTotal;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public BigDecimal getMonthRewordsMoney() {
        return monthRewordsMoney;
    }

    public void setMonthRewordsMoney(BigDecimal monthRewordsMoney) {
        this.monthRewordsMoney = monthRewordsMoney;
    }

    public BigDecimal getMonthBankSalary() {
        return monthBankSalary;
    }

    public void setMonthBankSalary(BigDecimal monthBankSalary) {
        this.monthBankSalary = monthBankSalary;
    }

    public BigDecimal getMonthOtherBankSalary() {
        return monthOtherBankSalary;
    }

    public void setMonthOtherBankSalary(BigDecimal monthOtherBankSalary) {
        this.monthOtherBankSalary = monthOtherBankSalary;
    }

    public BigDecimal getMonthBaseSalary() {
        return monthBaseSalary;
    }

    public void setMonthBaseSalary(BigDecimal monthBaseSalary) {
        this.monthBaseSalary = monthBaseSalary;
    }

    public BigDecimal getMonthPerformanceSalary() {
        return monthPerformanceSalary;
    }

    public void setMonthPerformanceSalary(BigDecimal monthPerformanceSalary) {
        this.monthPerformanceSalary = monthPerformanceSalary;
    }

    public BigDecimal getSocialSecurityPersonPayTotal() {
        return socialSecurityPersonPayTotal;
    }

    public void setSocialSecurityPersonPayTotal(BigDecimal socialSecurityPersonPayTotal) {
        this.socialSecurityPersonPayTotal = socialSecurityPersonPayTotal;
    }

    public BigDecimal getHousingFundPersonPayTotal() {
        return housingFundPersonPayTotal;
    }

    public void setHousingFundPersonPayTotal(BigDecimal housingFundPersonPayTotal) {
        this.housingFundPersonPayTotal = housingFundPersonPayTotal;
    }

    public BigDecimal getMonthPersonPayTotal() {
        return monthPersonPayTotal;
    }

    public void setMonthPersonPayTotal(BigDecimal monthPersonPayTotal) {
        this.monthPersonPayTotal = monthPersonPayTotal;
    }

    public BigDecimal getBankTaxBeforeShouldSalary() {
        return bankTaxBeforeShouldSalary;
    }

    public void setBankTaxBeforeShouldSalary(BigDecimal bankTaxBeforeShouldSalary) {
        this.bankTaxBeforeShouldSalary = bankTaxBeforeShouldSalary;
    }

    public BigDecimal getBankTaxRatio() {
        return bankTaxRatio;
    }

    public void setBankTaxRatio(BigDecimal bankTaxRatio) {
        this.bankTaxRatio = bankTaxRatio;
    }

    public BigDecimal getBankRealityShouldTaxMoney() {
        return bankRealityShouldTaxMoney;
    }

    public void setBankRealityShouldTaxMoney(BigDecimal bankRealityShouldTaxMoney) {
        this.bankRealityShouldTaxMoney = bankRealityShouldTaxMoney;
    }

    public BigDecimal getBankRealitySalary() {
        return bankRealitySalary;
    }

    public void setBankRealitySalary(BigDecimal bankRealitySalary) {
        this.bankRealitySalary = bankRealitySalary;
    }

    public BigDecimal getOtherBankShouldTaxMoney() {
        return otherBankShouldTaxMoney;
    }

    public void setOtherBankShouldTaxMoney(BigDecimal otherBankShouldTaxMoney) {
        this.otherBankShouldTaxMoney = otherBankShouldTaxMoney;
    }

    public BigDecimal getOtherBankRealitySalary() {
        return otherBankRealitySalary;
    }

    public void setOtherBankRealitySalary(BigDecimal otherBankRealitySalary) {
        this.otherBankRealitySalary = otherBankRealitySalary;
    }

    public BigDecimal getAddComputerSubsidy() {
        return addComputerSubsidy;
    }

    public void setAddComputerSubsidy(BigDecimal addComputerSubsidy) {
        this.addComputerSubsidy = addComputerSubsidy;
    }

    public BigDecimal getAddOtherSubsidy() {
        return addOtherSubsidy;
    }

    public void setAddOtherSubsidy(BigDecimal addOtherSubsidy) {
        this.addOtherSubsidy = addOtherSubsidy;
    }

    public BigDecimal getDeductSick() {
        return deductSick;
    }

    public void setDeductSick(BigDecimal deductSick) {
        this.deductSick = deductSick;
    }

    public BigDecimal getDeductThing() {
        return deductThing;
    }

    public void setDeductThing(BigDecimal deductThing) {
        this.deductThing = deductThing;
    }

    public BigDecimal getDeductOther() {
        return deductOther;
    }

    public void setDeductOther(BigDecimal deductOther) {
        this.deductOther = deductOther;
    }

    public BigDecimal getDeductServiceFee() {
        return deductServiceFee;
    }

    public void setDeductServiceFee(BigDecimal deductServiceFee) {
        this.deductServiceFee = deductServiceFee;
    }

    public BigDecimal getMonthSalaryRealityTotal() {
        return monthSalaryRealityTotal;
    }

    public void setMonthSalaryRealityTotal(BigDecimal monthSalaryRealityTotal) {
        this.monthSalaryRealityTotal = monthSalaryRealityTotal;
    }
}
