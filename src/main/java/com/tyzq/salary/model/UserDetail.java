package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户明细表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-10-23
 */
public class UserDetail implements Serializable {

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
     * 用户表账号
     */
    private String userAccount;
    /**
     * 员工标准薪资
     */
    private BigDecimal standardSalary;
    /**
     * 薪资发放比例
     */
    private BigDecimal salaryGrantRatio;
    /**
     * 计算后的员工计薪工资=员工标准薪资*薪资发放比例
     */
    private BigDecimal computeStandardSalary;
    /**
     * 试用期的员工计薪工资=员工标准薪资*试用期薪资发放比例
     */
    private BigDecimal computeProbationSalary;
    /**
     * 病假标准
     */
    private BigDecimal personSickStandard;
    /**
     * 绩效占工资比例
     */
    private BigDecimal performanceRatio;
    /**
     * 基本工资=(1-绩效占工资比例)*计算后的员工计薪工资
     */
    private BigDecimal baseSalary;
    /**
     * 绩效工资=绩效占工资比例*计算后的员工计薪工资
     */
    private BigDecimal performanceSalary;
    /**
     * 预设银行代发工资金额
     */
    private BigDecimal bankSalary;
    /**
     * 预设他行代发工资金额
     */
    private BigDecimal otherBankSalary;
    /**
     * 国家规定纳税起步金额
     */
    private BigDecimal stipulationStartTaxMoney;
    /**
     * 岗位工资
     */
    private BigDecimal postSalary;
    /**
     * 岗位津贴
     */
    private BigDecimal postSubsidy;
    /**
     * 基本工资（这里是导出工资表需要）
     */
    private BigDecimal otherSubsidy;
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
     * 专扣--其他扣除
     */
    private BigDecimal otherDeduct;
    /**
     * 专项扣除的总计金额
     */
    private BigDecimal specialDeductTotal;
    /**
     * 养老基数
     */
    private BigDecimal yanglBaseMoney;
    /**
     * 养老个人缴纳比例
     */
    private BigDecimal yanglPersonRatio;
    /**
     * 养老公司缴纳比例
     */
    private BigDecimal yanglCompanyRatio;
    /**
     * 失业基数
     */
    private BigDecimal shiyBaseMoney;
    /**
     * 失业个人缴纳比例
     */
    private BigDecimal shiyPersonRatio;
    /**
     * 失业公司缴纳比例
     */
    private BigDecimal shiyCompanyRatio;
    /**
     * 工伤基数
     */
    private BigDecimal gongsBaseMoney;
    /**
     * 工伤个人缴纳比例
     */
    private BigDecimal gongsPersonRatio;
    /**
     * 工伤公司缴纳比例
     */
    private BigDecimal gongsCompanyRatio;
    /**
     * 生育基数
     */
    private BigDecimal shengyBaseMoney;
    /**
     * 生育个人缴纳比例
     */
    private BigDecimal shengyPersonRatio;
    /**
     * 生育公司缴纳比例
     */
    private BigDecimal shengyCompanyRatio;
    /**
     * 医疗基数
     */
    private BigDecimal yilBaseMoney;
    /**
     * 医疗个人缴纳比例
     */
    private BigDecimal yilPersonRatio;
    /**
     * 医疗个人另缴金额
     */
    private BigDecimal yilPersonAddMoney;
    /**
     * 医疗公司缴纳比例
     */
    private BigDecimal yilCompanyRatio;
    /**
     * 医疗公司另缴金额
     */
    private BigDecimal yilCompanyAddMoney;
    /**
     * 其他险基数
     */
    private BigDecimal otherBaseMoney;
    /**
     * 其他险个人缴纳比例
     */
    private BigDecimal otherPersonRatio;
    /**
     * 其他险公司缴纳比例
     */
    private BigDecimal otherCompanyRatio;
    /**
     * 公积金基数
     */
    private BigDecimal housingFundBaseMoney;
    /**
     * 公积金个人缴纳比例
     */
    private BigDecimal housingFundPersonRatio;
    /**
     * 公积金公司缴纳比例
     */
    private BigDecimal housingFundCompanyRatio;
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
     * 累计专项附加扣除金额
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
     * 累计专项扣除（个人年度社保+公积金）
     */
    private BigDecimal totalOtherDeduct;
    /**
     * 增加项：电脑补
     */
    private BigDecimal addComputerSubsidy;
    /**
     * 增加项：其他补
     */
    private BigDecimal addOtherSubsidy;
    /**
     * 扣款项：病假
     */
    private BigDecimal deductSick;
    /**
     * 社保代缴手续费
     */
    private BigDecimal deductThing;
    /**
     * 扣款项：其他
     */
    private BigDecimal deductOther;
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

    public BigDecimal getStandardSalary() {
        return standardSalary;
    }

    public void setStandardSalary(BigDecimal standardSalary) {
        this.standardSalary = standardSalary;
    }

    public BigDecimal getSalaryGrantRatio() {
        return salaryGrantRatio;
    }

    public void setSalaryGrantRatio(BigDecimal salaryGrantRatio) {
        this.salaryGrantRatio = salaryGrantRatio;
    }

    public BigDecimal getComputeStandardSalary() {
        return computeStandardSalary;
    }

    public void setComputeStandardSalary(BigDecimal computeStandardSalary) {
        this.computeStandardSalary = computeStandardSalary;
    }

    public BigDecimal getComputeProbationSalary() {
        return computeProbationSalary;
    }

    public void setComputeProbationSalary(BigDecimal computeProbationSalary) {
        this.computeProbationSalary = computeProbationSalary;
    }

    public BigDecimal getPersonSickStandard() {
        return personSickStandard;
    }

    public void setPersonSickStandard(BigDecimal personSickStandard) {
        this.personSickStandard = personSickStandard;
    }

    public BigDecimal getPerformanceRatio() {
        return performanceRatio;
    }

    public void setPerformanceRatio(BigDecimal performanceRatio) {
        this.performanceRatio = performanceRatio;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getPerformanceSalary() {
        return performanceSalary;
    }

    public void setPerformanceSalary(BigDecimal performanceSalary) {
        this.performanceSalary = performanceSalary;
    }

    public BigDecimal getBankSalary() {
        return bankSalary;
    }

    public void setBankSalary(BigDecimal bankSalary) {
        this.bankSalary = bankSalary;
    }

    public BigDecimal getOtherBankSalary() {
        return otherBankSalary;
    }

    public void setOtherBankSalary(BigDecimal otherBankSalary) {
        this.otherBankSalary = otherBankSalary;
    }

    public BigDecimal getStipulationStartTaxMoney() {
        return stipulationStartTaxMoney;
    }

    public void setStipulationStartTaxMoney(BigDecimal stipulationStartTaxMoney) {
        this.stipulationStartTaxMoney = stipulationStartTaxMoney;
    }

    public BigDecimal getPostSalary() {
        return postSalary;
    }

    public void setPostSalary(BigDecimal postSalary) {
        this.postSalary = postSalary;
    }

    public BigDecimal getPostSubsidy() {
        return postSubsidy;
    }

    public void setPostSubsidy(BigDecimal postSubsidy) {
        this.postSubsidy = postSubsidy;
    }

    public BigDecimal getOtherSubsidy() {
        return otherSubsidy;
    }

    public void setOtherSubsidy(BigDecimal otherSubsidy) {
        this.otherSubsidy = otherSubsidy;
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

    public BigDecimal getOtherDeduct() {
        return otherDeduct;
    }

    public void setOtherDeduct(BigDecimal otherDeduct) {
        this.otherDeduct = otherDeduct;
    }

    public BigDecimal getSpecialDeductTotal() {
        return specialDeductTotal;
    }

    public void setSpecialDeductTotal(BigDecimal specialDeductTotal) {
        this.specialDeductTotal = specialDeductTotal;
    }

    public BigDecimal getYanglBaseMoney() {
        return yanglBaseMoney;
    }

    public void setYanglBaseMoney(BigDecimal yanglBaseMoney) {
        this.yanglBaseMoney = yanglBaseMoney;
    }

    public BigDecimal getYanglPersonRatio() {
        return yanglPersonRatio;
    }

    public void setYanglPersonRatio(BigDecimal yanglPersonRatio) {
        this.yanglPersonRatio = yanglPersonRatio;
    }

    public BigDecimal getYanglCompanyRatio() {
        return yanglCompanyRatio;
    }

    public void setYanglCompanyRatio(BigDecimal yanglCompanyRatio) {
        this.yanglCompanyRatio = yanglCompanyRatio;
    }

    public BigDecimal getShiyBaseMoney() {
        return shiyBaseMoney;
    }

    public void setShiyBaseMoney(BigDecimal shiyBaseMoney) {
        this.shiyBaseMoney = shiyBaseMoney;
    }

    public BigDecimal getShiyPersonRatio() {
        return shiyPersonRatio;
    }

    public void setShiyPersonRatio(BigDecimal shiyPersonRatio) {
        this.shiyPersonRatio = shiyPersonRatio;
    }

    public BigDecimal getShiyCompanyRatio() {
        return shiyCompanyRatio;
    }

    public void setShiyCompanyRatio(BigDecimal shiyCompanyRatio) {
        this.shiyCompanyRatio = shiyCompanyRatio;
    }

    public BigDecimal getGongsBaseMoney() {
        return gongsBaseMoney;
    }

    public void setGongsBaseMoney(BigDecimal gongsBaseMoney) {
        this.gongsBaseMoney = gongsBaseMoney;
    }

    public BigDecimal getGongsPersonRatio() {
        return gongsPersonRatio;
    }

    public void setGongsPersonRatio(BigDecimal gongsPersonRatio) {
        this.gongsPersonRatio = gongsPersonRatio;
    }

    public BigDecimal getGongsCompanyRatio() {
        return gongsCompanyRatio;
    }

    public void setGongsCompanyRatio(BigDecimal gongsCompanyRatio) {
        this.gongsCompanyRatio = gongsCompanyRatio;
    }

    public BigDecimal getShengyBaseMoney() {
        return shengyBaseMoney;
    }

    public void setShengyBaseMoney(BigDecimal shengyBaseMoney) {
        this.shengyBaseMoney = shengyBaseMoney;
    }

    public BigDecimal getShengyPersonRatio() {
        return shengyPersonRatio;
    }

    public void setShengyPersonRatio(BigDecimal shengyPersonRatio) {
        this.shengyPersonRatio = shengyPersonRatio;
    }

    public BigDecimal getShengyCompanyRatio() {
        return shengyCompanyRatio;
    }

    public void setShengyCompanyRatio(BigDecimal shengyCompanyRatio) {
        this.shengyCompanyRatio = shengyCompanyRatio;
    }

    public BigDecimal getYilBaseMoney() {
        return yilBaseMoney;
    }

    public void setYilBaseMoney(BigDecimal yilBaseMoney) {
        this.yilBaseMoney = yilBaseMoney;
    }

    public BigDecimal getYilPersonRatio() {
        return yilPersonRatio;
    }

    public void setYilPersonRatio(BigDecimal yilPersonRatio) {
        this.yilPersonRatio = yilPersonRatio;
    }

    public BigDecimal getYilPersonAddMoney() {
        return yilPersonAddMoney;
    }

    public void setYilPersonAddMoney(BigDecimal yilPersonAddMoney) {
        this.yilPersonAddMoney = yilPersonAddMoney;
    }

    public BigDecimal getYilCompanyRatio() {
        return yilCompanyRatio;
    }

    public void setYilCompanyRatio(BigDecimal yilCompanyRatio) {
        this.yilCompanyRatio = yilCompanyRatio;
    }

    public BigDecimal getYilCompanyAddMoney() {
        return yilCompanyAddMoney;
    }

    public void setYilCompanyAddMoney(BigDecimal yilCompanyAddMoney) {
        this.yilCompanyAddMoney = yilCompanyAddMoney;
    }

    public BigDecimal getOtherBaseMoney() {
        return otherBaseMoney;
    }

    public void setOtherBaseMoney(BigDecimal otherBaseMoney) {
        this.otherBaseMoney = otherBaseMoney;
    }

    public BigDecimal getOtherPersonRatio() {
        return otherPersonRatio;
    }

    public void setOtherPersonRatio(BigDecimal otherPersonRatio) {
        this.otherPersonRatio = otherPersonRatio;
    }

    public BigDecimal getOtherCompanyRatio() {
        return otherCompanyRatio;
    }

    public void setOtherCompanyRatio(BigDecimal otherCompanyRatio) {
        this.otherCompanyRatio = otherCompanyRatio;
    }

    public BigDecimal getHousingFundBaseMoney() {
        return housingFundBaseMoney;
    }

    public void setHousingFundBaseMoney(BigDecimal housingFundBaseMoney) {
        this.housingFundBaseMoney = housingFundBaseMoney;
    }

    public BigDecimal getHousingFundPersonRatio() {
        return housingFundPersonRatio;
    }

    public void setHousingFundPersonRatio(BigDecimal housingFundPersonRatio) {
        this.housingFundPersonRatio = housingFundPersonRatio;
    }

    public BigDecimal getHousingFundCompanyRatio() {
        return housingFundCompanyRatio;
    }

    public void setHousingFundCompanyRatio(BigDecimal housingFundCompanyRatio) {
        this.housingFundCompanyRatio = housingFundCompanyRatio;
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
        return "UserDetail{" +
        "id=" + id +
        ", userId=" + userId +
        ", userAccount=" + userAccount +
        ", standardSalary=" + standardSalary +
        ", salaryGrantRatio=" + salaryGrantRatio +
        ", computeStandardSalary=" + computeStandardSalary +
        ", computeProbationSalary=" + computeProbationSalary +
        ", personSickStandard=" + personSickStandard +
        ", performanceRatio=" + performanceRatio +
        ", baseSalary=" + baseSalary +
        ", performanceSalary=" + performanceSalary +
        ", bankSalary=" + bankSalary +
        ", otherBankSalary=" + otherBankSalary +
        ", stipulationStartTaxMoney=" + stipulationStartTaxMoney +
        ", postSalary=" + postSalary +
        ", postSubsidy=" + postSubsidy +
        ", otherSubsidy=" + otherSubsidy +
        ", childEducation=" + childEducation +
        ", continueEducation=" + continueEducation +
        ", homeLoanInterest=" + homeLoanInterest +
        ", homeRents=" + homeRents +
        ", supportParents=" + supportParents +
        ", otherDeduct=" + otherDeduct +
        ", specialDeductTotal=" + specialDeductTotal +
        ", yanglBaseMoney=" + yanglBaseMoney +
        ", yanglPersonRatio=" + yanglPersonRatio +
        ", yanglCompanyRatio=" + yanglCompanyRatio +
        ", shiyBaseMoney=" + shiyBaseMoney +
        ", shiyPersonRatio=" + shiyPersonRatio +
        ", shiyCompanyRatio=" + shiyCompanyRatio +
        ", gongsBaseMoney=" + gongsBaseMoney +
        ", gongsPersonRatio=" + gongsPersonRatio +
        ", gongsCompanyRatio=" + gongsCompanyRatio +
        ", shengyBaseMoney=" + shengyBaseMoney +
        ", shengyPersonRatio=" + shengyPersonRatio +
        ", shengyCompanyRatio=" + shengyCompanyRatio +
        ", yilBaseMoney=" + yilBaseMoney +
        ", yilPersonRatio=" + yilPersonRatio +
        ", yilPersonAddMoney=" + yilPersonAddMoney +
        ", yilCompanyRatio=" + yilCompanyRatio +
        ", yilCompanyAddMoney=" + yilCompanyAddMoney +
        ", otherBaseMoney=" + otherBaseMoney +
        ", otherPersonRatio=" + otherPersonRatio +
        ", otherCompanyRatio=" + otherCompanyRatio +
        ", housingFundBaseMoney=" + housingFundBaseMoney +
        ", housingFundPersonRatio=" + housingFundPersonRatio +
        ", housingFundCompanyRatio=" + housingFundCompanyRatio +
        ", totalIncomeMoney=" + totalIncomeMoney +
        ", totalTaxableSelfMoney=" + totalTaxableSelfMoney +
        ", totalAlreadyTaxableMoney=" + totalAlreadyTaxableMoney +
        ", totalDeductMoney=" + totalDeductMoney +
        ", totalSpecialDeductMoney=" + totalSpecialDeductMoney +
        ", totalChildEducation=" + totalChildEducation +
        ", totalContinueEducation=" + totalContinueEducation +
        ", totalHomeLoanInterest=" + totalHomeLoanInterest +
        ", totalHomeRents=" + totalHomeRents +
        ", totalSupportParents=" + totalSupportParents +
        ", totalOtherDeduct=" + totalOtherDeduct +
        ", addComputerSubsidy=" + addComputerSubsidy +
        ", addOtherSubsidy=" + addOtherSubsidy +
        ", deductSick=" + deductSick +
        ", deductThing=" + deductThing +
        ", deductOther=" + deductOther +
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
