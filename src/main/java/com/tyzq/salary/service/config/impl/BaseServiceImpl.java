package com.tyzq.salary.service.config.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.*;
import com.tyzq.salary.model.*;
import com.tyzq.salary.model.vo.base.SalaryDeptQueryVO;
import com.tyzq.salary.model.vo.base.UserSalaryDeptQueryVO;
import com.tyzq.salary.model.vo.base.UserSalaryDeptSaveVO;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.service.config.BaseService;
import com.tyzq.salary.utils.DateUtils;
import com.tyzq.salary.utils.PasswordUtil;
import com.tyzq.salary.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 09:18
 * @Description: //TODO 基础接口实现类
 **/
@Service
@Transactional
public class BaseServiceImpl implements BaseService {

    @Resource
    private SalaryDeptMapper salaryDeptMapper;

    @Resource
    private BaseBillRecordMapper baseBillRecordMapper;

    @Resource
    private UserSalaryDeptMapper userSalaryDeptMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private BaseFlowGenerateMapper baseFlowGenerateMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:57 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据单据类型，获取唯一单据编号
     **/
    @Override
    public String getBillCodeByBillType(int billTypeParam) {
        // 先获取当前时间
        String yyyyMMdd = DateUtils.getDateString(new Date(), "yyyyMMdd");
        // 定义单据类型英文编码
        String enCode = "";
        // 定义单据类型名称
        String billType = "";
        // 定义单据前缀
        String billPrefix = "";
        // 定义单据后缀
        String billSuffix = "";

        // 遍历所传类型
        switch (billTypeParam) {
            case 1 :
                enCode = "XZ";
                billType = "薪资审批";
                billPrefix = enCode + yyyyMMdd;
                break;
            default:
                enCode = "FF";
                billType = "非法单据";
                billPrefix = enCode + yyyyMMdd;
                break;
        }
        // 查询单据记录表中，该类型今日最大排序的单据
        List<BaseBillRecord> billRecordList = baseBillRecordMapper.selectList(Condition.create().eq("bill_prefix", billPrefix).eq("en_code", enCode).orderBy("sort_num", false));
        // 校验当日是否已存在该类型单据
        if (CollectionUtils.isEmpty(billRecordList)) {
            // 当日无该类型单据生成
            billSuffix = "0001";
            // 定义新单据并赋值
            BaseBillRecord baseBillRecord = new BaseBillRecord();
            baseBillRecord.setEnCode(enCode);
            baseBillRecord.setBillType(billType);
            baseBillRecord.setBillPrefix(billPrefix);
            baseBillRecord.setBillCode(billPrefix + billSuffix);
            baseBillRecord.setBillSuffix(billSuffix);
            baseBillRecord.setSortNum(1);
            // 入库
            baseBillRecordMapper.insert(baseBillRecord);
            return baseBillRecord.getBillCode();
        }
        // 获取最大排序的单据
        BaseBillRecord billRecord = billRecordList.get(0);
        // 最大单据后缀
        billSuffix = billRecord.getBillSuffix();
        // 转换后缀
        Integer integer = Integer.valueOf(billSuffix);
        integer++;
        // 获取转换后的长度
        int length = integer.toString().length();
        // 遍历length
        switch (length) {
            case 1 :
                billSuffix = "000" + integer;
                break;
            case 2 :
                billSuffix = "00" + integer;
                break;
            case 3 :
                billSuffix = "0" + integer;
                break;
        }
        // 定义新单据并赋值
        BaseBillRecord baseBillRecord = new BaseBillRecord();
        baseBillRecord.setEnCode(enCode);
        baseBillRecord.setBillType(billType);
        baseBillRecord.setBillPrefix(billPrefix);
        baseBillRecord.setBillCode(billPrefix + billSuffix);
        baseBillRecord.setBillSuffix(billSuffix);
        Integer sortNum = baseBillRecord.getSortNum();
        baseBillRecord.setSortNum(sortNum++);
        // 入库
        baseBillRecordMapper.insert(baseBillRecord);
        return baseBillRecord.getBillCode();
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 18:02 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  根据单据编号，获取唯一流程code
     **/
    @Override
    public String getFlowCodeByApplicationCode(String applicationCode) {
        // 获取flowCode
        String flowCode = UUIDUtil.getUUID();
        // 录入流程code生成表
        BaseFlowGenerate baseFlowGenerate = new BaseFlowGenerate();
        baseFlowGenerate.setApplicationCode(applicationCode);
        baseFlowGenerate.setApproverStatus(0);
        baseFlowGenerate.setFlowCode(flowCode);
        // 入库
        baseFlowGenerateMapper.insert(baseFlowGenerate);
        return flowCode;
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:45 2020/9/15
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 查询薪资核算部门列表
     **/
    @Override
    public ApiResult selectSalaryDeptList(SalaryDeptQueryVO salaryDeptQueryVO, HttpServletRequest request) {
        // 条件构造
        Wrapper wrapper = Condition.create();
        // 校验 薪资核算部门名称
        if (StringUtils.isNotBlank(salaryDeptQueryVO.getSalaryDeptName())) {
            wrapper.like("salary_dept_name", salaryDeptQueryVO.getSalaryDeptName());
        }
        // 最后条件 + 倒序排序
        wrapper.eq("delete_flag", 0).orderBy("create_time", false);
        // 分页查询
        Page<SalaryDept> page = new Page<>(salaryDeptQueryVO.getPageNum(), salaryDeptQueryVO.getPageSize());
        List<SalaryDept> dataList = salaryDeptMapper.selectPage(page, wrapper);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<SalaryDept> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(page.getTotal());
        tablePageVO.setPages(page.getPages());
        tablePageVO.setDataList(dataList);
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:41 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 核算人员与部门配置列表
     **/
    @Override
    public ApiResult selectUserSalaryDeptList(UserSalaryDeptQueryVO userSalaryDeptQueryVO, UserSessionVO userSessionVO) {
        // 校验权限是否为总经理/副总角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权查看员工信息！");
        }
        // 条件构造
        Wrapper wrapper = Condition.create();
        // 校验 用户名称
        if (StringUtils.isNotBlank(userSalaryDeptQueryVO.getUserName())) {
            wrapper.like("user_name", userSalaryDeptQueryVO.getUserName());
        }
        // 校验 薪资核算部门名称
        if (StringUtils.isNotBlank(userSalaryDeptQueryVO.getSalaryDeptName())) {
            wrapper.like("salary_dept_name", userSalaryDeptQueryVO.getSalaryDeptName());
        }
        // 最后条件 + 倒序排序
        wrapper.eq("delete_flag", 0).orderBy("create_time", false);
        // 分页查询
        Page<UserSalaryDept> page = new Page<>(userSalaryDeptQueryVO.getPageNum(), userSalaryDeptQueryVO.getPageSize());
        List<UserSalaryDept> dataList = userSalaryDeptMapper.selectPage(page, wrapper);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<UserSalaryDept> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(page.getTotal());
        tablePageVO.setPages(page.getPages());
        tablePageVO.setDataList(dataList);
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:49 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  查询角色是薪资核算的人员列表
     **/
    @Override
    public ApiResult selectUserComputeRoleList(UserSalaryDeptQueryVO userSalaryDeptQueryVO, UserSessionVO userSessionVO) {
        // 校验权限是否为总经理/副总角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权查看员工信息！");
        }
        // 查询用户角色表中角色id为  12 部门薪资核算角色
        List<UserRole> userRoleList = userRoleMapper.selectList(Condition.create().eq("role_id", Constants.SALARY_DEPT_ROLE_ID));
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<User> tablePageVO = new BootstrapTablePageVO<>();
        // 校验
        if (CollectionUtils.isEmpty(userRoleList)) {
            tablePageVO.setTotal(0l);
            tablePageVO.setPages(0l);
            return ApiResult.getSuccessApiResponse(tablePageVO);
        }
        List<Long> userIdList = userRoleList.stream().map(UserRole::getUserId).collect(Collectors.toList());
        // 条件构造
        Wrapper wrapper = Condition.create();
        // 校验 用户名称
        if (StringUtils.isNotBlank(userSalaryDeptQueryVO.getUserName())) {
            wrapper.like("user_name", userSalaryDeptQueryVO.getUserName());
        }
        wrapper.in("id", userIdList);
        // 最后条件 + 倒序排序
        wrapper.eq("delete_flag", 0).orderBy("create_time", false);
        // 分页查询
        Page<User> page = new Page<>(userSalaryDeptQueryVO.getPageNum(), userSalaryDeptQueryVO.getPageSize());
        List<User> dataList = userMapper.selectPage(page, wrapper);
        tablePageVO.setTotal(page.getTotal());
        tablePageVO.setPages(page.getPages());
        tablePageVO.setDataList(dataList);
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:19 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 新增用户薪资部门关联数据
     **/
    @Override
    public ApiResult saveUserSalaryDept(UserSalaryDeptSaveVO userSalaryDeptSaveVO, UserSessionVO userSessionVO) {
        // 先删除
        userSalaryDeptMapper.delete(Condition.create().eq("user_id", userSalaryDeptSaveVO.getUserId()).eq("salary_dept_id", userSalaryDeptSaveVO.getSalaryDeptId()));
        // 新增
        userSalaryDeptMapper.insert(new UserSalaryDept() {{
            setUserId(userSalaryDeptSaveVO.getUserId());
            setUserAccount(userSalaryDeptSaveVO.getUserAccount());
            setUserName(userSalaryDeptSaveVO.getUserName());
            setSalaryDeptId(userSalaryDeptSaveVO.getSalaryDeptId());
            setSalaryDeptName(userSalaryDeptSaveVO.getSalaryDeptName());
            setDeleteFlag(0);
            setCreateId(userSessionVO.getUserAccount());
            setCreateName(userSessionVO.getUserName());
            setCreateTime(new Date());
            setEditTime(new Date());
        }});
        return ApiResult.getSuccessApiResponse();
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:25 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量删除用户薪资部门关联表数据
     **/
    @Override
    public ApiResult deleteUserSalaryDeptByIds(String ids, UserSessionVO userSessionVO) {
        // 校验权限是否为总经理/副总角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权查看员工信息！");
        }
        // 按照英文逗号分隔
        List<Long> longIdList = Arrays.asList(ids.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        // 校验
        if (CollectionUtils.isEmpty(longIdList)) {
            return ApiResult.getSuccessApiResponse("操作成功！您本次共删除：0条数据！");
        }
        // 删除
        userSalaryDeptMapper.deleteBatchIds(longIdList);
        return ApiResult.getSuccessApiResponse("操作成功！您本次共删除：" + longIdList.size() + "条数据！");
    }
}
