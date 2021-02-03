package com.tyzq.salary.controller.external;

import com.alibaba.fastjson.JSON;
import com.tyzq.salary.common.enums.ExternalCodeEnum;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.external.ExternalSalaryParamVO;
import com.tyzq.salary.service.external.ExternalService;
import com.tyzq.salary.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:56 2021/1/26
 * @Description: //TODO 对外接口入口
 **/
@Api(tags = "【对外接口】··【对外接口薪资模块】")
@RestController
@RequestMapping(value = "/external/api")
public class ExternalController {

    private static final Logger logger = LoggerFactory.getLogger(ExternalController.class);

    @Value(value = "${keygen.querySalary}")
    private String querySalaryKey;

    @Autowired
    private ExternalService externalService;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:58 2021/1/26
     * @Param:
     * @return:
     * @Description: //TODO 根据账号和时间，查询用户薪资数据信息
     **/
    @ApiOperation(value = "对外接口：查用户当月薪资", httpMethod = "POST", notes = "根据账号和时间，查询用户薪资数据信息")
    @PostMapping(value = "/querySalaryExternal")
    public ApiResult querySalaryExternal(@RequestBody ExternalSalaryParamVO paramVO) {
        // 记录参数
        logger.info("薪资对外查询接口调用时间为：" + DateUtils.getNowDateString() + "，参数为：{}", JSON.toJSONString(paramVO));
        // 校验 用户账号
        if (StringUtils.isBlank(paramVO.getUserAccount())) {
            return ApiResult.getFailedApiResponse(ExternalCodeEnum.QUERY_SALARY_10001.getCode(), ExternalCodeEnum.QUERY_SALARY_10001.getMessage());
        }
        // 校验 薪资归属日期
        if (StringUtils.isBlank(paramVO.getSalaryDate()) || paramVO.getSalaryDate().length() != 7) {
            return ApiResult.getFailedApiResponse(ExternalCodeEnum.QUERY_SALARY_10003.getCode(), ExternalCodeEnum.QUERY_SALARY_10003.getMessage());
        }
        // 校验 时间戳
        if (null == paramVO.getTimestamp()) {
            return ApiResult.getFailedApiResponse(ExternalCodeEnum.QUERY_SALARY_10004.getCode(), ExternalCodeEnum.QUERY_SALARY_10004.getMessage());
        }
        // 签名
        String sign = DigestUtils.md5Hex(paramVO.getUserAccount() + "&" + paramVO.getSalaryDate() + "&" + paramVO.getTimestamp() + "&" + querySalaryKey).toLowerCase();
        // 校验 签名
        if (StringUtils.isBlank(paramVO.getSign()) || !paramVO.getSign().equals(sign)) {
            return ApiResult.getFailedApiResponse(ExternalCodeEnum.QUERY_SALARY_10005.getCode(), ExternalCodeEnum.QUERY_SALARY_10005.getMessage());
        }
        try {
            // 查询
            return externalService.querySalaryExternal(paramVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("薪资对外查询接口出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("调用失败！");
        }
    }
}
