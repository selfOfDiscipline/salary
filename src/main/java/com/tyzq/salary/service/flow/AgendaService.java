package com.tyzq.salary.service.flow;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.flow.FlowHandleParamVO;
import com.tyzq.salary.model.vo.flow.FlowRecordQueryVO;
import com.tyzq.salary.model.vo.flow.SalaryBillQueryVO;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 20:14
 * @Description: //TODO 待办管理接口
 **/
public interface AgendaService {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:21 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询个人待办列表
     **/
    ApiResult selectPersonAgendaList(FlowRecordQueryVO flowRecordQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:29 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据单据编号，查询该单据对应的所有薪资列表
     **/
    ApiResult getSalaryInfoByApplicationCode(String applicationCode, Integer menuType);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 21:19 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 处理当前节点
     **/
    ApiResult handleThisNode(FlowHandleParamVO handleParamVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 22:29 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 个人发起的流程列表
     **/
    ApiResult selectMineAgendaList(SalaryBillQueryVO salaryBillQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:59 2020/10/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 汇总待办列表
     **/
    ApiResult collectTheMonthSalaryFlow(String ids, UserSessionVO userSessionVO);
}