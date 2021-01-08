package com.tyzq.salary.service.salary;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.salary.ComputeSalaryParamVO;
import com.tyzq.salary.model.vo.salary.SalaryHistoryQueryVO;
import com.tyzq.salary.model.vo.salary.UserComputeSalaryQueryVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-09 09:47
 * @Description: //TODO 薪资实现
 **/
public interface SalaryService {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:29 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 计薪列表查询
     **/
    ApiResult selectUserListBySalaryUser(UserComputeSalaryQueryVO userComputeSalaryQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:24 2020/9/21
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 一键生成该负责人对应员工的本月基础工资单
     **/
    ApiResult generateTheMonthBasePayroll(UserSessionVO userSessionVO, boolean adminFlag);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:00 2020/9/22
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 上月入职员工计薪
     **/
    ApiResult lastMonthIncomeCompute(ComputeSalaryParamVO computeSalaryParamVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:57 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 上月转正员工计薪
     **/
    ApiResult lastMonthBecomeCompute(ComputeSalaryParamVO computeSalaryParamVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 13:58 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 正常员工计薪
     **/
    ApiResult lastMonthCompute(ComputeSalaryParamVO computeSalaryParamVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:24 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 发起薪资审批流程，根据所传薪资归属部门id
     **/
    ApiResult startSalaryFlow(Long salaryDeptId, Integer userPostType, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:04 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询历史工资单列表，默认查上个月
     **/
    ApiResult selectHistorySalaryList(SalaryHistoryQueryVO salaryHistoryQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:44 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询汇总列表
     **/
    ApiResult selectCollectListBySalaryDate(String salaryDate, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:00 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 导出工资单，默认导出上月
     **/
    void exportSalaryBill(SalaryHistoryQueryVO salaryHistoryQueryVO, HttpServletRequest request, HttpServletResponse response);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:36 2020/10/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  驳回后再次发起流程，根据薪资流程记录表id
     **/
    ApiResult updateSalaryFlowById(Long salaryFlowId, UserSessionVO userSessionVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:34 2021/1/8
     * @Param:
     * @return:
     * @Description: //TODO 一键清空所有员工的 年度各个累计金额
     **/
    ApiResult eliminateAllUserTotalMoney(UserSessionVO userSessionVO);
}