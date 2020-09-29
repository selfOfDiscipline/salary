package com.tyzq.salary.common.enums;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-07 11:36
 * @Description: //TODO Api接口常量定义枚举
 **/
public enum ApiCodeEnum {

    SUCCESS(200, "操作成功！"),
    FAILED(500, "操作失败！"),
    BODY_NOT_MATCH(400, "请求的数据格式不符！"),
    LOGIN(401, "请登录！"),
    FORBIDDEN(403, "没有相关权限！"),
    NOT_FOUND(404, "未找到该资源！"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试！");

    /* code*/
    private int code;

    /* 提示信息*/
    private String message;

    ApiCodeEnum(int code, String message) {
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