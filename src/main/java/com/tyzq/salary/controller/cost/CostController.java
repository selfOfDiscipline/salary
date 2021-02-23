package com.tyzq.salary.controller.cost;

import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.CostQueryVO;
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
     * @Description: //TODO 查询成本列表（根据权限）
     **/
    @ApiOperation(value = "查询成本列表", httpMethod = "POST", notes = "查询成本列表")
    @PostMapping(value = "/selectCostList")
    public ApiResult selectCostList(@RequestBody CostQueryVO queryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return costService.generateCostDataByDate(null, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询成本列表信息错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询成本列表出现错误异常！");
        }
    }
}
