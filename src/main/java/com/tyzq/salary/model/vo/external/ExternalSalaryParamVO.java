package com.tyzq.salary.model.vo.external;

import java.io.Serializable;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 15:20 2021/1/26
 * @Description: //TODO 对外薪资查询接口参数VO
 **/
public class ExternalSalaryParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户账号*/
    private String userAccount;

    /* 薪资归属日期  格式：yyyy-MM*/
    private String salaryDate;

    /* 当前时间戳*/
    private Long timestamp;

    /**
     * 签名
     * 规则：Md5(userAccount + & + salaryDate + & + timestamp + & + key).toLowerCase();
     */
    private String sign;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(String salaryDate) {
        this.salaryDate = salaryDate;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
