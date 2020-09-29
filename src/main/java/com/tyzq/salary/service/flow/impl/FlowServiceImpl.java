package com.tyzq.salary.service.flow.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.BaseFlowConfigDetailMapper;
import com.tyzq.salary.mapper.BaseFlowConfigMapper;
import com.tyzq.salary.model.BaseFlowConfig;
import com.tyzq.salary.model.BaseFlowConfigDetail;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.flow.FlowConfigSaveVO;
import com.tyzq.salary.model.vo.flow.FlowQueryVO;
import com.tyzq.salary.service.flow.FlowService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 15:06
 * @Description: //TODO 流程配置实现类
 **/
@Service
@Transactional
public class FlowServiceImpl implements FlowService {

    @Resource
    private BaseFlowConfigMapper baseFlowConfigMapper;

    @Resource
    private BaseFlowConfigDetailMapper baseFlowConfigDetailMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:18 2020/9/27
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 
     **/
    @Override
    public ApiResult selectFlowList(FlowQueryVO flowQueryVO, UserSessionVO userSessionVO) {
        // 校验权限是否为总经理/副总角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权查看流程配置信息！");
        }
        // 条件构造
        Wrapper wrapper = Condition.create();
        // 校验 流程编号
        if (StringUtils.isNotBlank(flowQueryVO.getFlowEnCode())) {
            wrapper.like("flow_en_code", flowQueryVO.getFlowEnCode());
        }
        // 最后条件 + 倒序排序
        wrapper.eq("use_flag", 0);
        wrapper.eq("delete_flag", 0).orderBy("create_time", false);
        // 分页查询
        Page<BaseFlowConfig> page = new Page<>(flowQueryVO.getPageNum(), flowQueryVO.getPageSize());
        List<BaseFlowConfig> dataList = baseFlowConfigMapper.selectPage(page, wrapper);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<BaseFlowConfig> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(page.getTotal());
        tablePageVO.setPages(page.getPages());
        tablePageVO.setDataList(dataList);
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:32 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO (总经理/副总权限可新增修改)新增或修改流程信息有id为修改，无id为新增
     **/
    @Override
    public ApiResult saveOrUpdateFlowConfig(FlowConfigSaveVO flowConfigSaveVO, UserSessionVO userSessionVO) {
        // 获取对象
        BaseFlowConfig baseFlowConfig = flowConfigSaveVO.getBaseFlowConfig();
        List<BaseFlowConfigDetail> baseFlowConfigDetailList = flowConfigSaveVO.getBaseFlowConfigDetailList();
        // 校验 新增 or 修改
        if (null == baseFlowConfig.getId()) {
            // 新增
            // 常量赋值
            baseFlowConfig.setDeleteFlag(0);
            baseFlowConfig.setCreateId(userSessionVO.getUserAccount());
            baseFlowConfig.setCreateName(userSessionVO.getUserName());
            baseFlowConfig.setCreateTime(new Date());
            baseFlowConfig.setEditTime(new Date());
            // 入库
            baseFlowConfigMapper.insert(baseFlowConfig);
            // 新增明细表
            for (BaseFlowConfigDetail detail : baseFlowConfigDetailList) {
                // 常量赋值
                detail.setBaseFlowConfigId(baseFlowConfig.getId());
                detail.setDeleteFlag(0);
                detail.setCreateId(userSessionVO.getUserAccount());
                detail.setCreateName(userSessionVO.getUserName());
                detail.setCreateTime(new Date());
                detail.setEditTime(new Date());
                // 入库
                baseFlowConfigDetailMapper.insert(detail);
            }
            return ApiResult.getSuccessApiResponse(baseFlowConfig.getId());
        } else {
            // 修改
            // 常量赋值
            baseFlowConfig.setEditId(userSessionVO.getUserAccount());
            baseFlowConfig.setEditName(userSessionVO.getUserName());
            baseFlowConfig.setEditTime(new Date());
            // 修改
            baseFlowConfigMapper.updateById(baseFlowConfig);
            // 修改明细表
            // 先查询出该流程主表关联的所有明细
            List<BaseFlowConfigDetail> oldDetailList = baseFlowConfigDetailMapper.selectList(Condition.create().eq("base_flow_config_id", baseFlowConfig.getId()));
            // 校验
            if (CollectionUtils.isEmpty(oldDetailList)) {
                // 直接新增
                // 新增明细表
                for (BaseFlowConfigDetail detail : baseFlowConfigDetailList) {
                    // 常量赋值
                    detail.setBaseFlowConfigId(baseFlowConfig.getId());
                    detail.setDeleteFlag(0);
                    detail.setCreateId(userSessionVO.getUserAccount());
                    detail.setCreateName(userSessionVO.getUserName());
                    detail.setCreateTime(new Date());
                    detail.setEditTime(new Date());
                    // 入库
                    baseFlowConfigDetailMapper.insert(detail);
                }
                return ApiResult.getSuccessApiResponse(baseFlowConfig.getId());
            }
            // 获取已存在的老id
            List<Long> oldIdList = oldDetailList.stream().map(BaseFlowConfigDetail::getId).collect(Collectors.toList());
            // 遍历新集合，对有id的和无id的分类
            List<BaseFlowConfigDetail> haveIdDetailList = Lists.newArrayList();
            List<BaseFlowConfigDetail> noHaveIdDetailList = Lists.newArrayList();
            for (BaseFlowConfigDetail detail : baseFlowConfigDetailList) {
                if (null == detail.getId()) {
                    noHaveIdDetailList.add(detail);
                } else {
                    haveIdDetailList.add(detail);
                }
            }
            // 获取有id的集合中的id集合
            List<Long> havaIdList = haveIdDetailList.stream().map(BaseFlowConfigDetail::getId).collect(Collectors.toList());
            // 去重
            oldIdList.removeAll(havaIdList);
            // 批量删除
            baseFlowConfigDetailMapper.deleteBatchIds(oldIdList);
            // 循环修改有id的集合
            for (BaseFlowConfigDetail detail : haveIdDetailList) {
                // 常量赋值
                detail.setBaseFlowConfigId(baseFlowConfig.getId());
                detail.setEditId(userSessionVO.getUserAccount());
                detail.setEditName(userSessionVO.getUserName());
                detail.setEditTime(new Date());
                // 入库
                baseFlowConfigDetailMapper.updateById(detail);
            }
            // 循环新增无id的集合
            for (BaseFlowConfigDetail detail : noHaveIdDetailList) {
                // 常量赋值
                detail.setBaseFlowConfigId(baseFlowConfig.getId());
                detail.setDeleteFlag(0);
                detail.setCreateId(userSessionVO.getUserAccount());
                detail.setCreateName(userSessionVO.getUserName());
                detail.setCreateTime(new Date());
                detail.setEditTime(new Date());
                // 入库
                baseFlowConfigDetailMapper.insert(detail);
            }
            return ApiResult.getSuccessApiResponse(baseFlowConfig.getId());
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:03 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量删除流程主表，根据所传流程主表ids字符串，多个流程主表id用英文逗号分隔
     **/
    @Override
    public ApiResult deleteFlowConfigByIds(String ids, UserSessionVO userSessionVO) {
        // 转换id
        // 按照英文逗号分隔
        List<Long> longIdList = Arrays.asList(ids.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        // 批量修改流程主表
        baseFlowConfigMapper.update(new BaseFlowConfig() {{
            setDeleteFlag(1);
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
        }}, Condition.create().in("id", longIdList));
        // 批量修改流程明细表
        baseFlowConfigDetailMapper.update(new BaseFlowConfigDetail() {{
            setDeleteFlag(1);
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
        }}, Condition.create().in("base_flow_config_id", longIdList));
        return ApiResult.getSuccessApiResponse("您本次共成功删除：" + longIdList.size() + "条数据！");
    }
}
