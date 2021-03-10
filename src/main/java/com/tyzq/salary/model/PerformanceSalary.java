package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 绩效表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2021-03-04
 */
public class PerformanceSalary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户角色
     */
    private String userRoleName;
    /**
     * 薪资归属部门名称
     */
    private String salaryDeptName;
    /**
     * 所属年份
     */
    private String belongYear;
    /**
     * 所属季度
     */
    private String belongQuarter;
    /**
     * 绩效工资
     */
    private BigDecimal performanceSalary;
    /**
     * 绩效系数
     */
    private BigDecimal performanceRatio;
    /**
     * 其他金额增减项
     */
    private BigDecimal quarterRewordsMoney;
    /**
     * 实际应发金额
     */
    private BigDecimal shouldRealitySalary;
    /**
     * 税率
     */
    private BigDecimal tax;
    /**
     * 税额
     */
    private BigDecimal taxMoney;
    /**
     * 目前已发放金额
     */
    private BigDecimal realitySalary;
    /**
     * 目前未发金额
     */
    private BigDecimal nonRealitySalary;
    /**
     * 上次发放金额
     */
    private BigDecimal lastSendMoney;
    /**
     * 上次发放日期
     */
    private Date lastSendDate;
    /**
     * 薪资归属部门id
     */
    private Long salaryDeptId;
    /**
     * 是否允许再次计算：0--允许，1--不允许，3--已审核通过
     */
    private Integer againComputeFlag;
    /**
     * 本季度是否计算过该数据：0--未计算过，1--已计算过
     */
    private Integer currentComputeFlag;
    /**
     * 本月薪资是否已正常发放标识：0--否，1--是
     */
    private Integer payWagesFlag;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBelongYear() {
        return belongYear;
    }

    public void setBelongYear(String belongYear) {
        this.belongYear = belongYear;
    }

    public String getBelongQuarter() {
        return belongQuarter;
    }

    public void setBelongQuarter(String belongQuarter) {
        this.belongQuarter = belongQuarter;
    }

    public BigDecimal getPerformanceSalary() {
        return performanceSalary;
    }

    public void setPerformanceSalary(BigDecimal performanceSalary) {
        this.performanceSalary = performanceSalary;
    }

    public BigDecimal getPerformanceRatio() {
        return performanceRatio;
    }

    public void setPerformanceRatio(BigDecimal performanceRatio) {
        this.performanceRatio = performanceRatio;
    }

    public BigDecimal getQuarterRewordsMoney() {
        return quarterRewordsMoney;
    }

    public void setQuarterRewordsMoney(BigDecimal quarterRewordsMoney) {
        this.quarterRewordsMoney = quarterRewordsMoney;
    }

    public BigDecimal getShouldRealitySalary() {
        return shouldRealitySalary;
    }

    public void setShouldRealitySalary(BigDecimal shouldRealitySalary) {
        this.shouldRealitySalary = shouldRealitySalary;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(BigDecimal taxMoney) {
        this.taxMoney = taxMoney;
    }

    public BigDecimal getRealitySalary() {
        return realitySalary;
    }

    public void setRealitySalary(BigDecimal realitySalary) {
        this.realitySalary = realitySalary;
    }

    public BigDecimal getNonRealitySalary() {
        return nonRealitySalary;
    }

    public void setNonRealitySalary(BigDecimal nonRealitySalary) {
        this.nonRealitySalary = nonRealitySalary;
    }

    public BigDecimal getLastSendMoney() {
        return lastSendMoney;
    }

    public void setLastSendMoney(BigDecimal lastSendMoney) {
        this.lastSendMoney = lastSendMoney;
    }

    public Date getLastSendDate() {
        return lastSendDate;
    }

    public void setLastSendDate(Date lastSendDate) {
        this.lastSendDate = lastSendDate;
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

    public Integer getPayWagesFlag() {
        return payWagesFlag;
    }

    public void setPayWagesFlag(Integer payWagesFlag) {
        this.payWagesFlag = payWagesFlag;
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
        return "PerformanceSalary{" +
        "id=" + id +
        ", userId=" + userId +
        ", userAccount=" + userAccount +
        ", userName=" + userName +
        ", userRoleName=" + userRoleName +
        ", salaryDeptName=" + salaryDeptName +
        ", belongYear=" + belongYear +
        ", belongQuarter=" + belongQuarter +
        ", performanceSalary=" + performanceSalary +
        ", performanceRatio=" + performanceRatio +
        ", quarterRewordsMoney=" + quarterRewordsMoney +
        ", shouldRealitySalary=" + shouldRealitySalary +
        ", tax=" + tax +
        ", taxMoney=" + taxMoney +
        ", realitySalary=" + realitySalary +
        ", nonRealitySalary=" + nonRealitySalary +
        ", lastSendMoney=" + lastSendMoney +
        ", lastSendDate=" + lastSendDate +
        ", salaryDeptId=" + salaryDeptId +
        ", againComputeFlag=" + againComputeFlag +
        ", currentComputeFlag=" + currentComputeFlag +
        ", payWagesFlag=" + payWagesFlag +
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
