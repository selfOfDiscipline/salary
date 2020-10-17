package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户薪资表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-10-13
 */
public class UserSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户表id
     */
    private Long userId;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 职位
     */
    private String userRoleName;
    /**
     * 薪资归属部门名称
     */
    private String salaryDeptName;
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
     * 病假缺勤天数
     */
    private BigDecimal monthRewordsMoney;
    /**
     * 本月绩效比例
     */
    private BigDecimal monthPerformanceRatio;
    /**
     * 本月银行代发工资金额
     */
    private BigDecimal monthBankSalary;
    /**
     * 本月他行代发金额
     */
    private BigDecimal monthOtherBankSalary;
    /**
     * 本月基本工资
     */
    private BigDecimal monthBaseSalary;
    /**
     * 本月绩效工资
     */
    private BigDecimal monthPerformanceSalary;
    /**
     * 本月岗位工资
     */
    private BigDecimal monthPostSalary;
    /**
     * 本月岗位津贴
     */
    private BigDecimal monthPostSubsidy;
    /**
     * 本月其他补贴
     */
    private BigDecimal monthOtherSubsidy;
    /**
     * 税前基本工资小计
     */
    private BigDecimal taxBeforeBaseSalaryTotal;
    /**
     * 养老个人缴纳金额
     */
    private BigDecimal yanglPersonPayMoney;
    /**
     * 养老公司缴纳金额
     */
    private BigDecimal yanglCompanyPayMoney;
    /**
     * 失业个人缴纳金额
     */
    private BigDecimal shiyPersonPayMoney;
    /**
     * 失业公司缴纳金额
     */
    private BigDecimal shiyCompanyPayMoney;
    /**
     * 工伤个人缴纳金额
     */
    private BigDecimal gongsPersonPayMoney;
    /**
     * 工伤公司缴纳金额
     */
    private BigDecimal gongsCompanyPayMoney;
    /**
     * 生育个人缴纳金额
     */
    private BigDecimal shengyPersonPayMoney;
    /**
     * 生育公司缴纳金额
     */
    private BigDecimal shengyCompanyPayMoney;
    /**
     * 医疗个人缴纳金额
     */
    private BigDecimal yilPersonPayMoney;
    /**
     * 医疗公司缴纳金额
     */
    private BigDecimal yilCompanyPayMoney;
    /**
     * 社保个人承担合计
     */
    private BigDecimal socialSecurityPersonPayTotal;
    /**
     * 社保公司承担合计
     */
    private BigDecimal socailSecurityCompanyPayTotal;
    /**
     * 公积金个人承担合计
     */
    private BigDecimal housingFundPersonPayTotal;
    /**
     * 公积金公司承担合计
     */
    private BigDecimal housingFundCompanyPayTotal;
    /**
     * 本月费用个人总计
     */
    private BigDecimal monthPersonPayTotal;
    /**
     * 本月费用公司总计
     */
    private BigDecimal monthCompanyPayTotal;
    /**
     * 本月个人+公司费用总计
     */
    private BigDecimal monthPersonCompanyPayTotal;
    /**
     * 银行代发税前应发金额
     */
    private BigDecimal bankTaxBeforeShouldSalary;
    /**
     * 银行代发应纳税所得额
     */
    private BigDecimal bankTaxableSelfMoney;
    /**
     * 银行代发税率
     */
    private BigDecimal bankTaxRatio;
    /**
     * 银行代发本月预估应缴税额
     */
    private BigDecimal bankPlanShouldTaxMoney;
    /**
     * 银行代发本月实际应缴税额
     */
    private BigDecimal bankRealityShouldTaxMoney;
    /**
     * 银行代发工资实发总计
     */
    private BigDecimal bankRealitySalary;
    /**
     * 他行发放部分个税（计算）
     */
    private BigDecimal otherBankShouldTaxMoney;
    /**
     * 他行实发小计
     */
    private BigDecimal otherBankRealitySalary;
    /**
     * 本月总工资实发总计（银行代发实发+他行实发）
     */
    private BigDecimal monthSalaryRealityTotal;
    /**
     * 增加项：电脑补
     */
    private BigDecimal addComputerSubsidy;
    /**
     * 增加项：其他
     */
    private BigDecimal addOtherSubsidy;
    /**
     * 扣款项：病假
     */
    private BigDecimal deductSick;
    /**
     * 扣款项：事假
     */
    private BigDecimal deductThing;
    /**
     * 扣款项：其他
     */
    private BigDecimal deductOther;
    /**
     * 薪资归属部门id
     */
    private Long salaryDeptId;
    /**
     * 是否允许再次计算：0--允许，1--不允许
     */
    private Integer againComputeFlag;
    /**
     * 本月是否计算过该数据：0--未计算过，1--已计算过
     */
    private Integer currentComputeFlag;
    /**
     * 是否删除：0为正常，1为已删除
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
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

    public BigDecimal getMonthPerformanceRatio() {
        return monthPerformanceRatio;
    }

    public void setMonthPerformanceRatio(BigDecimal monthPerformanceRatio) {
        this.monthPerformanceRatio = monthPerformanceRatio;
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

    public BigDecimal getMonthPostSalary() {
        return monthPostSalary;
    }

    public void setMonthPostSalary(BigDecimal monthPostSalary) {
        this.monthPostSalary = monthPostSalary;
    }

    public BigDecimal getMonthPostSubsidy() {
        return monthPostSubsidy;
    }

    public void setMonthPostSubsidy(BigDecimal monthPostSubsidy) {
        this.monthPostSubsidy = monthPostSubsidy;
    }

    public BigDecimal getMonthOtherSubsidy() {
        return monthOtherSubsidy;
    }

    public void setMonthOtherSubsidy(BigDecimal monthOtherSubsidy) {
        this.monthOtherSubsidy = monthOtherSubsidy;
    }

    public BigDecimal getTaxBeforeBaseSalaryTotal() {
        return taxBeforeBaseSalaryTotal;
    }

    public void setTaxBeforeBaseSalaryTotal(BigDecimal taxBeforeBaseSalaryTotal) {
        this.taxBeforeBaseSalaryTotal = taxBeforeBaseSalaryTotal;
    }

    public BigDecimal getYanglPersonPayMoney() {
        return yanglPersonPayMoney;
    }

    public void setYanglPersonPayMoney(BigDecimal yanglPersonPayMoney) {
        this.yanglPersonPayMoney = yanglPersonPayMoney;
    }

    public BigDecimal getYanglCompanyPayMoney() {
        return yanglCompanyPayMoney;
    }

    public void setYanglCompanyPayMoney(BigDecimal yanglCompanyPayMoney) {
        this.yanglCompanyPayMoney = yanglCompanyPayMoney;
    }

    public BigDecimal getShiyPersonPayMoney() {
        return shiyPersonPayMoney;
    }

    public void setShiyPersonPayMoney(BigDecimal shiyPersonPayMoney) {
        this.shiyPersonPayMoney = shiyPersonPayMoney;
    }

    public BigDecimal getShiyCompanyPayMoney() {
        return shiyCompanyPayMoney;
    }

    public void setShiyCompanyPayMoney(BigDecimal shiyCompanyPayMoney) {
        this.shiyCompanyPayMoney = shiyCompanyPayMoney;
    }

    public BigDecimal getGongsPersonPayMoney() {
        return gongsPersonPayMoney;
    }

    public void setGongsPersonPayMoney(BigDecimal gongsPersonPayMoney) {
        this.gongsPersonPayMoney = gongsPersonPayMoney;
    }

    public BigDecimal getGongsCompanyPayMoney() {
        return gongsCompanyPayMoney;
    }

    public void setGongsCompanyPayMoney(BigDecimal gongsCompanyPayMoney) {
        this.gongsCompanyPayMoney = gongsCompanyPayMoney;
    }

    public BigDecimal getShengyPersonPayMoney() {
        return shengyPersonPayMoney;
    }

    public void setShengyPersonPayMoney(BigDecimal shengyPersonPayMoney) {
        this.shengyPersonPayMoney = shengyPersonPayMoney;
    }

    public BigDecimal getShengyCompanyPayMoney() {
        return shengyCompanyPayMoney;
    }

    public void setShengyCompanyPayMoney(BigDecimal shengyCompanyPayMoney) {
        this.shengyCompanyPayMoney = shengyCompanyPayMoney;
    }

    public BigDecimal getYilPersonPayMoney() {
        return yilPersonPayMoney;
    }

    public void setYilPersonPayMoney(BigDecimal yilPersonPayMoney) {
        this.yilPersonPayMoney = yilPersonPayMoney;
    }

    public BigDecimal getYilCompanyPayMoney() {
        return yilCompanyPayMoney;
    }

    public void setYilCompanyPayMoney(BigDecimal yilCompanyPayMoney) {
        this.yilCompanyPayMoney = yilCompanyPayMoney;
    }

    public BigDecimal getSocialSecurityPersonPayTotal() {
        return socialSecurityPersonPayTotal;
    }

    public void setSocialSecurityPersonPayTotal(BigDecimal socialSecurityPersonPayTotal) {
        this.socialSecurityPersonPayTotal = socialSecurityPersonPayTotal;
    }

    public BigDecimal getSocailSecurityCompanyPayTotal() {
        return socailSecurityCompanyPayTotal;
    }

    public void setSocailSecurityCompanyPayTotal(BigDecimal socailSecurityCompanyPayTotal) {
        this.socailSecurityCompanyPayTotal = socailSecurityCompanyPayTotal;
    }

    public BigDecimal getHousingFundPersonPayTotal() {
        return housingFundPersonPayTotal;
    }

    public void setHousingFundPersonPayTotal(BigDecimal housingFundPersonPayTotal) {
        this.housingFundPersonPayTotal = housingFundPersonPayTotal;
    }

    public BigDecimal getHousingFundCompanyPayTotal() {
        return housingFundCompanyPayTotal;
    }

    public void setHousingFundCompanyPayTotal(BigDecimal housingFundCompanyPayTotal) {
        this.housingFundCompanyPayTotal = housingFundCompanyPayTotal;
    }

    public BigDecimal getMonthPersonPayTotal() {
        return monthPersonPayTotal;
    }

    public void setMonthPersonPayTotal(BigDecimal monthPersonPayTotal) {
        this.monthPersonPayTotal = monthPersonPayTotal;
    }

    public BigDecimal getMonthCompanyPayTotal() {
        return monthCompanyPayTotal;
    }

    public void setMonthCompanyPayTotal(BigDecimal monthCompanyPayTotal) {
        this.monthCompanyPayTotal = monthCompanyPayTotal;
    }

    public BigDecimal getMonthPersonCompanyPayTotal() {
        return monthPersonCompanyPayTotal;
    }

    public void setMonthPersonCompanyPayTotal(BigDecimal monthPersonCompanyPayTotal) {
        this.monthPersonCompanyPayTotal = monthPersonCompanyPayTotal;
    }

    public BigDecimal getBankTaxBeforeShouldSalary() {
        return bankTaxBeforeShouldSalary;
    }

    public void setBankTaxBeforeShouldSalary(BigDecimal bankTaxBeforeShouldSalary) {
        this.bankTaxBeforeShouldSalary = bankTaxBeforeShouldSalary;
    }

    public BigDecimal getBankTaxableSelfMoney() {
        return bankTaxableSelfMoney;
    }

    public void setBankTaxableSelfMoney(BigDecimal bankTaxableSelfMoney) {
        this.bankTaxableSelfMoney = bankTaxableSelfMoney;
    }

    public BigDecimal getBankTaxRatio() {
        return bankTaxRatio;
    }

    public void setBankTaxRatio(BigDecimal bankTaxRatio) {
        this.bankTaxRatio = bankTaxRatio;
    }

    public BigDecimal getBankPlanShouldTaxMoney() {
        return bankPlanShouldTaxMoney;
    }

    public void setBankPlanShouldTaxMoney(BigDecimal bankPlanShouldTaxMoney) {
        this.bankPlanShouldTaxMoney = bankPlanShouldTaxMoney;
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

    public BigDecimal getMonthSalaryRealityTotal() {
        return monthSalaryRealityTotal;
    }

    public void setMonthSalaryRealityTotal(BigDecimal monthSalaryRealityTotal) {
        this.monthSalaryRealityTotal = monthSalaryRealityTotal;
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

    public Long getSalaryDeptId() {
        return salaryDeptId;
    }

    public void setSalaryDeptId(Long salaryDeptId) {
        this.salaryDeptId = salaryDeptId;
    }

    public Integer getAgainComputeFlag() {
        return againComputeFlag;
    }

    public void setAgainComputeFlag(Integer againComputeFlag) {
        this.againComputeFlag = againComputeFlag;
    }

    public Integer getCurrentComputeFlag() {
        return currentComputeFlag;
    }

    public void setCurrentComputeFlag(Integer currentComputeFlag) {
        this.currentComputeFlag = currentComputeFlag;
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

    public BigDecimal getMonthRewordsMoney() {
        return monthRewordsMoney;
    }

    public void setMonthRewordsMoney(BigDecimal monthRewordsMoney) {
        this.monthRewordsMoney = monthRewordsMoney;
    }

    @Override
    public String toString() {
        return "UserSalary{" +
        "id=" + id +
        ", userId=" + userId +
        ", userAccount=" + userAccount +
        ", userRoleName=" + userRoleName +
        ", salaryDeptName=" + salaryDeptName +
        ", salaryDate=" + salaryDate +
        ", newEntryAttendanceDays=" + newEntryAttendanceDays +
        ", positiveBeforeAttendanceDays=" + positiveBeforeAttendanceDays +
        ", positiveAfterAttendanceDays=" + positiveAfterAttendanceDays +
        ", positiveAfterOtherAttendanceDays=" + positiveAfterOtherAttendanceDays +
        ", positiveAfterSickAttendanceDays=" + positiveAfterSickAttendanceDays +
        ", positiveBeforeOtherAttendanceDays=" + positiveBeforeOtherAttendanceDays +
        ", positiveBeforeSickAttendanceDays=" + positiveBeforeSickAttendanceDays +
        ", otherAbsenceDays=" + otherAbsenceDays +
        ", sickAdsenceDays=" + sickAdsenceDays +
        ", monthRewordsMoney=" + monthRewordsMoney +
        ", monthPerformanceRatio=" + monthPerformanceRatio +
        ", monthBankSalary=" + monthBankSalary +
        ", monthOtherBankSalary=" + monthOtherBankSalary +
        ", monthBaseSalary=" + monthBaseSalary +
        ", monthPerformanceSalary=" + monthPerformanceSalary +
        ", monthPostSalary=" + monthPostSalary +
        ", monthPostSubsidy=" + monthPostSubsidy +
        ", monthOtherSubsidy=" + monthOtherSubsidy +
        ", taxBeforeBaseSalaryTotal=" + taxBeforeBaseSalaryTotal +
        ", yanglPersonPayMoney=" + yanglPersonPayMoney +
        ", yanglCompanyPayMoney=" + yanglCompanyPayMoney +
        ", shiyPersonPayMoney=" + shiyPersonPayMoney +
        ", shiyCompanyPayMoney=" + shiyCompanyPayMoney +
        ", gongsPersonPayMoney=" + gongsPersonPayMoney +
        ", gongsCompanyPayMoney=" + gongsCompanyPayMoney +
        ", shengyPersonPayMoney=" + shengyPersonPayMoney +
        ", shengyCompanyPayMoney=" + shengyCompanyPayMoney +
        ", yilPersonPayMoney=" + yilPersonPayMoney +
        ", yilCompanyPayMoney=" + yilCompanyPayMoney +
        ", socialSecurityPersonPayTotal=" + socialSecurityPersonPayTotal +
        ", socailSecurityCompanyPayTotal=" + socailSecurityCompanyPayTotal +
        ", housingFundPersonPayTotal=" + housingFundPersonPayTotal +
        ", housingFundCompanyPayTotal=" + housingFundCompanyPayTotal +
        ", monthPersonPayTotal=" + monthPersonPayTotal +
        ", monthCompanyPayTotal=" + monthCompanyPayTotal +
        ", monthPersonCompanyPayTotal=" + monthPersonCompanyPayTotal +
        ", bankTaxBeforeShouldSalary=" + bankTaxBeforeShouldSalary +
        ", bankTaxableSelfMoney=" + bankTaxableSelfMoney +
        ", bankTaxRatio=" + bankTaxRatio +
        ", bankPlanShouldTaxMoney=" + bankPlanShouldTaxMoney +
        ", bankRealityShouldTaxMoney=" + bankRealityShouldTaxMoney +
        ", bankRealitySalary=" + bankRealitySalary +
        ", otherBankShouldTaxMoney=" + otherBankShouldTaxMoney +
        ", otherBankRealitySalary=" + otherBankRealitySalary +
        ", monthSalaryRealityTotal=" + monthSalaryRealityTotal +
        ", addComputerSubsidy=" + addComputerSubsidy +
        ", addOtherSubsidy=" + addOtherSubsidy +
        ", deductSick=" + deductSick +
        ", deductThing=" + deductThing +
        ", deductOther=" + deductOther +
        ", salaryDeptId=" + salaryDeptId +
        ", againComputeFlag=" + againComputeFlag +
        ", currentComputeFlag=" + currentComputeFlag +
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
