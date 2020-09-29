package com.tyzq.salary.model.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-18 17:14
 * @Description: //TODO 用户基本信息返回结果VO
 **/
public class UserBaseResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
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
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户性别：0--男，1--女
     */
    private Integer userSex;
    /**
     * 入职日期
     */
    private Date userEntryDate;
    /**
     * 预计转正日期
     */
    private Date planChangeFormalDate;
    /**
     * 户口类型：0--城镇，1--农村
     */
    private Integer householdType;
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
     * 绩效占工资比例
     */
    private BigDecimal performanceRatio;
    /**
     * 业务归属部门名称
     */
    private String userDeptName;
    /**
     * 薪资归属部门名称
     */
    private String salaryDeptName;
    /**
     * 职位
     */
    private String roleName;

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

    public Integer getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(Integer householdType) {
        this.householdType = householdType;
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

    public BigDecimal getPerformanceRatio() {
        return performanceRatio;
    }

    public void setPerformanceRatio(BigDecimal performanceRatio) {
        this.performanceRatio = performanceRatio;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
