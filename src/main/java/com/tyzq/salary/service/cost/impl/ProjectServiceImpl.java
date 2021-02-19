package com.tyzq.salary.service.cost.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.ProjectMapper;
import com.tyzq.salary.mapper.ProjectUserMapper;
import com.tyzq.salary.mapper.UserMapper;
import com.tyzq.salary.model.Project;
import com.tyzq.salary.model.ProjectUser;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.ProjectQueryVO;
import com.tyzq.salary.model.vo.cost.ProjectSaveVO;
import com.tyzq.salary.model.vo.cost.UserCostQueryVO;
import com.tyzq.salary.model.vo.cost.UserResultVO;
import com.tyzq.salary.service.cost.ProjectService;
import com.tyzq.salary.utils.DateUtils;
import com.tyzq.salary.utils.PasswordUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private ProjectUserMapper projectUserMapper;

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
        wrapper.eq("delete_flag", 0);
        wrapper.orderBy("create_time", false);
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

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:15 2021/2/4
     * @Param:
     * @return:
     * @Description: //TODO 新增修改项目列表
     **/
    @Override
    public ApiResult saveOrUpdateProject(ProjectSaveVO projectSaveVO, UserSessionVO userSessionVO) {
        // 获取项目对象
        Project project = projectSaveVO.getProject();
        // 校验
        if (null == project) {
            return ApiResult.getFailedApiResponse("项目对象不能为空！");
        }
        // 校验项目归属部门
        if (null == project.getDeptId()) {
            return ApiResult.getFailedApiResponse("项目归属部门不能为空！");
        }
        // 获取项目人员对象集合
        List<ProjectUser> projectUserList = projectSaveVO.getProjectUserList();
        // 判断 新增还是修改
        if (null == project.getId()) {
            // 生成项目编号
            project.setProjectCode(PasswordUtil.getProjectCode("TYZQ-", DateUtils.getDateString(project.getContractStartDate(), "yyyyMMdd"), 16));
            // 直接赋值新增
            project.setDeleteFlag(0);
            project.setCreateId(userSessionVO.getUserAccount());
            project.setCreateName(userSessionVO.getUserName());
            project.setCreateTime(new Date());
            project.setEditTime(new Date());
            // 入库
            projectMapper.insert(project);
            // 校验是否有挂 项目人员
            if (CollectionUtils.isNotEmpty(projectUserList)) {
                // 新增用户
                for (ProjectUser projectUser : projectUserList) {
                    // 赋值基础信息
                    projectUser.setProjectCode(project.getProjectCode());
                    projectUser.setProjectName(project.getProjectName());
                    projectUser.setDeleteFlag(0);
                    projectUser.setCreateId(userSessionVO.getUserAccount());
                    projectUser.setCreateName(userSessionVO.getUserName());
                    projectUser.setCreateTime(new Date());
                    projectUser.setEditTime(new Date());
                    // 入库
                    projectUserMapper.insert(projectUser);
                }
            }
            return ApiResult.getSuccessApiResponse(project.getId());
        } else {
            // 项目修改
            project.setEditId(userSessionVO.getUserAccount());
            project.setEditName(userSessionVO.getUserName());
            project.setEditTime(new Date());
            // 入库
            projectMapper.updateById(project);
            // 先查询该项目目前已关联多少员工
            List<ProjectUser> existProjectUserList = projectUserMapper.selectList(Condition.create().eq("project_code", project.getProjectCode()).eq("delete_flag", 0));
            // 校验 所传用户
            if (CollectionUtils.isEmpty(projectUserList)) {
                // 校验 数据库已存在的
                if (CollectionUtils.isNotEmpty(existProjectUserList)) {
                    // 获取 id集合
                    List<Long> existIdList = existProjectUserList.stream().map(ProjectUser::getId).collect(Collectors.toList());
                    // 将以上数据 逻辑删（逻辑删目的是为了保存记录以后可查看该员工共关联过该项目多少次）
                    projectUserMapper.update(new ProjectUser() {{
                        setDeleteFlag(1);
                        setEditId(userSessionVO.getUserAccount());
                        setEditName(userSessionVO.getUserName());
                        setEditTime(new Date());
                    }}, Condition.create().in("id", existIdList));
                }
            } else {
                // 先校验数据库中是否存在
                if (CollectionUtils.isEmpty(existProjectUserList)) {
                    // 直接新增处理
                    // 新增用户
                    for (ProjectUser projectUser : projectUserList) {
                        // 赋值基础信息
                        projectUser.setProjectCode(project.getProjectCode());
                        projectUser.setProjectName(project.getProjectName());
                        projectUser.setDeleteFlag(0);
                        projectUser.setCreateId(userSessionVO.getUserAccount());
                        projectUser.setCreateName(userSessionVO.getUserName());
                        projectUser.setCreateTime(new Date());
                        projectUser.setEditTime(new Date());
                        // 入库
                        projectUserMapper.insert(projectUser);
                    }
                    return ApiResult.getSuccessApiResponse(project.getId());
                }
                // 获取数据库中数据id集合
                List<Long> existIdList = existProjectUserList.stream().map(ProjectUser::getId).collect(Collectors.toList());
                // 定义 已存在数据的id集合
                List<Long> oldIdList = Lists.newArrayList();
                // 处理数据，并记录老数据id
                for (ProjectUser projectUser : projectUserList) {
                    // 校验id是否相同，有id的是修改，无id的是新增
                    if (null == projectUser.getId()) {
                        // 新增
                        // 赋值基础信息
                        projectUser.setProjectCode(project.getProjectCode());
                        projectUser.setProjectName(project.getProjectName());
                        projectUser.setDeleteFlag(0);
                        projectUser.setCreateId(userSessionVO.getUserAccount());
                        projectUser.setCreateName(userSessionVO.getUserName());
                        projectUser.setCreateTime(new Date());
                        projectUser.setEditTime(new Date());
                        // 入库
                        projectUserMapper.insert(projectUser);
                    } else {
                        // 修改
                        projectUser.setEditId(userSessionVO.getUserAccount());
                        projectUser.setEditName(userSessionVO.getUserName());
                        projectUser.setEditTime(new Date());
                        // 入库
                        projectUserMapper.updateById(projectUser);
                        // 记录到老数据id集合
                        oldIdList.add(projectUser.getId());
                    }
                }
                // 将数据库中id去重
                existIdList.removeAll(oldIdList);
                // 将以上数据 逻辑删（逻辑删目的是为了保存记录以后可查看该员工共关联过该项目多少次）
                projectUserMapper.update(new ProjectUser() {{
                    setDeleteFlag(1);
                    setEditId(userSessionVO.getUserAccount());
                    setEditName(userSessionVO.getUserName());
                    setEditTime(new Date());
                }}, Condition.create().in("id", existIdList));
            }
            return ApiResult.getSuccessApiResponse(project.getId());
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:13 2021/2/5
     * @Param:
     * @return:
     * @Description: //TODO 根据项目id，查询项目详情包含项目所属人员列表
     **/
    @Override
    public ApiResult getProjectInfoById(Long id) {
        // 查项目
        Project project = projectMapper.selectById(id);
        // 校验
        if (null == project) {
            return ApiResult.getFailedApiResponse("该项目不存在！");
        }
        // 查项目用户列表
        List<ProjectUser> projectUserList = projectUserMapper.selectList(Condition.create().eq("project_code", project.getProjectCode()).eq("delete_flag", 0).orderBy("create_time", false));
        // 返回用户详情接口
        return ApiResult.getSuccessApiResponse(new ProjectSaveVO() {{
            setProject(project);
            setProjectUserList(projectUserList);
        }});
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 15:23 2021/2/18
     * @Param:
     * @return:
     * @Description: //TODO 批量删除项目及项目关联人员信息，根据所传项目ids字符串，多个项目id用英文逗号分隔
     **/
    @Override
    public ApiResult deleteProjectByIds(String ids, UserSessionVO userSessionVO) {
        // 按照英文逗号分隔
        List<Long> longIdList = Arrays.asList(ids.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        // 校验
        if (CollectionUtils.isEmpty(longIdList)) {
            return ApiResult.getSuccessApiResponse("操作成功！您本次共删除：0条数据！");
        }
        // 修改项目表
        projectMapper.update(new Project() {{
            setDeleteFlag(1);
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
        }}, Condition.create().in("id", longIdList));
        // 查询项目编号
        List<Project> projectList = projectMapper.selectList(Condition.create().setSqlSelect("project_code").in("id", longIdList));
        // 校验
        if (CollectionUtils.isEmpty(projectList)) {
            return ApiResult.getSuccessApiResponse("操作成功！您本次共删除：" + longIdList.size() + "条数据！");
        }
        // 取出项目编号集合
        List<String> projectCodeList = projectList.stream().map(Project::getProjectCode).collect(Collectors.toList());
        // 修改项目成员表
        projectUserMapper.update(new ProjectUser() {{
            setDeleteFlag(1);
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
        }}, Condition.create().in("project_code", projectCodeList));
        return ApiResult.getSuccessApiResponse("操作成功！您本次共删除：" + longIdList.size() + "条数据！");
    }
}
