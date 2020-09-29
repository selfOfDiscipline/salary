package com.tyzq.salary.model.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-21 11:32
 * @Description: //TODO 用户计薪列表响应VO
 **/
public class UserSalaryResultVO implements Serializable {

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
     * 用户账号
     */
    private String userAccount;
    /**
     * 该薪资所属日期
     */
    private Date salaryDate;
    /**
     * 薪资核算归属部门id
     */
    private Long salaryDeptId;
    /**
     * 入职日期
     */
    private Date userEntryDate;
    /**
     * 实际转正日期
     */
    private Date realityChangeFormalDate;
    /**
     * 户口类型：0--城镇，1--农村
     */
    private Integer householdType;
    /**
     * 员工岗位类型：0--管理岗，1--成本岗，2--技术岗
     */
    private Integer userPostType;
    /**
     * 员工在职类型：0--试用期，1--正式，2--离职
     */
    private Integer userRankType;
    /**
     * 新入职出勤天数
     */
    private BigDecimal newEntryAttendanceDays;
    /**
     * 转正前出勤天数
     */
    private BigDecimal positiveBeforeAttendanceDays;
    /**
     * 转成后出勤天数
     */
    private BigDecimal positiveAfterAttendanceDays;
    /**
     * 缺勤天数
     */
    private BigDecimal absenceDays;
    /**
     * 本月绩效比例
     */
    private BigDecimal monthPerformanceRatio;
    /**
     * 预设银行代发工资金额
     */
    private BigDecimal bankSalary;
    /**
     * 预设他行代发工资金额
     */
    private BigDecimal otherBankSalary;
    /**
     * 基本工资=(1-绩效占工资比例)*计算后的员工计薪工资
     */
    private BigDecimal baseSalary;
    /**
     * 绩效工资=绩效占工资比例*计算后的员工计薪工资
     */
    private BigDecimal performanceSalary;
    /**
     * 养老失业基数
     */
    private BigDecimal yanglShiyBaseMoney;
    /**
     * 医疗工伤生育基数
     */
    private BigDecimal yilGongsShengyBaseMoney;
    /**
     * 公积金基数
     */
    private BigDecimal housingFundBaseMoney;
    /**
     * 专项扣除的总计金额
     */
    private BigDecimal specialDeductTotal;








}
