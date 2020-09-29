package com.tyzq.salary.model.vo.user;

import com.tyzq.salary.model.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-18 14:47
 * @Description: //TODO 用户修改的参数VO
 **/
public class UserUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户user主表id*/
    private Long id;

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
     * 扣款项：事假
     */
    private BigDecimal deductThing;
    /**
     * 扣款项：其他
     */
    private BigDecimal deductOther;



}
