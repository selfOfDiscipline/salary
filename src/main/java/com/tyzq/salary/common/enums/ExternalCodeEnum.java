package com.tyzq.salary.common.enums;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 15:32 2021/1/26
 * @Description: //TODO 对外接口参数返回值枚举类
 **/
public enum ExternalCodeEnum {

    QUERY_SALARY_10001(10001, "用户账号必传！"),
    QUERY_SALARY_10002(10002, "该用户账号不存在！"),
    QUERY_SALARY_10003(10003, "薪资归属日期必传且格式为2021-01"),
    QUERY_SALARY_10004(10004, "时间戳必传！"),
    QUERY_SALARY_10005(10005, "签名有误！");

    /* code*/
    private int code;

    /* 提示信息*/
    private String message;

    ExternalCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
