package com.tyzq.salary.service.config;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.SalaryDeptQueryVO;
import com.tyzq.salary.model.vo.base.UserSalaryDeptQueryVO;
import com.tyzq.salary.model.vo.base.UserSalaryDeptSaveVO;
import com.tyzq.salary.model.vo.base.UserSessionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 09:18
 * @Description: //TODO 基础接口
 **/
public interface BaseService {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:56 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据单据类型，获取唯一单据编号
     **/
    String getBillCodeByBillType(int billTypeParam);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:56 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据单据编号，获取唯一流程code
     **/
    String getFlowCodeByApplicationCode(String applicationCode);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:45 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询薪资核算部门列表
     **/
    ApiResult selectSalaryDeptList(SalaryDeptQueryVO salaryDeptQueryVO, HttpServletRequest request);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:41 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 核算人员与部门配置列表
     **/
    ApiResult selectUserSalaryDeptList(UserSalaryDeptQueryVO userSalaryDeptQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:48 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询角色是薪资核算的人员列表
     **/
    ApiResult selectUserComputeRoleList(UserSalaryDeptQueryVO userSalaryDeptQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:19 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 新增用户薪资部门关联数据
     **/
    ApiResult saveUserSalaryDept(UserSalaryDeptSaveVO userSalaryDeptSaveVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:25 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量删除用户薪资部门关联表数据
     **/
    ApiResult deleteUserSalaryDeptByIds(String ids, UserSessionVO userSessionVO);
}