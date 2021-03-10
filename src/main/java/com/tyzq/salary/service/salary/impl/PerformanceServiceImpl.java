package com.tyzq.salary.service.salary.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.PerformanceSalaryMapper;
import com.tyzq.salary.mapper.UserMapper;
import com.tyzq.salary.model.PerformanceSalary;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.salary.PerformanceBaseUserVO;
import com.tyzq.salary.model.vo.salary.PerformanceComputeVO;
import com.tyzq.salary.model.vo.salary.PerformanceQueryParamVO;
import com.tyzq.salary.model.vo.salary.UserComputeResultVO;
import com.tyzq.salary.service.salary.PerformanceService;
import com.tyzq.salary.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 9:19 2021/3/5
 * @Description: //TODO 绩效管理模块
 **/
@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PerformanceSalaryMapper performanceSalaryMapper;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:26 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 一键生成上季度员工绩效基础工资单，只能4/7/10/1月份生成
     **/
    @Override
    public ApiResult generateLastQuarterSalary(UserSessionVO userSessionVO) {
        // 当前年
        int thisYear = DateUtils.getThisYear();
        // 当前月份的上个季度
        int thisMonthLastQuarter = DateUtils.getThisMonthLastQuarter();
        // 查询所有 有季度绩效的员工信息  条件：未删除，绩效类型为季度
        List<PerformanceBaseUserVO> baseUserVOList = userMapper.selectAllQuarterUserList();
        // 校验
        if (CollectionUtils.isEmpty(baseUserVOList)) {
            return ApiResult.getFailedApiResponse("未查询到有季度绩效的员工！");
        }
        // 获取用户id集合
        List<Long> userIdList = baseUserVOList.stream().map(PerformanceBaseUserVO::getUserId).collect(Collectors.toList());
        // 查询以上人员本季度 基础绩效单据是否被创建过
        List<PerformanceSalary> oldSalaryList = performanceSalaryMapper.selectList(Condition.create().eq("belong_year", thisYear)
                .eq("belong_quarter", thisMonthLastQuarter).in("user_id", userIdList).eq("delete_flag", 0));
        // 校验
        if (CollectionUtils.isNotEmpty(oldSalaryList)) {
            // 校验条数是否一致
            if (userIdList.size() == oldSalaryList.size()) {
                return ApiResult.getSuccessApiResponse("您关联员工的本月基础工资单已全部生成请勿重复生成！");
            }
            // 匹配 未生成的员工，并重新生成
            // 定义已生成的员工的 id集合
            List<Long> oldIdList = Lists.newArrayListWithCapacity(oldSalaryList.size());
            // 遍历
            baseUserVOList.forEach(user -> {
                oldSalaryList.forEach(salary -> {
                    if (user.getUserId().intValue() == salary.getUserId().intValue()) {
                        // 已生成数据
                        oldIdList.add(salary.getUserId());
                    }
                });
            });
            // 总人数ID 去重
            userIdList.removeAll(oldIdList);
            // 遍历 并生成数据
            for (PerformanceBaseUserVO user : baseUserVOList) {
                for (Long id : userIdList) {
                    // 匹配
                    if (user.getUserId().intValue() == id.intValue()) {
                        // 定义对象并赋值
                        PerformanceSalary salary = new PerformanceSalary();
                        salary.setCreateId(userSessionVO.getUserAccount());
                        salary.setCreateName(userSessionVO.getUserName());
                        salary.setCreateTime(new Date());
                        salary.setEditTime(new Date());
                        salary.setBelongYear(thisYear + "");
                        salary.setBelongQuarter(thisMonthLastQuarter + "");
                        // copy
                        BeanUtils.copyProperties(user, salary);
                        // 绩效工资 = 标准薪资 * 绩效占比 * 3(这里3代表三个月)
                        salary.setPerformanceSalary(user.getStandardSalary().multiply(user.getPerformanceRatio()).multiply(new BigDecimal("3")));
                        // 绩效系数
                        salary.setPerformanceRatio(new BigDecimal("1"));
                        // 入库
                        performanceSalaryMapper.insert(salary);
                    }
                }
            }
            return ApiResult.getSuccessApiResponse("员工上季度绩效基础工资生成完毕！共生成：" + userIdList.size() +"人！");
        }
        // 遍历 并生成数据
        for (PerformanceBaseUserVO user : baseUserVOList) {
            // 定义对象并赋值
            PerformanceSalary salary = new PerformanceSalary();
            salary.setCreateId(userSessionVO.getUserAccount());
            salary.setCreateName(userSessionVO.getUserName());
            salary.setCreateTime(new Date());
            salary.setEditTime(new Date());
            salary.setBelongYear(thisYear + "");
            salary.setBelongQuarter(thisMonthLastQuarter + "");
            // copy
            BeanUtils.copyProperties(user, salary);
            // 绩效工资 = 标准薪资 * 绩效占比 * 3(这里3代表三个月)
            salary.setPerformanceSalary(user.getStandardSalary().multiply(user.getPerformanceRatio()).multiply(new BigDecimal("3")));
            // 绩效系数
            salary.setPerformanceRatio(new BigDecimal("1"));
            // 入库
            performanceSalaryMapper.insert(salary);
        }
        return ApiResult.getSuccessApiResponse("员工上季度绩效基础工资生成完毕！共生成：" + userIdList.size() +"人！");
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:45 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 查询绩效核算列表，可根据用户名，薪资归属部门id，条查
     **/
    @Override
    public ApiResult selectPerformanceComputeList(PerformanceQueryParamVO paramVO) {
        // 当前年
        int thisYear = DateUtils.getThisYear();
        // 当前月份的上个季度
        int thisMonthLastQuarter = DateUtils.getThisMonthLastQuarter();
        // 定义查询条件 ： 分页， 年份， 季度
        Wrapper wrapper = Condition.create().eq("belong_year", thisYear).eq("belong_quarter", thisMonthLastQuarter).eq("pay_wages_flag", 0);
        // 校验用户名
        if (StringUtils.isNotBlank(paramVO.getUserName())) {
            wrapper = wrapper.like("user_name", paramVO.getUserName());
        }
        // 校验是否传了部门
        if (null != paramVO.getUserSalaryDeptId()) {
            // 赋值部门id
            wrapper = wrapper.eq("salary_dept_id", paramVO.getUserSalaryDeptId());
        }
        // 未删除
        wrapper = wrapper.eq("delete_flag", 0);
        // 排序
        wrapper.orderBy("create_time", false);
        // 查询计薪列表
        PageHelper.startPage(paramVO.getPageNum(), paramVO.getPageSize());
        List<PerformanceSalary> performanceSalaryList = performanceSalaryMapper.selectList(wrapper);
        PageInfo<PerformanceSalary> info = new PageInfo<>(performanceSalaryList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<PerformanceSalary> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 13:13 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 季度绩效计算接口，根据绩效系数 & 其他金额增减项
     **/
    @Override
    public ApiResult computePerformance(PerformanceComputeVO computeVO, UserSessionVO userSessionVO) {
        // 校验
        if (null == computeVO.getId()) {
            return ApiResult.getFailedApiResponse("当前数据ID必传！");
        }
        // 当月绩效系数
        BigDecimal performanceRatio = null == computeVO.getPerformanceRatio() ? new BigDecimal("1") : computeVO.getPerformanceRatio();
        BigDecimal quarterRewordsMoney = null == computeVO.getQuarterRewordsMoney() ? BigDecimal.ZERO : computeVO.getQuarterRewordsMoney();
        // 获取当前数据
        PerformanceSalary performanceSalary = performanceSalaryMapper.selectById(computeVO.getId());
        // 计算并赋值
        // 实际应发工资 = 当月绩效系数 * 绩效工资
        BigDecimal shouldSalary = performanceRatio.multiply(performanceSalary.getPerformanceSalary()).add(quarterRewordsMoney);
        // 实际应发工资
        performanceSalary.setShouldRealitySalary(shouldSalary);
        // 赋值系数 本季度奖惩金额
        performanceSalary.setPerformanceRatio(performanceRatio);
        performanceSalary.setQuarterRewordsMoney(quarterRewordsMoney);
        // 目前未发金额
        performanceSalary.setNonRealitySalary(shouldSalary);
        // 基础赋值
        performanceSalary.setEditId(userSessionVO.getUserAccount());
        performanceSalary.setEditName(userSessionVO.getUserName());
        performanceSalary.setEditTime(new Date());
        // 入库
        performanceSalaryMapper.updateById(performanceSalary);
        return ApiResult.getSuccessApiResponse(performanceSalary);
    }
}
