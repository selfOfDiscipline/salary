package com.tyzq.salary.service.cost.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.ProjectMapper;
import com.tyzq.salary.mapper.UserMapper;
import com.tyzq.salary.model.Project;
import com.tyzq.salary.model.vo.cost.ProjectQueryVO;
import com.tyzq.salary.model.vo.cost.UserCostQueryVO;
import com.tyzq.salary.model.vo.cost.UserResultVO;
import com.tyzq.salary.service.cost.ProjectService;
import com.tyzq.salary.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:37 2021/2/2
 * @Description: //TODO 项目管理接口实现类
 **/
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ProjectMapper projectMapper;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 16:53 2021/2/2
     * @Param:
     * @return:
     * @Description: //TODO 查询薪资模块的 基础员工信息列表（用于给项目挂员工）
     **/
    @Override
    public ApiResult selectBaseUserList(UserCostQueryVO userCostQueryVO) {
        // 查询数据库
        PageHelper.startPage(userCostQueryVO.getPageNum(), userCostQueryVO.getPageSize());
        List<UserResultVO> dataList = userMapper.selectBaseUserList(userCostQueryVO);
        PageInfo<UserResultVO> info = new PageInfo<>(dataList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<UserResultVO> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:39 2021/2/3
     * @Param:
     * @return:
     * @Description: //TODO 查询项目列表
     **/
    @Override
    public ApiResult selectProjectList(ProjectQueryVO projectQueryVO) {
        // 定义条件
        Wrapper<Project> wrapper = new EntityWrapper<>();
        // 校验 项目名称
        if (StringUtils.isNotBlank(projectQueryVO.getProjectName())) {
            wrapper.like("project_name", "%" + projectQueryVO.getProjectName() + "%");
        }
        // 校验 业务归属部门id
        if (null != projectQueryVO.getDeptId()) {
            wrapper.eq("dept_id", projectQueryVO.getDeptId());
        }
        // 校验 客户公司id
        if (null != projectQueryVO.getCompanyId()) {
            wrapper.eq("company_id", projectQueryVO.getCompanyId());
        }
        // 校验 项目类型：0--外包，1--总包，2--自研，3--委托制
        if (null != projectQueryVO.getProjectType()) {
            wrapper.eq("project_type", projectQueryVO.getProjectType());
        }
        // 校验 项目状态：0--正常，1--完结，2--废弃
        if (null != projectQueryVO.getProjectStatus()) {
            wrapper.eq("project_status", projectQueryVO.getProjectStatus());
        }
        // 校验 项目开始日期 区间开始
        if (StringUtils.isNotBlank(projectQueryVO.getContractStartDateBegin())) {
            wrapper.ge("contract_start_date", projectQueryVO.getContractStartDateBegin());
        }
        // 校验 项目开始日期 区间结束
        if (StringUtils.isNotBlank(projectQueryVO.getContractStartDateEnd())) {
            // 转换日期并加1天
            wrapper.lt("contract_start_date", DateUtils.stepDaysWithDate(projectQueryVO.getContractStartDateEnd(), "yyyy-MM-dd", 1));
        }
        // 校验 项目结束日期 区间开始
        if (StringUtils.isNotBlank(projectQueryVO.getContractEndDateBegin())) {
            wrapper.ge("contract_end_date", projectQueryVO.getContractEndDateBegin());
        }
        // 校验 项目结束日期 区间结束
        if (StringUtils.isNotBlank(projectQueryVO.getContractEndDateEnd())) {
            // 转换日期并加1天
            wrapper.lt("contract_end_date", DateUtils.stepDaysWithDate(projectQueryVO.getContractEndDateEnd(), "yyyy-MM-dd", 1));
        }
        // 查询数据库
        PageHelper.startPage(projectQueryVO.getPageNum(), projectQueryVO.getPageSize());
        List<Project> dataList = projectMapper.selectList(wrapper);
        PageInfo<Project> info = new PageInfo<>(dataList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<Project> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }
}
