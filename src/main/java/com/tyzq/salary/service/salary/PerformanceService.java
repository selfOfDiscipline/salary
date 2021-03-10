package com.tyzq.salary.service.salary;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.salary.PerformanceComputeVO;
import com.tyzq.salary.model.vo.salary.PerformanceQueryParamVO;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 9:18 2021/3/5
 * @Description: //TODO 绩效模块
 **/
public interface PerformanceService {

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:25 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 一键生成上季度员工绩效基础工资单，只能4/7/10/1月份生成
     **/
    ApiResult generateLastQuarterSalary(UserSessionVO userSessionVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:45 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 查询绩效核算列表，可根据用户名，薪资归属部门id，条查
     **/
    ApiResult selectPerformanceComputeList(PerformanceQueryParamVO paramVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 13:13 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 季度绩效计算接口，根据绩效系数 & 其他金额增减项
     **/
    ApiResult computePerformance(PerformanceComputeVO computeVO, UserSessionVO userSessionVO);
}
