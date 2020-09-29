package com.tyzq.salary.controller.config;

import com.baomidou.mybatisplus.mapper.Condition;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.RoleMapper;
import com.tyzq.salary.mapper.UserRoleMapper;
import com.tyzq.salary.model.Role;
import com.tyzq.salary.model.UserRole;
import com.tyzq.salary.model.vo.base.RoleMenuSaveVO;
import com.tyzq.salary.model.vo.base.RoleVO;
import com.tyzq.salary.model.vo.base.UserRoleSaveVO;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.service.config.RoleService;
import com.tyzq.salary.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 09:17
 * @Description: //TODO 角色控制类
 **/
@Api(tags = "【基础配置】··【角色模块】")
@RestController
@RequestMapping(value = "/config/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:25 2020/9/15
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 给角色配权限，根据用户选中的角色ID，以及选中的权限ids，多个权限id用英文逗号分隔
     **/
    @ApiOperation(value = "给角色配权限", httpMethod = "POST", notes = "给角色配权限，根据用户选中的角色ID，以及选中的权限ids，多个权限id用英文逗号分隔")
    @PostMapping(value = "/saveRoleMenuByCondition")
    public ApiResult saveRoleMenuByCondition(@RequestBody RoleMenuSaveVO roleMenuSaveVO, HttpServletRequest request) {
        // 校验
        if (null == roleMenuSaveVO || null == roleMenuSaveVO.getRoleId()) {
            return ApiResult.getFailedApiResponse("角色ID不能为空！");
        }
        try {
            // 业务操作
            return roleService.saveRoleMenuByCondition(roleMenuSaveVO, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("给角色配权限错误异常：" + e);
            return ApiResult.getFailedApiResponse("给角色配权限出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:42 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 给用户配角色，根据选中的用户ID，以及选中的角色ids，多个角色id用英文逗号分隔
     **/
    @ApiOperation(value = "给用户配角色", httpMethod = "POST", notes = "给用户配角色，根据选中的用户ID，以及选中的角色ids，多个角色id用英文逗号分隔")
    @PostMapping(value = "/saveUserRoleByCondition")
    public ApiResult saveUserRoleByCondition(@RequestBody UserRoleSaveVO userRoleSaveVO, HttpServletRequest request) {
        // 校验
        if (null == userRoleSaveVO || null == userRoleSaveVO.getUserId()) {
            return ApiResult.getFailedApiResponse("用户ID不能为空！");
        }
        try {
            // 业务操作
            return roleService.saveUserRoleByCondition(userRoleSaveVO, request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("给用户配角色错误异常：" + e);
            return ApiResult.getFailedApiResponse("给用户配角色出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:08 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询全量角色结构，当前接口未进行角色等级校验筛选
     **/
    @ApiOperation(value = "查询全量角色结构", httpMethod = "GET", notes = "查询全量角色结构，当前接口未进行角色等级校验筛选")
    @GetMapping(value = "/selectRoleList")
    public ApiResult selectRoleList(HttpServletRequest request) {
        // 获取用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("用户未登录！");
        }
        try {
            // 从缓存获取全量角色集合
            List<RoleVO> allRoleVOList = (List<RoleVO>) RedisUtil.get(Constants.REDIS_ROLE);
            // 校验
            if (CollectionUtils.isEmpty(allRoleVOList)) {
                // 从数据库获取
                List<Role> roleList = roleMapper.selectList(Condition.create().eq("use_flag", 0).eq("delete_flag", 0));
                // 校验
                if (!CollectionUtils.isEmpty(roleList)) {
                    // 转换VO集合
                    List<RoleVO> roleVOList = Lists.newArrayListWithCapacity(roleList.size());
                    roleList.forEach(m -> {
                        RoleVO roleVO = new RoleVO();
                        BeanUtils.copyProperties(m, roleVO);
                        roleVOList.add(roleVO);
                    });
                    // 赋值
                    allRoleVOList = roleVOList;
                    // 将全量角色集合放入缓存24小时
                    RedisUtil.set(Constants.REDIS_ROLE, allRoleVOList, Constants.REDIS_COMMON_SECONDS);
                }
            }
            // 再次校验
            if (CollectionUtils.isEmpty(allRoleVOList)) {
                return ApiResult.getFailedApiResponse("全量角色不存在！");
            }
            // 按照父级节点分组
            Map<Long, List<RoleVO>> rootMap = allRoleVOList.stream().sorted(Comparator.comparing(Role::getSortNum)).collect(Collectors.groupingBy(Role::getPid));
            // 获取到父级根目录节点集合
            List<RoleVO> rootList = allRoleVOList.stream().filter(a -> a.getPid() == 0).sorted(Comparator.comparing(RoleVO::getSortNum)).collect(Collectors.toList());
            // 递归
            putChildrenList(rootList, rootMap);
            return ApiResult.getSuccessApiResponse(rootList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询角色出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询角色出现错误异常！");
        }
    }

    /**
     * 递归生成角色子类
     * @param rootList
     * @param rootMap
     */
    private void putChildrenList(List<RoleVO> rootList, Map<Long, List<RoleVO>> rootMap) {
        rootList.forEach(m -> {
            // 通过根节点id，查看是否有子节点
            List<RoleVO> children = rootMap.get(m.getId());
            if (children != null){
                children = children.stream().sorted(Comparator.comparing(RoleVO::getSortNum)).collect(Collectors.toList());
                m.setChildList(children);
                // 依次递归
                putChildrenList(children, rootMap);
            }
        });
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:20 2020/9/15
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 根据用户id查询其拥有角色并标记
     **/
    @ApiOperation(value = "根据用户id查询其拥有角色并标记", httpMethod = "GET", notes = "根据用户id查询其拥有角色并标记")
    @GetMapping(value = "/selectRoleListByUserId")
    public ApiResult selectRoleListByUserId(@RequestParam("userId") Long userId) {
        try {
            // 从缓存获取全量角色集合
            List<RoleVO> allRoleVOList = (List<RoleVO>) RedisUtil.get(Constants.REDIS_ROLE);
            // 校验
            if (CollectionUtils.isEmpty(allRoleVOList)) {
                // 从数据库获取
                List<Role> roleList = roleMapper.selectList(Condition.create().eq("use_flag", 0).eq("delete_flag", 0));
                // 校验
                if (!CollectionUtils.isEmpty(roleList)) {
                    // 转换VO集合
                    List<RoleVO> roleVOList = Lists.newArrayListWithCapacity(roleList.size());
                    roleList.forEach(m -> {
                        RoleVO roleVO = new RoleVO();
                        BeanUtils.copyProperties(m, roleVO);
                        roleVOList.add(roleVO);
                    });
                    // 赋值
                    allRoleVOList = roleVOList;
                    // 将全量角色集合放入缓存24小时
                    RedisUtil.set(Constants.REDIS_ROLE, allRoleVOList, Constants.REDIS_COMMON_SECONDS);
                }
            }
            // 再次校验
            if (CollectionUtils.isEmpty(allRoleVOList)) {
                return ApiResult.getFailedApiResponse("全量角色不存在！");
            }
            // 获取所传用户的用户角色集合
            List<UserRole> userRoleList = userRoleMapper.selectList(Condition.create().eq("user_id", userId.intValue()));
            // 校验
            if (CollectionUtils.isEmpty(userRoleList)) {
                return ApiResult.getFailedApiResponse("当前用户尚未配置角色！");
            }
            // 获取以上用户角色集合中所有的角色ID集合
            List<Long> userIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            // 在全量角色集合中进行匹配，匹配到的数据将其选中标识置为true
            allRoleVOList.forEach(s -> {
                userIdList.forEach(y -> {
                    if (s.getId().intValue() == y.intValue()) {
                        s.setCheckFlag(true);
                    }
                });
            });
            // 按照父级节点分组
            Map<Long, List<RoleVO>> rootMap = allRoleVOList.stream().sorted(Comparator.comparing(Role::getSortNum)).collect(Collectors.groupingBy(Role::getPid));
            // 获取到父级根目录节点集合
            List<RoleVO> rootList = allRoleVOList.stream().filter(a -> a.getPid() == 0).sorted(Comparator.comparing(RoleVO::getSortNum)).collect(Collectors.toList());
            // 递归
            putChildrenList(rootList, rootMap);
            return ApiResult.getSuccessApiResponse(rootList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询角色出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询角色出现错误异常！");
        }
    }
}
