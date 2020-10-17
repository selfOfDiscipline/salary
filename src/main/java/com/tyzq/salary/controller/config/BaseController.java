package com.tyzq.salary.controller.config;

import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.*;
import com.tyzq.salary.service.config.BaseService;
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
 * @CreateTime: 2020-09-15 09:18
 * @Description: //TODO 基础控制类
 **/
@Api(tags = "【基础配置】··【基础模块】")
@RestController
@RequestMapping(value = "/config/base")
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private BaseService baseService;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:43 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询薪资归属部门列表(追加按权限)
     **/
    @ApiOperation(value = "薪资归属部门列表", httpMethod = "POST", notes = "查询薪资归属部门列表（追加按权限）")
    @PostMapping(value = "/selectSalaryDeptList")
    public ApiResult selectSalaryDeptList(@RequestBody SalaryDeptQueryVO salaryDeptQueryVO, HttpServletRequest request) {
        try {
            // 业务操作
            return baseService.selectSalaryDeptList(salaryDeptQueryVO, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询薪资核算部门列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询薪资核算部门列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:33 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询核算人员与部门配置列表
     **/
    @ApiOperation(value = "查询核算人员与部门配置列表", httpMethod = "POST", notes = "查询核算人员与部门配置列表")
    @PostMapping(value = "/selectUserSalaryDeptList")
    public ApiResult selectUserSalaryDeptList(@RequestBody UserSalaryDeptQueryVO userSalaryDeptQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        try {
            // 业务操作
            return baseService.selectUserSalaryDeptList(userSalaryDeptQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询核算人员与部门配置列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询核算人员与部门配置列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:46 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询角色是薪资核算的人员列表
     **/
    @ApiOperation(value = "查询角色是薪资核算的人员列表", httpMethod = "POST", notes = "查询角色是薪资核算的人员列表")
    @PostMapping(value = "/selectUserComputeRoleList")
    public ApiResult selectUserComputeRoleList(@RequestBody UserSalaryDeptQueryVO userSalaryDeptQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        try {
            // 业务操作
            return baseService.selectUserComputeRoleList(userSalaryDeptQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询角色是薪资核算的人员列表出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询角色是薪资核算的人员列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:56 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 新增用户角色关联数据
     **/
    @ApiOperation(value = "新增用户薪资部门关联数据", httpMethod = "POST", notes = "新增用户角色关联数据")
    @PostMapping(value = "/saveUserSalaryDept")
    public ApiResult saveUserSalaryDept(@RequestBody UserSalaryDeptSaveVO userSalaryDeptSaveVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验是否登录
        if (null == userSalaryDeptSaveVO) {
            return ApiResult.getFailedApiResponse("参数为空！");
        }
        try {
            // 业务操作
            return baseService.saveUserSalaryDept(userSalaryDeptSaveVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("新增用户薪资部门关联数据出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("新增用户薪资部门关联数据出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:23 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量删除用户薪资部门关联表数据
     **/
    @ApiOperation(value = "批量删除用户薪资部门关联表数据", httpMethod = "GET", notes = "批量删除用户薪资部门关联表数据，根据所传用户数据ids字符串，多个数据id用英文逗号分隔")
    @GetMapping(value = "/deleteUserSalaryDeptByIds")
    public ApiResult deleteUserSalaryDeptByIds(@RequestParam("ids") String ids, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验
        if (StringUtils.isBlank(ids)) {
            return ApiResult.getFailedApiResponse("请至少传一条ID！");
        }
        try {
            // 业务操作
            return baseService.deleteUserSalaryDeptByIds(ids, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量删除用户薪资部门关联表数据错误异常：" + e);
            return ApiResult.getFailedApiResponse("批量删除用户薪资部门关联表数据错误异常！");
        }
    }
}
