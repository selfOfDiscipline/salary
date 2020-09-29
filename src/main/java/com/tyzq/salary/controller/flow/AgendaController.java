package com.tyzq.salary.controller.flow;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.BaseFlowConfig;
import com.tyzq.salary.model.BaseFlowConfigDetail;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.flow.*;
import com.tyzq.salary.service.flow.AgendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 20:13
 * @Description: //TODO 待办管理控制类
 **/
@Api(tags = "【基础配置】··【待办管理】")
@RestController
@RequestMapping(value = "/agenda")
public class AgendaController {

    private static final Logger logger = LoggerFactory.getLogger(AgendaController.class);

    @Autowired
    private AgendaService agendaService;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:14 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  查询个人待办列表
     **/
    @ApiOperation(value = "查询个人待办列表", httpMethod = "POST", notes = "查询个人待办列表")
    @PostMapping(value = "/selectPersonAgendaList")
    public ApiResult selectPersonAgendaList(@RequestBody FlowRecordQueryVO flowRecordQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return agendaService.selectPersonAgendaList(flowRecordQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询个人待办列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询个人待办列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:26 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据单据编号，查询该单据对应的所有薪资列表
     **/
    @ApiOperation(value = "查询工资单列表", httpMethod = "GET", notes = "根据单据编号，查询该单据对应的所有薪资列表")
    @GetMapping(value = "/getSalaryInfoByApplicationCode")
    public ApiResult getSalaryInfoByApplicationCode(@RequestParam("applicationCode") String applicationCode) {
        // 校验
        if (StringUtils.isBlank(applicationCode)) {
            return ApiResult.getFailedApiResponse("单据编号不能为空！");
        }
        try {
            // 业务操作
            return agendaService.getSalaryInfoByApplicationCode(applicationCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询工资单列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询工资单列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:52 2020/9/27
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 处理当前节点
     **/
    @ApiOperation(value = "处理当前节点", httpMethod = "GET", notes = "处理当前节点")
    @GetMapping(value = "/handleThisNode")
    public ApiResult handleThisNode(@RequestBody FlowHandleParamVO handleParamVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验
        if (null == handleParamVO || null == handleParamVO.getId()) {
            return ApiResult.getFailedApiResponse("待办id不能为空！");
        }
        try {
            // 业务操作
            return agendaService.handleThisNode(handleParamVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理当前节点出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("处理当前节点出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 22:26 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 个人发起的流程列表
     **/
    @ApiOperation(value = "个人发起的流程列表", httpMethod = "POST", notes = "个人发起的流程列表")
    @PostMapping(value = "/selectMineAgendaList")
    public ApiResult selectMineAgendaList(@RequestBody SalaryBillQueryVO salaryBillQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return agendaService.selectMineAgendaList(salaryBillQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("个人发起的流程列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("个人发起的流程列表出现错误异常！");
        }
    }
}
