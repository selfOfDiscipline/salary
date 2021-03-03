package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2021-03-03
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
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
     * 用户密码
     */
    private String userPassword;
    /**
     * 加盐
     */
    private String userSalt;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户性别：0--男，1--女
     */
    private Integer userSex;
    /**
     * 户籍所在地
     */
    private String originalAddress;
    /**
     * 现居住地
     */
    private String nowAddress;
    /**
     * 入职日期
     */
    private Date userEntryDate;
    /**
     * 预计转正日期
     */
    private Date planChangeFormalDate;
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
     * 是否允许：0--不允许，1--允许
     */
    private Integer allowFlag;
    /**
     * 用户类型：0--员工，1--管理员
     */
    private Integer adminFlag;
    /**
     * 现金类税率
     */
    private BigDecimal personMoneyTax;
    /**
     * 标准出勤天数
     */
    private BigDecimal standardAttendanceDays;
    /**
     * 绩效类型：0--无；1--月度；2--季度
     */
    private Integer performanceType;
    /**
     * 日结工资
     */
    private BigDecimal practiceMoney;
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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getOriginalAddress() {
        return originalAddress;
    }

    public void setOriginalAddress(String originalAddress) {
        this.originalAddress = originalAddress;
    }

    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }

    public Date getUserEntryDate() {
        return userEntryDate;
    }

    public void setUserEntryDate(Date userEntryDate) {
        this.userEntryDate = userEntryDate;
    }

    public Date getPlanChangeFormalDate() {
        return planChangeFormalDate;
    }

    public void setPlanChangeFormalDate(Date planChangeFormalDate) {
        this.planChangeFormalDate = planChangeFormalDate;
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

    public Long getSalaryDeptId() {
        return salaryDeptId;
    }

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

    public Integer getAllowFlag() {
        return allowFlag;
    }

    public void setAllowFlag(Integer allowFlag) {
        this.allowFlag = allowFlag;
    }

    public Integer getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Integer adminFlag) {
        this.adminFlag = adminFlag;
    }

    public BigDecimal getPersonMoneyTax() {
        return personMoneyTax;
    }

    public void setPersonMoneyTax(BigDecimal personMoneyTax) {
        this.personMoneyTax = personMoneyTax;
    }

    public BigDecimal getStandardAttendanceDays() {
        return standardAttendanceDays;
    }

    public void setStandardAttendanceDays(BigDecimal standardAttendanceDays) {
        this.standardAttendanceDays = standardAttendanceDays;
    }

    public Integer getPerformanceType() {
        return performanceType;
    }

    public void setPerformanceType(Integer performanceType) {
        this.performanceType = performanceType;
    }

    public BigDecimal getPracticeMoney() {
        return practiceMoney;
    }

    public void setPracticeMoney(BigDecimal practiceMoney) {
        this.practiceMoney = practiceMoney;
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
        return "User{" +
        "id=" + id +
        ", userName=" + userName +
        ", userCard=" + userCard +
        ", userTel=" + userTel +
        ", userAccount=" + userAccount +
        ", userPassword=" + userPassword +
        ", userSalt=" + userSalt +
        ", userEmail=" + userEmail +
        ", userSex=" + userSex +
        ", originalAddress=" + originalAddress +
        ", nowAddress=" + nowAddress +
        ", userEntryDate=" + userEntryDate +
        ", planChangeFormalDate=" + planChangeFormalDate +
        ", realityChangeFormalDate=" + realityChangeFormalDate +
        ", userLeaveDate=" + userLeaveDate +
        ", userDeptId=" + userDeptId +
        ", salaryDeptId=" + salaryDeptId +
        ", remark=" + remark +
        ", householdType=" + householdType +
        ", salaryBankCard=" + salaryBankCard +
        ", salaryBankOpen=" + salaryBankOpen +
        ", salaryBankOpenName=" + salaryBankOpenName +
        ", salaryBankOpenProvince=" + salaryBankOpenProvince +
        ", salaryBankOpenCity=" + salaryBankOpenCity +
        ", otherBankCard=" + otherBankCard +
        ", otherBankOpen=" + otherBankOpen +
        ", otherBankOpenName=" + otherBankOpenName +
        ", otherBankOpenProvince=" + otherBankOpenProvince +
        ", otherBankOpenCity=" + otherBankOpenCity +
        ", socialSecurityCard=" + socialSecurityCard +
        ", socialSecurityStartDate=" + socialSecurityStartDate +
        ", workCity=" + workCity +
        ", userPostType=" + userPostType +
        ", userRankType=" + userRankType +
        ", allowFlag=" + allowFlag +
        ", adminFlag=" + adminFlag +
        ", personMoneyTax=" + personMoneyTax +
        ", standardAttendanceDays=" + standardAttendanceDays +
        ", performanceType=" + performanceType +
        ", practiceMoney=" + practiceMoney +
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
