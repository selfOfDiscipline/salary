package com.tyzq.salary.common.vo;

import com.tyzq.salary.common.enums.ApiCodeEnum;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-07 11:26
 * @Description: //TODO 结果响应类
 **/
public class ApiResult implements Serializable {

    private static final long serialVersionUID = -1283570981723235L;

    /*
    * 接口响应 code
    * */
    private Integer code;

    /*
     * 接口响应 提示信息
     * */
    private String message;

    /*
     * 接口响应 数据信息
     * */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiResult() {}

    public ApiResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ApiResult getSuccessApiResponse() {
        return new ApiResult(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMessage());
    }

    public static ApiResult getSuccessApiResponse(String message) {
        return new ApiResult(ApiCodeEnum.SUCCESS.getCode(), message);
    }

    public static ApiResult getSuccessApiResponse(Object data) {
        return new ApiResult(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMessage(), data);
    }

    public static ApiResult getSuccessApiResponse(Integer code, String message) {
        return new ApiResult(code, message);
    }

    public static ApiResult getSuccessApiResponse(Integer code, String message, Object data) {
        return new ApiResult(code, message, data);
    }

    public static ApiResult getFailedApiResponse() {
        return new ApiResult(ApiCodeEnum.FAILED.getCode(), ApiCodeEnum.FAILED.getMessage());
    }

    public static ApiResult getFailedApiResponse(String message) {
        return new ApiResult(ApiCodeEnum.FAILED.getCode(), message);
    }

    public static ApiResult getFailedApiResponse(Object data) {
        return new ApiResult(ApiCodeEnum.FAILED.getCode(), ApiCodeEnum.FAILED.getMessage(), data);
    }

    public static ApiResult getFailedApiResponse(Integer code, String message) {
        return new ApiResult(code, message);
    }

    public static ApiResult getFailedApiResponse(Integer code, String message, Object data) {
        return new ApiResult(code, message, data);
    }
}
