package com.tyzq.salary.service.config.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.MenuRoleMapper;
import com.tyzq.salary.mapper.UserRoleMapper;
import com.tyzq.salary.model.MenuRole;
import com.tyzq.salary.model.UserRole;
import com.tyzq.salary.model.vo.base.RoleMenuSaveVO;
import com.tyzq.salary.model.vo.base.UserRoleSaveVO;
import com.tyzq.salary.service.config.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 09:19
 * @Description: //TODO 角色接口实现类
 **/
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    private MenuRoleMapper menuRoleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:34 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 给角色配权限，根据用户选中的角色ID，以及选中的权限ids，多个权限id用英文逗号分隔
     **/
    @Override
    public ApiResult saveRoleMenuByCondition(RoleMenuSaveVO roleMenuSaveVO, HttpServletRequest request) {
        // 先清空本角色所有权限
        menuRoleMapper.delete(Condition.create().eq("role_id", roleMenuSaveVO.getRoleId().intValue()));
        // 校验用户所传的权限ID
        if (StringUtils.isBlank(roleMenuSaveVO.getMenuIds())) {
            // 为空，代表清空本角色的所有权限 即可
            return ApiResult.getSuccessApiResponse();
        }
        // 按照英文逗号分隔
        List<Long> longIdList = Arrays.asList(roleMenuSaveVO.getMenuIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        // 校验
        if (CollectionUtils.isEmpty(longIdList)) {
            return ApiResult.getSuccessApiResponse();
        }
        // 新增
        longIdList.forEach(s -> menuRoleMapper.insert(new MenuRole() {{setMenuId(s); setRoleId(roleMenuSaveVO.getRoleId());}}));
        return ApiResult.getSuccessApiResponse();
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:46 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 给用户配角色，根据选中的用户ID，以及选中的角色ids，多个角色id用英文逗号分隔
     **/
    @Override
    public ApiResult saveUserRoleByCondition(UserRoleSaveVO userRoleSaveVO, HttpServletRequest request) {
        // 先清空本用户所有角色
        userRoleMapper.delete(Condition.create().eq("user_id", userRoleSaveVO.getUserId().intValue()));
        // 校验用户所传的权限ID
        if (StringUtils.isBlank(userRoleSaveVO.getRoleIds())) {
            // 为空，代表清空本用户的所有角色 即可
            return ApiResult.getSuccessApiResponse();
        }
        // 按照英文逗号分隔
        List<Long> longIdList = Arrays.asList(userRoleSaveVO.getRoleIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        // 校验
        if (CollectionUtils.isEmpty(longIdList)) {
            return ApiResult.getSuccessApiResponse();
        }
        // 新增
        longIdList.forEach(s -> userRoleMapper.insert(new UserRole() {{setRoleId(s); setUserId(userRoleSaveVO.getUserId());}}));
        return ApiResult.getSuccessApiResponse();
    }
}
