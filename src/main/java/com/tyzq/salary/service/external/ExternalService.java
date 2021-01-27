package com.tyzq.salary.service.external;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.external.ExternalSalaryParamVO;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:53 2021/1/26
 * @Description: //TODO 对接接口
 **/
public interface ExternalService {

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 16:05 2021/1/26
     * @Param:
     * @return:
     * @Description: //TODO 根据账号和时间，查询用户薪资数据信息
     **/
    ApiResult querySalaryExternal(ExternalSalaryParamVO paramVO);
}
