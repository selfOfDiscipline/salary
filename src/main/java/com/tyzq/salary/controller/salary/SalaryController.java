package com.tyzq.salary.controller.salary;

import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.SalaryDeptMapper;
import com.tyzq.salary.mapper.UserSalaryDeptMapper;
import com.tyzq.salary.mapper.UserSalaryMapper;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.salary.ComputeSalaryParamVO;
import com.tyzq.salary.model.vo.salary.SalaryHistoryQueryVO;
import com.tyzq.salary.model.vo.salary.UserComputeSalaryQueryVO;
import com.tyzq.salary.service.salary.SalaryService;
import com.tyzq.salary.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-08 16:50
 * @Description: //TODO 薪水模块
 **/
@Api(tags = "【薪资管理】··【薪资计算模块】")
@RestController
@RequestMapping(value = "/salary")
public class SalaryController {

    private static final Logger logger = LoggerFactory.getLogger(SalaryController.class);

    @Autowired
    private SalaryService salaryService;

    @Resource
    private UserSalaryDeptMapper userSalaryDeptMapper;

    @Resource
    private SalaryDeptMapper salaryDeptMapper;

    @Resource
    private UserSalaryMapper userSalaryMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:19 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 计薪列表查询
     **/
    @ApiOperation(value = "计薪列表查询", httpMethod = "POST", notes = "计薪列表查询")
    @PostMapping(value = "/selectUserListBySalaryUser")
    public ApiResult selectUserListBySalaryUser(@RequestBody UserComputeSalaryQueryVO userComputeSalaryQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        try {
            // 业务操作
            return salaryService.selectUserListBySalaryUser(userComputeSalaryQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询当前薪资核算负责人所关联员工错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询当前薪资核算负责人所关联员工出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:03 2020/9/21
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 一键生成该负责人对应员工的本月基础工资单
     **/
    @ApiOperation(value = "一键生成该负责人对应员工的本月基础工资单", httpMethod = "GET", notes = "一键生成该负责人对应员工的本月基础工资单")
    @GetMapping(value = "/generateTheMonthBasePayroll")
    public synchronized ApiResult generateTheMonthBasePayroll(HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验权限是否为总经理/副总/薪资核算角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID) && !roleIdList.contains(Constants.SALARY_DEPT_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权生成数据！");
        }
        // 定义是否为总经理/副总 角色标识
        boolean adminFlag = false;
        if (roleIdList.contains(Constants.ADMIN_ROLE_ID) || roleIdList.contains(Constants.OTHER_ROLE_ID)) {
            adminFlag = true;
        }
        try {
            // 业务操作
            return salaryService.generateTheMonthBasePayroll(userSessionVO, adminFlag);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("一键生成该负责人对应员工的本月基础工资单错误异常：" + e);
            return ApiResult.getFailedApiResponse("一键生成该负责人对应员工的本月基础工资单出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 13:49 2020/9/22
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 上月入职员工计薪
     **/
    @ApiOperation(value = "上月入职员工计薪", httpMethod = "POST", notes = "上月入职员工计薪")
    @PostMapping(value = "/lastMonthIncomeCompute")
    public synchronized ApiResult lastMonthIncomeCompute(@RequestBody ComputeSalaryParamVO computeSalaryParamVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验必填参数
        if (null == computeSalaryParamVO || null == computeSalaryParamVO.getId()) {
            return ApiResult.getFailedApiResponse("该数据ID不能为空！");
        }
        // 校验必填参数
        if (null == computeSalaryParamVO.getNewEntryAttendanceDays()) {
            return ApiResult.getFailedApiResponse("新入职出勤天数不能为空！");
        }
        // 校验 绩效
        if (null == computeSalaryParamVO.getMonthPerformanceRatio()) {
            computeSalaryParamVO.setMonthPerformanceRatio(new BigDecimal("1.00"));
        }
        try {
            // 业务操作
            return salaryService.lastMonthIncomeCompute(computeSalaryParamVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上月入职员工计薪错误异常：" + e);
            return ApiResult.getFailedApiResponse("上月入职员工计薪出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:46 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 上月转正员工计薪
     **/
    @ApiOperation(value = "上月转正员工计薪", httpMethod = "POST", notes = "上月转正员工计薪")
    @PostMapping(value = "/lastMonthBecomeCompute")
    public synchronized ApiResult lastMonthBecomeCompute(@RequestBody ComputeSalaryParamVO computeSalaryParamVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验必填参数
        if (null == computeSalaryParamVO || null == computeSalaryParamVO.getId()) {
            return ApiResult.getFailedApiResponse("该数据ID不能为空！");
        }
        // 校验必填参数
        if (null == computeSalaryParamVO.getPositiveBeforeAttendanceDays()) {
            return ApiResult.getFailedApiResponse("转正前应出勤天数不能为空！");
        }
        // 校验 转正前其他缺勤天数
        if (null == computeSalaryParamVO.getPositiveBeforeOtherAttendanceDays()) {
            computeSalaryParamVO.setPositiveBeforeOtherAttendanceDays(new BigDecimal("0.00"));
        }
        // 校验 转正前病假缺勤天数
        if (null == computeSalaryParamVO.getPositiveBeforeSickAttendanceDays()) {
            computeSalaryParamVO.setPositiveBeforeSickAttendanceDays(new BigDecimal("0.00"));
        }
        // 校验必填参数
        if (null == computeSalaryParamVO.getPositiveAfterAttendanceDays()) {
            return ApiResult.getFailedApiResponse("转正后应出勤天数不能为空！");
        }
        // 校验 转正后其他缺勤天数
        if (null == computeSalaryParamVO.getPositiveAfterOtherAttendanceDays()) {
            computeSalaryParamVO.setPositiveAfterOtherAttendanceDays(new BigDecimal("0.00"));
        }
        // 校验 转正后病假缺勤天数
        if (null == computeSalaryParamVO.getPositiveAfterSickAttendanceDays()) {
            computeSalaryParamVO.setPositiveAfterSickAttendanceDays(new BigDecimal("0.00"));
        }
        // 校验 绩效
        if (null == computeSalaryParamVO.getMonthPerformanceRatio()) {
            computeSalaryParamVO.setMonthPerformanceRatio(new BigDecimal("1.00"));
        }
        try {
            // 业务操作
            return salaryService.lastMonthBecomeCompute(computeSalaryParamVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上月转正员工计薪错误异常：" + e);
            return ApiResult.getFailedApiResponse("上月转正员工计薪出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 13:54 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 正常员工计薪
     **/
    @ApiOperation(value = "正常员工计薪", httpMethod = "POST", notes = "上月转正员工计薪")
    @PostMapping(value = "/lastMonthCompute")
    public synchronized ApiResult lastMonthCompute(@RequestBody ComputeSalaryParamVO computeSalaryParamVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验必填参数
        if (null == computeSalaryParamVO || null == computeSalaryParamVO.getId()) {
            return ApiResult.getFailedApiResponse("该数据ID不能为空！");
        }
        // 校验 其他缺勤天数
        if (null == computeSalaryParamVO.getOtherAbsenceDays()) {
            computeSalaryParamVO.setOtherAbsenceDays(new BigDecimal("0.00"));
        }
        // 校验 病假缺勤天数
        if (null == computeSalaryParamVO.getSickAbsenceDays()) {
            computeSalaryParamVO.setSickAbsenceDays(new BigDecimal("0.00"));
        }
        // 校验 绩效
        if (null == computeSalaryParamVO.getMonthPerformanceRatio()) {
            computeSalaryParamVO.setMonthPerformanceRatio(new BigDecimal("1.00"));
        }
        try {
            // 业务操作
            return salaryService.lastMonthCompute(computeSalaryParamVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("正常员工计薪错误异常：" + e);
            return ApiResult.getFailedApiResponse("正常员工计薪出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:16 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 发起薪资审批流程，根据所传薪资归属部门id
     **/
    @ApiOperation(value = "发起薪资审批流程", httpMethod = "GET", notes = "发起薪资审批流程，根据所传Long类型的salaryDeptId薪资归属部门id && Integer类型的userPostType所选岗位类型，当userPostType==0代表管理岗，否则为技术岗")
    @GetMapping(value = "/startSalaryFlow")
    public ApiResult startSalaryFlow(@RequestParam(value = "salaryDeptId", required = false) Long salaryDeptId, @RequestParam("userPostType") Integer userPostType, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return salaryService.startSalaryFlow(salaryDeptId, userPostType, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发起薪资审批流程错误异常：" + e);
            return ApiResult.getFailedApiResponse("发起薪资审批流程出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:54 2020/9/28
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 查询历史工资单列表
     **/
    @ApiOperation(value = "查询历史工资单列表", httpMethod = "POST", notes = "查询历史工资单列表，默认查上个月")
    @PostMapping(value = "/selectHistorySalaryList")
    public ApiResult selectHistorySalaryList(@RequestBody SalaryHistoryQueryVO salaryHistoryQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return salaryService.selectHistorySalaryList(salaryHistoryQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询历史工资单列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询历史工资单列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:25 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询汇总列表
     **/
    @ApiOperation(value = "查询汇总列表", httpMethod = "GET", notes = "查询汇总列表")
    @GetMapping(value = "/selectCollectListBySalaryDate")
    public ApiResult selectCollectListBySalaryDate(@RequestParam(value = "salaryDate", required = false) Date salaryDate, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验
        if (null == salaryDate) {
            salaryDate = DateUtils.getThisDateLastMonth();
        }
        try {
            // 业务操作
            return salaryService.selectCollectListBySalaryDate(salaryDate, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询汇总列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询汇总列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:58 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 导出工资单，默认导出上月
     **/
    @ApiOperation(value = "导出工资单", httpMethod = "GET", notes = "导出工资单，默认导出上月")
    @GetMapping(value = "/exportSalaryBill")
    public ApiResult exportSalaryBill(@RequestParam(value = "salaryDate", required = false) Date salaryDate, HttpServletRequest request, HttpServletResponse response) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验
        if (null == salaryDate) {
            salaryDate = DateUtils.getThisDateLastMonth();
        }
        try {
            // 业务操作
            salaryService.exportSalaryBill(salaryDate, userSessionVO, response);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出工资单错误异常：" + e);
            return ApiResult.getFailedApiResponse("导出工资单出现错误异常！");
        }
    }
}
