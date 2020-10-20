package com.tyzq.salary.controller.config;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.*;
import com.tyzq.salary.model.*;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.user.*;
import com.tyzq.salary.service.config.UserService;
import com.tyzq.salary.utils.RedisUtil;
import com.tyzq.salary.utils.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProJectName: zhongyi
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-24 11:15
 * @Description: //TODO 用户层
 **/
@Api(tags = "【基础配置】··【用户管理模块】")
@RestController
@RequestMapping(value = "/config/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private UserSalaryDeptMapper userSalaryDeptMapper;

    @Resource
    private UserDetailMapper userDetailMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private RoleMapper roleMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:47 2019/10/25
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 用户登录  先查询是否为管理员，再查询是否为客户
     **/
    @ApiOperation(value = "登录", httpMethod = "POST", notes = "用户登录接口")
    @PostMapping(value = "/login")
    public ApiResult login(@RequestBody UserLoginVO userLoginVO, HttpSession session) {
        // 校验
        if (null == userLoginVO || StringUtils.isBlank(userLoginVO.getAccount()) || StringUtils.isBlank(userLoginVO.getPassword())) {
            return ApiResult.getFailedApiResponse("账号或密码为空！");
        }
        try {
            return userService.login(userLoginVO.getAccount(), userLoginVO.getPassword(), session);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户登录出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("用户登录出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:10 2019/10/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 退出登录
     **/
    @ApiOperation(value = "退出登录", httpMethod = "GET", notes = "用户退出登录接口")
    @GetMapping(value = "/logout")
    public ApiResult logout(HttpServletRequest request) {
        try {
            // 首先获取到token
            String token = SessionUtil.getTokenByAuthorization(request);
            // 删除对象信息
            RedisUtil.del(Constants.ACCESS_TOKEN + token);
            // 清空session
            request.getSession().removeAttribute(Constants.USER_SESSION);
            request.getSession().invalidate();
            return ApiResult.getSuccessApiResponse();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户退出登录出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("用户退出登录出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:13 2020/9/9
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 修改用户基础信息
     **/
    @ApiOperation(value = "修改用户基础信息", httpMethod = "POST", notes = "修改用户基础信息")
    @PostMapping(value = "/updateUserByCondition")
    public ApiResult updateUserByCondition(@RequestBody UserSaveVO userSaveVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验权限是否为总经理/副总/薪资核算角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID) && !roleIdList.contains(Constants.SALARY_DEPT_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权修改员工信息！");
        }
        // 校验
        if (null == userSaveVO) {
            return ApiResult.getFailedApiResponse("参数对象不能为空！");
        }
        User user = userSaveVO.getUser();
        UserDetail userDetail = userSaveVO.getUserDetail();
        // 校验
        if (null == user || null == userDetail) {
            return ApiResult.getFailedApiResponse("用户对象或用户明细不能为空！");
        }
        // 校验
        if (null == user.getId() || StringUtils.isBlank(user.getUserAccount())) {
            return ApiResult.getFailedApiResponse("用户id或用户对象不能为空！");
        }

        // 校验账号及ID的唯一匹配性
        // 查询用户表
        List<User> dataList = userMapper.selectList(Condition.create().eq("id", user.getId()).eq("user_account", user.getUserAccount()));
        // 校验
        if (CollectionUtils.isEmpty(dataList)) {
            return ApiResult.getFailedApiResponse("本次修改的账号与数据ID不匹配！");
        }
        try {
            // 业务操作
            return ApiResult.getSuccessApiResponse(userService.updateUserByCondition(userSaveVO, userSessionVO));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改用户基础信息错误异常：" + e);
            return ApiResult.getFailedApiResponse("修改用户基础信息出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:24 2020/9/18
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据用户ID查询用户信息
     **/
    @ApiOperation(value = "根据用户ID查询用户信息", httpMethod = "GET", notes = "根据用户ID查询用户信息")
    @GetMapping(value = "/getUserInfoById")
    public ApiResult getUserInfoById(@RequestParam("id") Long id, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验
        if (null == id) {
            return ApiResult.getFailedApiResponse("请至少传一条ID！");
        }
        // 查询用户基础表
        User user = userMapper.selectById(id);
        // 查询用户明细表
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(id);
        userDetail.setDeleteFlag(0);
        // 查询
        userDetail = userDetailMapper.selectOne(userDetail);
        // 封装返回
        UserOneResponseVO oneResponseVO = new UserOneResponseVO();
        oneResponseVO.setUser(user);
        oneResponseVO.setUserDetail(userDetail);
        // 查询用户所属的角色
        List<UserRole> userRoleList = userRoleMapper.selectList(Condition.create().eq("user_id", user.getId()));
        // 校验
        if (!CollectionUtils.isEmpty(userRoleList)) {
            // 取出角色id
            List<Long> thisUserRoleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            // 查询角色表
            List<Role> roleList = roleMapper.selectList(Condition.create().in("id", thisUserRoleIdList));
            // 定义ids字符串
            String roleIds = "";
            // 定义角色名称字符串
            String roleNames = "";
            // 取出id集合，用英文逗号分隔
            for (Role role : roleList) {
                roleIds = roleIds + role.getId().intValue();
                roleNames = roleNames + role.getRoleName();
            }
            // 放入返回对象
            oneResponseVO.setRoleIds(roleIds);
            oneResponseVO.setRoleNames(roleNames);
        }
        // 查询业务归属部门名称
        // 校验
        if (null != user.getUserDeptId()) {
            Dept dept = deptMapper.selectById(user.getUserDeptId());
            // 赋值
            oneResponseVO.setDeptName(dept.getDeptName());
        }
        return ApiResult.getSuccessApiResponse(oneResponseVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:44 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 给员工转正或离职
     **/
    @ApiOperation(value = "给员工转正或离职", httpMethod = "POST", notes = "给员工转正或离职")
    @PostMapping(value = "/updateUserRankByCondition")
    public ApiResult updateUserRankByCondition(@RequestBody UpdateUserRankParamVO userRankParamVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验
        if (null == userRankParamVO || null == userRankParamVO.getId()) {
            return ApiResult.getFailedApiResponse("用户ID必传！");
        }
        // 校验在职离职标识合法性
        if (null == userRankParamVO.getUserRankType() || (1 != userRankParamVO.getUserRankType().intValue() && 2 != userRankParamVO.getUserRankType().intValue())) {
            return ApiResult.getFailedApiResponse("员工在职类型不合法！");
        }
        try {
            // 业务操作
            return userService.updateUserRankByCondition(userRankParamVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量修改员工在职状态错误异常：" + e);
            return ApiResult.getFailedApiResponse("批量修改员工在职状态出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:44 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量删除员工，根据所传用户ids字符串，多个用户id用英文逗号分隔
     **/
    @ApiOperation(value = "批量删除员工", httpMethod = "GET", notes = "批量删除员工，根据所传用户ids字符串，多个用户id用英文逗号分隔")
    @GetMapping(value = "/deleteUserByIds")
    public ApiResult deleteUserByIds(@RequestParam("ids") String ids, HttpServletRequest request) {
        // 校验
        if (StringUtils.isBlank(ids)) {
            return ApiResult.getFailedApiResponse("请至少传一条ID！");
        }
        try {
            // 业务操作
            return ApiResult.getSuccessApiResponse(userService.deleteUserByIds(ids, request));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量删除员工错误异常：" + e);
            return ApiResult.getFailedApiResponse("批量删除员工出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:58 2020/9/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 管理岗新增或修改用户信息有id为修改，无id为新增，（用户所属的薪资核算部门必选）
     **/
    @ApiOperation(value = "新增或修改用户信息", httpMethod = "POST", notes = "(总经理/副总权限可新增修改，薪资核算角色只能新增)新增或修改用户信息有id为修改，无id为新增，（用户所属的薪资核算部门必选）")
    @PostMapping(value = "/saveOrUpdateManageUser")
    public ApiResult saveOrUpdateManageUser(@RequestBody UserSaveVO userSaveVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验权限是否为总经理/副总/薪资核算角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID) && !roleIdList.contains(Constants.SALARY_DEPT_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权新增修改员工信息！");
        }
        // 校验参数
        if (null == userSaveVO || null == userSaveVO.getUser() || null == userSaveVO.getUserDetail()) {
            return ApiResult.getFailedApiResponse("用户主表明细表不能为空！");
        }
        // 校验参数
        if (StringUtils.isBlank(userSaveVO.getUser().getUserAccount())) {
            return ApiResult.getFailedApiResponse("用户账号不能为空！");
        }
        // 校验用户所属薪资核算部门
        if (null == userSaveVO.getUser().getSalaryDeptId()) {
            return ApiResult.getFailedApiResponse("用户所属薪资核算部门不能为空！");
        }
        // 校验用户是否有权限修改
        if (null != userSaveVO.getUser().getId() && !roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID)) {
            return ApiResult.getFailedApiResponse("您无权限修改全量用户信息！");
        }
        // 如果是新增，这里校验员工账号是否存在
        if (null == userSaveVO.getUser().getId()) {
            // 查询用户表
            List<User> dataList = userMapper.selectList(Condition.create().eq("user_account", userSaveVO.getUser().getUserAccount()));
            // 校验
            if (!CollectionUtils.isEmpty(dataList)) {
                return ApiResult.getFailedApiResponse("该账号已存在！");
            }
        } else {
            // 修改用户信息
            // 校验账号及ID的唯一匹配性
            // 查询用户表
            List<User> dataList = userMapper.selectList(Condition.create().eq("id", userSaveVO.getUser().getId()).eq("user_account", userSaveVO.getUser().getUserAccount()));
            // 校验
            if (CollectionUtils.isEmpty(dataList)) {
                return ApiResult.getFailedApiResponse("本次修改的账号与数据ID不匹配！");
            }
        }
        try {
            // 业务操作
            return ApiResult.getSuccessApiResponse(userService.saveOrUpdateManageUser(userSaveVO, userSessionVO));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户新增或修改错误异常：" + e);
            return ApiResult.getFailedApiResponse("用户新增或修改出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:19 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询人员列表，支持条查
     **/
    @ApiOperation(value = "查询人员列表", httpMethod = "POST", notes = "查询当前负责人有权限查看的人员列表")
    @PostMapping(value = "/selectUserList")
    public ApiResult selectUserList(@RequestBody UserQueryVO userQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验权限是否为总经理/副总/薪资核算角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID) && !roleIdList.contains(Constants.SALARY_DEPT_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权查看员工信息！");
        }
        // 定义是否为总经理/副总 角色标识
        boolean adminFlag = false;
        if (roleIdList.contains(Constants.ADMIN_ROLE_ID) || roleIdList.contains(Constants.OTHER_ROLE_ID)) {
            adminFlag = true;
        }
        // 校验是否有权限查看
        if (false == adminFlag) {
            // 校验是否是按岗位类型查的，该角色只能看成本岗和技术岗
            if (null != userQueryVO.getUserPostType() && 0 == userQueryVO.getUserPostType()) {
                return ApiResult.getFailedApiResponse("您无权查看管理岗员工信息！");
            } else {
                userQueryVO.setUserPostType(null);
            }
            // 查询登录人是否在薪资管理人员表中
            List<UserSalaryDept> userSalaryDeptList = userSalaryDeptMapper.selectList(Condition.create().eq("user_id", userSessionVO.getId()).eq("delete_flag", 0));
            // 校验
            if (CollectionUtils.isEmpty(userSalaryDeptList)) {
                return ApiResult.getFailedApiResponse("您无权操作员工信息！");
            }
            // 取出登录人所负责的部门id
            List<Long> salaryDeptIdList = userSalaryDeptList.stream().map(UserSalaryDept::getSalaryDeptId).collect(Collectors.toList());
            // 先校验用户是否是按部门id查询的
            if (null != userQueryVO.getUserSalaryDeptId()) {
                // 校验
                if (!salaryDeptIdList.contains(userQueryVO.getUserSalaryDeptId())) {
                    return ApiResult.getFailedApiResponse("您无权查看该部门信息！");
                }
            }
        } else {
            userQueryVO.setUserPostType(0);
        }
        try {
            // 业务操作
            return userService.selectUserList(userQueryVO, adminFlag, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询人员列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询人员列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:43 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询全量人员基础信息列表
     **/
    @ApiOperation(value = "查询全量人员基础信息列表", httpMethod = "POST", notes = "查询全量人员基础信息列表")
    @PostMapping(value = "/selectAllUserList")
    public ApiResult selectAllUserList(@RequestBody UserQueryVO userQueryVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 校验权限是否为总经理/副总/薪资核算角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权查看全量员工信息！");
        }
        try {
            // 业务操作
            return userService.selectAllUserList(userQueryVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询全量人员基础信息列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询全量人员基础信息列表出现错误异常！");
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:53 2020/10/12
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 修改密码
     **/
    @ApiOperation(value = "修改密码", httpMethod = "POST", notes = "修改密码")
    @PostMapping(value = "/updateUserPassword")
    public ApiResult updateUserPassword(@RequestBody UpdatePasswordVO updatePasswordVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return userService.updateUserPassword(updatePasswordVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改密码错误异常：" + e);
            return ApiResult.getFailedApiResponse("修改密码出现错误异常！");
        }
    }
}
