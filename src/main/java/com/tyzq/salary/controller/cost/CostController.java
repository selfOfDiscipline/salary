package com.tyzq.salary.controller.cost;

import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.ProjectCost;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.CostQueryVO;
import com.tyzq.salary.model.vo.cost.ProjectCostQueryVO;
import com.tyzq.salary.service.cost.CostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:40 2021/2/2
 * @Description: //TODO 成本中心模块
 **/
@Api(tags = "【成本中心】··【成本管理模块】")
@RestController
@RequestMapping(value = "/cost")
public class CostController {

    private static final Logger logger = LoggerFactory.getLogger(CostController.class);

    @Autowired
    private CostService costService;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 10:03 2021/2/22
     * @Param:
     * @return:
     * @Description: //TODO 一键生成当月基础成本数据信息, 日期不存在就默认生成上个月的,日期格式：2021-02
     **/
    @ApiOperation(value = "一键生成当月基础成本数据信息", httpMethod = "GET", notes = "一键生成当月基础成本数据信息, 日期不存在就默认生成上个月的,日期格式：2021-02")
    @GetMapping(value = "/generateCostDataByDate")
    public ApiResult generateCostDataByDate(@RequestParam(value = "date", required = false) String date, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return costService.generateCostDataByDate(date, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("一键生成当月基础成本数据信息错误异常：" + e);
            return ApiResult.getFailedApiResponse("一键生成当月基础成本数据信息出现错误异常！");
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 15:43 2021/2/23
     * @Param:
     * @return:
     * @Description: //TODO 查询项目的月度成本详情,根据项目编号 + 所选月份(格式：yyyy-MM)，月份不传默认为上个月，查询该项目下所有成本，用于计算
     **/
    @ApiOperation(value = "查询项目的月度成本详情", httpMethod = "POST", notes = "根据项目编号 + 所选月份(格式：yyyy-MM)，查询该项目下所有成本，用于计算")
    @PostMapping(value = "/getProjectCostByCondition")
    public ApiResult selectCostList(@RequestBody ProjectCostQueryVO costQueryVO) {
        try {
            // 业务操作
            return costService.getProjectCostByCondition(costQueryVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询项目的月度成本详情错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询项目的月度成本详情出现错误异常！");
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:22 2021/2/25
     * @Param:
     * @return:
     * @Description: //TODO 计算当前明细（人员） 计算当前明细（人员），有id是修改，无id新增
     **/
    @ApiOperation(value = "计算当前明细（人员）", httpMethod = "POST", notes = "计算当前明细（人员），有id是修改，无id新增")
    @PostMapping(value = "/computeThisCost/{projectCode}/{costDate}")
    public ApiResult computeThisCost(@PathVariable("projectCode") String projectCode, @PathVariable("costDate") String costDate,
                                     @RequestBody ProjectCost projectCost, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return costService.computeThisCost(projectCode, costDate, projectCost, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("计算当前明细（人员）错误异常：" + e);
            return ApiResult.getFailedApiResponse("计算当前明细（人员）出现错误异常！");
        }
    }
}
