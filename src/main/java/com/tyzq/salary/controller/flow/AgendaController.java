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
     * @Description //TODO  查询待办列表--待处理
     **/
    @ApiOperation(value = "查询待办列表--待处理", httpMethod = "POST", notes = "查询待办列表--待处理")
    @PostMapping(value = "/selectPersonAgendaList")
    public ApiResult selectPersonAgendaList(@RequestBody FlowRecordQueryVO flowRecordQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return agendaService.selectPersonAgendaList(flowRecordQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询待办列表--待处理出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询待办列表--待处理出现错误异常！");
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
    @ApiOperation(value = "处理当前节点", httpMethod = "POST", notes = "处理当前节点")
    @PostMapping(value = "/handleThisNode")
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
     * @Description //TODO 查询个人发起的流程
     **/
    @ApiOperation(value = "查询个人发起的流程", httpMethod = "POST", notes = "查询个人发起的流程")
    @PostMapping(value = "/selectMineAgendaList")
    public ApiResult selectMineAgendaList(@RequestBody SalaryBillQueryVO salaryBillQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return agendaService.selectMineAgendaList(salaryBillQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询个人发起的流程出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询个人发起的流程出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:35 2020/10/13
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据单据编号，查询该流程的工资列表
     **/
    @ApiOperation(value = "查询该流程工资列表", httpMethod = "GET", notes = "根据单据编号，查询该流程的工资列表")
    @GetMapping(value = "/selectSalaryByApplicationCode")
    public ApiResult selectSalaryByApplicationCode(@RequestParam("applicationCode") String applicationCode, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return agendaService.selectSalaryByApplicationCode(applicationCode, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询该流程工资列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询该流程工资列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:55 2020/10/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 汇总薪资流程，根据薪资流程表id集合(仅人力资源总监角色)
     **/
    @ApiOperation(value = "汇总薪资流程", httpMethod = "GET", notes = "汇总薪资流程(仅人力资源总监角色)")
    @GetMapping(value = "/collectTheMonthSalaryFlow")
    public ApiResult collectTheMonthSalaryFlow(@RequestParam("ids") String ids, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        if (StringUtils.isBlank(ids)) {
            return ApiResult.getFailedApiResponse("至少选中一条待办进行汇总！");
        }
        try {
            // 业务操作
            return agendaService.collectTheMonthSalaryFlow(ids, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("汇总薪资流程出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("汇总薪资流程出现错误异常！");
        }
    }
}
