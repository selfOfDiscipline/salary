package com.tyzq.salary.controller.flow;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.BaseFlowConfigDetailMapper;
import com.tyzq.salary.mapper.BaseFlowConfigMapper;
import com.tyzq.salary.model.BaseFlowConfig;
import com.tyzq.salary.model.BaseFlowConfigDetail;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.flow.FlowConfigSaveVO;
import com.tyzq.salary.model.vo.flow.FlowQueryVO;
import com.tyzq.salary.service.flow.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 15:05
 * @Description: //TODO 流程配置模块
 **/
@Api(tags = "【基础配置】··【流程管理】")
@RestController
@RequestMapping(value = "/flow")
public class FlowController {

    private static final Logger logger = LoggerFactory.getLogger(FlowController.class);

    @Autowired
    private FlowService flowService;

    @Resource
    private BaseFlowConfigMapper baseFlowConfigMapper;

    @Resource
    private BaseFlowConfigDetailMapper baseFlowConfigDetailMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:14 2020/9/27
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO  查询所有流程配置列表
     **/
    @ApiOperation(value = "查询所有流程配置列表", httpMethod = "POST", notes = "查询所有流程配置列表")
    @PostMapping(value = "/selectFlowList")
    public ApiResult selectFlowList(@RequestBody FlowQueryVO flowQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return flowService.selectFlowList(flowQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询所有流程配置列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询所有流程配置列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:24 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO (总经理/副总权限可新增修改)新增或修改流程信息有id为修改，无id为新增
     **/
    @ApiOperation(value = "新增或修改流程信息", httpMethod = "POST", notes = "(总经理/副总权限可新增修改)新增或修改流程信息有id为修改，无id为新增")
    @PostMapping(value = "/saveOrUpdateFlowConfig")
    public ApiResult saveOrUpdateFlowConfig(@RequestBody FlowConfigSaveVO flowConfigSaveVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验权限是否为总经理/副总/薪资核算角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权新增修改流程配置信息！");
        }
        // 校验参数
        if (null == flowConfigSaveVO || null == flowConfigSaveVO.getBaseFlowConfig() || null == flowConfigSaveVO.getBaseFlowConfigDetailList()) {
            return ApiResult.getFailedApiResponse("流程配置主表明细表不能为空！");
        }
        try {
            // 业务操作
            return flowService.saveOrUpdateFlowConfig(flowConfigSaveVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("新增或修改流程信息错误异常：" + e);
            return ApiResult.getFailedApiResponse("新增或修改流程信息出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:59 2020/9/27
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 批量删除流程主表，根据所传流程主表ids字符串，多个流程主表id用英文逗号分隔
     **/
    @ApiOperation(value = "批量删除流程主表", httpMethod = "GET", notes = "批量删除流程主表，根据所传流程主表ids字符串，多个流程主表id用英文逗号分隔")
    @GetMapping(value = "/deleteFlowConfigByIds")
    public ApiResult deleteFlowConfigByIds(@RequestParam("ids") String ids, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验
        if (StringUtils.isBlank(ids)) {
            return ApiResult.getFailedApiResponse("请至少传一条ID！");
        }
        try {
            // 业务操作
            return flowService.deleteFlowConfigByIds(ids, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量删除流程主表错误异常：" + e);
            return ApiResult.getFailedApiResponse("批量删除流程主表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:24 2020/9/18
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据流程主表ID查询流程信息
     **/
    @ApiOperation(value = "根据流程主表ID查询流程信息", httpMethod = "GET", notes = "根据流程主表ID查询流程信息")
    @GetMapping(value = "/getFlowConfigById")
    public ApiResult getFlowConfigById(@RequestParam("id") Long id) {
        // 校验
        if (null == id) {
            return ApiResult.getFailedApiResponse("请至少传一条ID！");
        }
        // 查询流程主表
        BaseFlowConfig baseFlowConfig = baseFlowConfigMapper.selectById(id);
        List<BaseFlowConfigDetail> baseFlowConfigDetailList = baseFlowConfigDetailMapper.selectList(Condition.create().eq("base_flow_config_id", id));
        // 赋值
        FlowConfigSaveVO flowConfigSaveVO = new FlowConfigSaveVO();
        flowConfigSaveVO.setBaseFlowConfig(baseFlowConfig);
        flowConfigSaveVO.setBaseFlowConfigDetailList(baseFlowConfigDetailList);
        return ApiResult.getSuccessApiResponse(flowConfigSaveVO);
    }
}
