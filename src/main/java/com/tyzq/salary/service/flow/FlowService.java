package com.tyzq.salary.service.flow;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.flow.FlowConfigSaveVO;
import com.tyzq.salary.model.vo.flow.FlowQueryVO;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 15:05
 * @Description: //TODO 流程配置接口
 **/
public interface FlowService {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:18 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询所有流程配置列表
     **/
    ApiResult selectFlowList(FlowQueryVO flowQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:32 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO (总经理/副总权限可新增修改)新增或修改流程信息有id为修改，无id为新增
     **/
    ApiResult saveOrUpdateFlowConfig(FlowConfigSaveVO flowConfigSaveVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:03 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量删除流程主表，根据所传流程主表ids字符串，多个流程主表id用英文逗号分隔
     **/
    ApiResult deleteFlowConfigByIds(String ids, UserSessionVO userSessionVO);
}