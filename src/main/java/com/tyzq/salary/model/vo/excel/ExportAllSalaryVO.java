package com.tyzq.salary.model.vo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-28 14:09
 * @Description: //TODO 导出所有薪资excelVO
 **/
public class ExportAllSalaryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 序号*/
    @Excel(name = "序号")
    private Integer thisNumber;
    /**
     * 部门
     */
    @Excel(name = "部门")
    private String salaryDeptName;
    /**
     * 社保缴纳时间
     */
    @Excel(name = "社保缴纳时间")
    private Date socialSecurityStartDate;
    /**
     * 入职时间
     */
    @Excel(name = "入职时间")
    private Date userEntryDate;
    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String userName;
    /**
     * 身份证件号码
     */
    @Excel(name = "身份证件号码")
    private String userCard;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String userTel;
    /**
     * 用户手机号
     */
    @Excel(name = "职位")
    private String userRoleName;
    /**
     * 用户手机号
     */
    @Excel(name = "状态")
    private String userRankType;
    /**
     * 用户账号
     */
    @Excel(name = "员工账号")
    private String userAccount;
    /**
     * 备注
     */
    @Excel(name = "工作地点")
    private String workCity;
    /**
     * 户口类型：0--城镇，1--农村
     */
    @Excel(name = "薪资总额")
    private BigDecimal computeStandardSalary;




}
