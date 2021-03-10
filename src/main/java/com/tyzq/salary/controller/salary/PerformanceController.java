package com.tyzq.salary.controller.salary;

import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.salary.PerformanceComputeVO;
import com.tyzq.salary.model.vo.salary.PerformanceQueryParamVO;
import com.tyzq.salary.model.vo.salary.UserComputeSalaryQueryVO;
import com.tyzq.salary.service.salary.PerformanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 17:51 2021/3/4
 * @Description: //TODO 绩效管理模块
 **/
@Api(tags = "【绩效管理】··【绩效计算模块】")
@RestController
@RequestMapping(value = "/performance")
public class PerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceController.class);

    @Autowired
    private PerformanceService performanceService;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:14 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 一键生成上季度员工绩效基础工资单，只能4/7/10/1月份生成
     **/
    @ApiOperation(value = "一键生成上季度员工绩效基础工资单", httpMethod = "GET", notes = "一键生成上季度员工绩效基础工资单，只能4/7/10/1月份生成")
    @GetMapping(value = "/generateLastQuarterSalary")
    public synchronized ApiResult generateLastQuarterSalary(HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验权限是否为 副总
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || roleIdList.contains(Constants.OTHER_ROLE_ID)) {
            return ApiResult.getFailedApiResponse("您无权生成数据！");
        }
        try {
            // 业务操作
            return performanceService.generateLastQuarterSalary(userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("一键生成上季度员工绩效基础工资单错误异常：" + e);
            return ApiResult.getFailedApiResponse("一键生成上季度员工绩效基础工资单出现错误异常！");
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:39 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 查询绩效核算列表，可根据用户名，薪资归属部门id，条查
     **/
    @ApiOperation(value = "查询绩效核算列表", httpMethod = "POST", notes = "查询绩效核算列表，可根据用户名，薪资归属部门id，条查")
    @PostMapping(value = "/selectPerformanceComputeList")
    public ApiResult selectPerformanceComputeList(@RequestBody PerformanceQueryParamVO paramVO) {
        try {
            // 业务操作
            return performanceService.selectPerformanceComputeList(paramVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询绩效核算列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询绩效核算列表错误异常！");
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:39 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 季度绩效计算接口，根据绩效系数 & 其他金额增减项
     **/
    @ApiOperation(value = "季度绩效计算接口", httpMethod = "POST", notes = "季度绩效计算接口")
    @PostMapping(value = "/computePerformance")
    public synchronized ApiResult computePerformance(@RequestBody PerformanceComputeVO computeVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return performanceService.computePerformance(computeVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("季度绩效计算接口错误异常：" + e);
            return ApiResult.getFailedApiResponse("季度绩效计算接口错误异常！");
        }
    }
}
