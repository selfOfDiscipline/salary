package com.tyzq.salary.service.config;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.RoleMenuSaveVO;
import com.tyzq.salary.model.vo.base.UserRoleSaveVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 09:19
 * @Description: //TODO 角色接口
 **/
public interface RoleService {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:34 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 给角色配权限，根据用户选中的角色ID，以及选中的权限ids，多个权限id用英文逗号分隔
     **/
    ApiResult saveRoleMenuByCondition(RoleMenuSaveVO roleMenuSaveVO, HttpServletRequest request);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:46 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 给用户配角色，根据选中的用户ID，以及选中的角色ids，多个角色id用英文逗号分隔
     **/
    ApiResult saveUserRoleByCondition(UserRoleSaveVO userRoleSaveVO, HttpServletRequest request);
}