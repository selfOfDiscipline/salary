package com.tyzq.salary.controller.config;

import com.baomidou.mybatisplus.mapper.Condition;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.MenuMapper;
import com.tyzq.salary.mapper.MenuRoleMapper;
import com.tyzq.salary.mapper.UserRoleMapper;
import com.tyzq.salary.model.Menu;
import com.tyzq.salary.model.MenuRole;
import com.tyzq.salary.model.UserRole;
import com.tyzq.salary.model.vo.base.MenuVO;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.service.config.MenuService;
import com.tyzq.salary.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProJectName: zhongyi
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-28 10:07
 * @Description: //TODO 菜单模块
 **/
@Api(tags = "【基础配置】··【菜单模块】")
@RestController
@RequestMapping(value = "/config/menu")
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private MenuRoleMapper menuRoleMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:20 2019/10/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  查询个人菜单
     **/
    @ApiOperation(value = "查询个人菜单", httpMethod = "GET", notes = "查询当前用户菜单信息")
    @GetMapping(value = "/selectMenuList")
    public ApiResult selectMenuList(HttpServletRequest request) {
        // 获取用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("用户未登录！");
        }
        try {
            // 从缓存获取全量菜单集合
            List<MenuVO> allMenuVOList = (List<MenuVO>) RedisUtil.get(Constants.REDIS_MENU);
            // 校验
            if (CollectionUtils.isEmpty(allMenuVOList)) {
                // 从数据库获取
                List<Menu> menuList = menuMapper.selectList(Condition.create().eq("use_flag", 0).eq("delete_flag", 0));
                // 校验
                if (!CollectionUtils.isEmpty(menuList)) {
                    // 转换VO集合
                    List<MenuVO> thisMenuVOList = Lists.newArrayListWithCapacity(menuList.size());
                    menuList.forEach(m -> {
                        MenuVO menuVO = new MenuVO();
                        menuVO.setId(m.getId());
                        menuVO.setComponent(m.getMenuPath());
                        menuVO.setTitle(m.getMenuName());
                        menuVO.setIcon(m.getMenuIcon());
                        menuVO.setName(m.getMenuRedirect());
                        menuVO.setPath(m.getMenuRedirect());
                        menuVO.setRedirect(m.getMenuPath());
                        menuVO.setPid(m.getPid());
                        menuVO.setFullPath(m.getFullPath());
                        menuVO.setSortNum(m.getSortNum());
                        thisMenuVOList.add(menuVO);
                    });
                    // 赋值
                    allMenuVOList = thisMenuVOList;
                    // 将全量菜单集合放入缓存24小时
                    RedisUtil.set(Constants.REDIS_MENU, allMenuVOList, Constants.REDIS_COMMON_SECONDS);
                }
            }
            // 校验是否存在全量菜单
            if (CollectionUtils.isEmpty(allMenuVOList)) {
                return ApiResult.getFailedApiResponse("全量菜单权限不存在！");
            }
            // 获取当前用户的用户角色集合
            List<UserRole> userRoleList = userRoleMapper.selectList(Condition.create().eq("user_id", userSessionVO.getId().intValue()));
            // 校验
            if (CollectionUtils.isEmpty(userRoleList)) {
                return ApiResult.getFailedApiResponse("您尚未配置角色！");
            }
            // 获取当前用户的所有角色ID集合
            List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            // 获取以上角色集合所配置的角色权限集合
            List<MenuRole> menuRoleList = menuRoleMapper.selectList(Condition.create().in("role_id", roleIdList));
            // 校验
            if (CollectionUtils.isEmpty(menuRoleList)) {
                return ApiResult.getFailedApiResponse("您的角色尚未配置权限！");
            }
            // 获取以上角色集合中所有的权限ID集合
            List<Long> menuIdList = menuRoleList.stream().map(MenuRole::getMenuId).collect(Collectors.toList());
            // 定义当前登录人拥有的权限集合
            List<MenuVO> menuVOList = Lists.newArrayListWithCapacity(menuIdList.size());
            // 在全量菜单权限集合中进行匹配获取
            allMenuVOList.forEach(s -> {
                menuIdList.forEach(y -> {
                    if (s.getId().intValue() == y.intValue()) {
                        menuVOList.add(s);
                    }
                });
            });
            // 校验当前登录人的权限菜单是否存在
            if (CollectionUtils.isEmpty(menuVOList)) {
                return ApiResult.getFailedApiResponse("未能匹配到您的权限！");
            }
            // 按照父级节点分组
            Map<Long, List<MenuVO>> rootMap = menuVOList.stream().sorted(Comparator.comparing(MenuVO::getSortNum)).collect(Collectors.groupingBy(MenuVO::getPid));
            // 获取到父级根目录节点集合
            List<MenuVO> rootList = menuVOList.stream().filter(a -> a.getPid() == 0).sorted(Comparator.comparing(MenuVO::getSortNum)).collect(Collectors.toList());
            // 递归
            putChildrenList(rootList, rootMap);
            return ApiResult.getSuccessApiResponse(rootList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询菜单出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询菜单出现错误异常！");
        }
    }

    /**
     * 递归生成菜单子类
     * @param rootList
     * @param rootMap
     */
    private void putChildrenList(List<MenuVO> rootList, Map<Long, List<MenuVO>> rootMap) {
        rootList.forEach(m -> {
            // 通过根节点id，查看是否有子节点
            List<MenuVO> children = rootMap.get(m.getId());
            if (children != null){
                children = children.stream().sorted(Comparator.comparing(MenuVO::getSortNum)).collect(Collectors.toList());
                m.setChildren(children);
                // 依次递归
                putChildrenList(children, rootMap);
            }
        });
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 13:45 2020/9/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据角色id查询所属菜单权限
     **/
    @ApiOperation(value = "根据角色id查询所属菜单权限", httpMethod = "GET", notes = "根据角色id查询所属菜单权限")
    @GetMapping(value = "/selectMenuListByRoleId")
    public ApiResult selectMenuListByRoleId(@RequestParam("roleId") Long roleId) {
        try {
            // 从缓存获取全量菜单集合
            List<MenuVO> allMenuVOList = (List<MenuVO>) RedisUtil.get(Constants.REDIS_MENU);
            // 校验
            if (CollectionUtils.isEmpty(allMenuVOList)) {
                // 从数据库获取
                List<Menu> menuList = menuMapper.selectList(Condition.create().eq("use_flag", 0).eq("delete_flag", 0));
                // 校验
                if (!CollectionUtils.isEmpty(menuList)) {
                    // 转换VO集合
                    List<MenuVO> thisMenuVOList = Lists.newArrayListWithCapacity(menuList.size());
                    menuList.forEach(m -> {
                        MenuVO menuVO = new MenuVO();
                        menuVO.setId(m.getId());
                        menuVO.setComponent(m.getMenuPath());
                        menuVO.setTitle(m.getMenuName());
                        menuVO.setIcon(m.getMenuIcon());
                        menuVO.setName(m.getMenuRedirect());
                        menuVO.setPath(m.getMenuRedirect());
                        menuVO.setRedirect(m.getMenuPath());
                        menuVO.setPid(m.getPid());
                        menuVO.setFullPath(m.getFullPath());
                        menuVO.setSortNum(m.getSortNum());
                        thisMenuVOList.add(menuVO);
                    });
                    // 赋值
                    allMenuVOList = thisMenuVOList;
                    // 将全量菜单集合放入缓存24小时
                    RedisUtil.set(Constants.REDIS_MENU, allMenuVOList, Constants.REDIS_COMMON_SECONDS);
                }
            }
            // 校验是否存在全量菜单
            if (CollectionUtils.isEmpty(allMenuVOList)) {
                return ApiResult.getFailedApiResponse("全量菜单权限不存在！");
            }
            // 获取以上角色集合所配置的角色权限集合
            List<MenuRole> menuRoleList = menuRoleMapper.selectList(Condition.create().eq("role_id", roleId));
            // 校验
            if (CollectionUtils.isEmpty(menuRoleList)) {
                return ApiResult.getFailedApiResponse("当前角色尚未配置权限！");
            }
            // 获取以上角色集合中所有的权限ID集合
            List<Long> menuIdList = menuRoleList.stream().map(MenuRole::getMenuId).collect(Collectors.toList());
            // 在全量菜单权限集合中进行匹配，匹配到的数据将其选中标识置为true
            allMenuVOList.forEach(s -> {
                menuIdList.forEach(y -> {
                    if (s.getId().intValue() == y.intValue()) {
                        s.setCheckFlag(true);
                    }
                });
            });
            // 按照父级节点分组
            Map<Long, List<MenuVO>> rootMap = allMenuVOList.stream().sorted(Comparator.comparing(MenuVO::getSortNum)).collect(Collectors.groupingBy(MenuVO::getPid));
            // 获取到父级根目录节点集合
            List<MenuVO> rootList = allMenuVOList.stream().filter(a -> a.getPid() == 0).sorted(Comparator.comparing(MenuVO::getSortNum)).collect(Collectors.toList());
            // 递归
            putChildrenList(rootList, rootMap);
            return ApiResult.getSuccessApiResponse(rootList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("根据角色id查询菜单出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("根据角色id查询菜单出现错误异常！");
        }
    }
}
