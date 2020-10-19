package com.tyzq.salary.service.config.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.*;
import com.tyzq.salary.model.*;
import com.tyzq.salary.model.vo.base.SalaryDeptQueryVO;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.user.*;
import com.tyzq.salary.service.config.UserService;
import com.tyzq.salary.utils.DateUtils;
import com.tyzq.salary.utils.PasswordUtil;
import com.tyzq.salary.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-25 16:53
 * @Description: //TODO 用户接口业务层
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserSalaryDeptMapper userSalaryDeptMapper;

    @Resource
    private SalaryDeptMapper salaryDeptMapper;

    @Resource
    private UserDetailMapper userDetailMapper;

    @Resource
    private RoleMapper roleMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:55 2019/10/25
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  登录
     **/
    @Override
    public ApiResult login(String account, String password, HttpSession session) {
        // 首先根据账号和未删除标识，查询是否存在该用户（账号唯一）
        User user = new User();
        user.setUserAccount(account);
        user.setDeleteFlag(0);
        user = userMapper.selectOne(user);
        // 校验
        if (null == user) {
            return ApiResult.getFailedApiResponse("该账户不存在！");
        }
//        // 校验密码
//        if (!user.getUserPassword().equals(PasswordUtil.getPasswordBySalt(account, password, user.getUserSalt()))) {
//            return ApiResult.getFailedApiResponse("密码错误！");
//        }
        // 封装 UserSessionVO
        UserSessionVO userSessionVO = new UserSessionVO();
        // 获取用户角色
        List<UserRole> userRoleList = userRoleMapper.selectList(Condition.create().eq("user_id", user.getId().intValue()));
        if (!CollectionUtils.isEmpty(userRoleList)) {
            // 获取角色id集合
            List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            // 查询角色表集合
            List<Role> roleList = roleMapper.selectList(Condition.create().in("id", roleIdList).eq("delete_flag", 0));
            // 校验
            if (!CollectionUtils.isEmpty(roleIdList)) {
                // 获取角色Id集合
                roleIdList = roleList.stream().map(Role::getId).collect(Collectors.toList());
                // 获取角色名称集合
                List<String> roleNameList = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
                // 放入session中
                userSessionVO.setRoleIdList(roleIdList);
                userSessionVO.setRoleNameList(roleNameList);
                // 校验是否有 人力资源总监角色，有该角色即允许流程汇总，无该角色则不允许
                if (roleIdList.contains(Constants.FINANCE_ROLE_ID)) {
                    userSessionVO.setAllowCollectFlag(true);
                }
            }
        }
        // 生成token
        String token = PasswordUtil.getTokenWithLogin(user.getUserAccount(), user.getUserSalt(), System.currentTimeMillis());
        userSessionVO.setId(user.getId());
        userSessionVO.setUserAccount(user.getUserAccount());
        userSessionVO.setUserName(user.getUserName());
        userSessionVO.setLastLoginTime(DateUtils.getNowDateString());
        userSessionVO.setTokenKey(token);
        // 放session
        session.setAttribute(Constants.USER_SESSION, userSessionVO);
        // 将对象放redis，时间为120分钟，tokenkey为常量+token
        RedisUtil.set(Constants.ACCESS_TOKEN + token, JSON.toJSONString(userSessionVO), Constants.REDIS_SESSION_SECONDS);
        return ApiResult.getSuccessApiResponse(userSessionVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:32 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 修改用户基础信息
     **/
    @Override
    public Long updateUserByCondition(UserSaveVO userSaveVO, UserSessionVO userSessionVO) {
        User user = userSaveVO.getUser();
        UserDetail userDetail = userSaveVO.getUserDetail();
        // 修改用户基础信息
        // 常量赋值
        user.setEditId(userSessionVO.getUserAccount());
        user.setEditName(userSessionVO.getUserName());
        user.setEditTime(new Date());
        // 清空不允许用户修改的值
        user.setUserPassword(null);
        user.setUserSalt(null);
        user.setSalaryDeptId(null);
        user.setUserPostType(null);
        // 入库
        userMapper.updateById(user);
        // 修改用户明细
        userDetail.setEditId(userSessionVO.getUserAccount());
        userDetail.setEditName(userSessionVO.getUserName());
        userDetail.setEditTime(new Date());
        // 清空不允许用户修改的值
        userDetail.setStandardSalary(null);
        // 修改
        userDetailMapper.updateById(userDetail);
        return user.getId();
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:48 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  批量修改员工在职状态，根据所传员工在职类型（Integer类型的员工在职类型userRankType：0--试用期，1--正式，2--离职）及用户ids字符串，多个用户id用英文逗号分隔
     **/
    @Override
    public ApiResult updateUserRankByCondition(UpdateUserRankParamVO userRankParamVO, UserSessionVO userSessionVO) {
        // 查询用户主表数据
        User user = userMapper.selectById(userRankParamVO.getId());
        // 查询明细数据
        // 查询用户详情数据
        UserDetail userDetail = new UserDetail();
        userDetail.setDeleteFlag(0);
        userDetail.setUserId(userRankParamVO.getId());
        userDetail = userDetailMapper.selectOne(userDetail);
        // 判断员工本次修改类型是离职还是转正
        if (1 == userRankParamVO.getUserRankType().intValue()) {
            // 员工薪资发放比例
            BigDecimal salaryGrantRatio = userRankParamVO.getSalaryGrantRatio() == null ? new BigDecimal("0.00") : userRankParamVO.getSalaryGrantRatio();
            // 赋值 转正后的员工计薪工资
            BigDecimal computeStandardSalary = userDetail.getStandardSalary().multiply(salaryGrantRatio).setScale(2, BigDecimal.ROUND_HALF_UP);
            userDetail.setComputeStandardSalary(computeStandardSalary);
            // 赋值  预设他行代发工资金额=计算后的员工计薪工资 - 预设银行代发工资金额
            userDetail.setOtherBankSalary(computeStandardSalary.subtract(userDetail.getBankSalary()));
            // 绩效占工资比例
            BigDecimal performanceRatio = userDetail.getPerformanceRatio() == null ? new BigDecimal("0.00") : userDetail.getPerformanceRatio();
            // 赋值基本工资
            userDetail.setBaseSalary((new BigDecimal("1.00").subtract(performanceRatio)).multiply(computeStandardSalary));
            // 赋值绩效工资
            userDetail.setPerformanceSalary(performanceRatio.multiply(computeStandardSalary));
            // 员工实际转正日期
            user.setRealityChangeFormalDate(userRankParamVO.getRealityChangeFormalDate());
            // 员工状态
            user.setUserRankType(1);
        } else {
            // 员工实际离职日期
            user.setUserLeaveDate(userRankParamVO.getUserLeaveDate());
            // 员工状态
            user.setUserRankType(2);
        }
        // 用户表修改常量
        user.setEditId(userSessionVO.getUserAccount());
        user.setEditName(userSessionVO.getUserName());
        user.setEditTime(new Date());
        // 修改用户
        userMapper.updateById(user);
        // 用户明细表修改常量
        userDetail.setEditId(userSessionVO.getUserAccount());
        userDetail.setEditName(userSessionVO.getUserName());
        userDetail.setEditTime(new Date());
        // 修改用户明细
        userDetailMapper.updateById(userDetail);
        return ApiResult.getSuccessApiResponse(user.getId());
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:58 2020/9/14
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 
     **/
    @Override
    public String deleteUserByIds(String ids, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        // 按照英文逗号分隔
        List<Long> longIdList = Arrays.asList(ids.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        // 校验
        if (CollectionUtils.isEmpty(longIdList)) {
            return "操作成功！您本次共删除：0条数据！";
        }
        // 修改
        userMapper.update(new User() {{
            setDeleteFlag(1);
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
        }}, Condition.create().in("id", longIdList));
        // 修改用户详情表
        userDetailMapper.update(new UserDetail() {{
            setDeleteFlag(1);
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
        }}, Condition.create().in("user_id", longIdList));
        return "操作成功！您本次共删除：" + longIdList.size() + "条数据！";
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 11:05 2020/9/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 管理岗新增或修改用户信息有id为修改，无id为新增，（用户所属的薪资核算部门必选）
     **/
    @Override
    public Long saveOrUpdateManageUser(UserSaveVO userSaveVO, UserSessionVO userSessionVO) {
        // 获取用户对象
        User user = userSaveVO.getUser();
        // 获取用户明细对象
        UserDetail userDetail = userSaveVO.getUserDetail();
        // 校验新增还是修改
        if (null == user.getId()) {
            // 新增
            // 是否允许登录本系统（总经理/副总  新增的用户才允许登录本系统）
            if (userSessionVO.getRoleIdList().contains(Constants.OTHER_ROLE_ID) || userSessionVO.getRoleIdList().contains(Constants.ADMIN_ROLE_ID)) {
                user.setAllowFlag(1);
            }
            // 获取32位随机用户盐值
            String userSalt = PasswordUtil.randomGenerate(32);
            // 获取32位加密密码
            String passwordBySalt = PasswordUtil.getPasswordBySalt(user.getUserAccount(), "123456", userSalt);
            // 赋值
            user.setUserSalt(userSalt);
            user.setUserPassword(passwordBySalt);
            user.setDeleteFlag(0);
            user.setCreateId(userSessionVO.getUserAccount());
            user.setCreateName(userSessionVO.getUserName());
            user.setCreateTime(new Date());
            user.setEditTime(new Date());
            // 入库
            userMapper.insert(user);
            // 校验是否选择了角色 （单用户会存在多角色）
            if (StringUtils.isNotBlank(userSaveVO.getRoleIds())) {
                // 分隔角色
                List<Long> roleIdList = Arrays.asList(userSaveVO.getRoleIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
                // 校验
                if (!CollectionUtils.isEmpty(roleIdList)) {
                    // 遍历新增用户角色
                    roleIdList.forEach(s -> {
                        userRoleMapper.insert(new UserRole() {{
                            setUserId(user.getId());
                            setRoleId(s);
                        }});
                    });
                }
            }
            // 新增用户明细
            // 常量赋值
            userDetail.setUserId(user.getId());
            userDetail.setUserAccount(user.getUserAccount());
            userDetail.setDeleteFlag(0);
            userDetail.setCreateId(userSessionVO.getUserAccount());
            userDetail.setCreateName(userSessionVO.getUserName());
            userDetail.setCreateTime(new Date());
            userDetail.setEditTime(new Date());
            // 需要计算录入部分
            // 员工标准薪资
            BigDecimal standardSalary = userDetail.getStandardSalary() == null ? new BigDecimal("0.00") : userDetail.getStandardSalary();
            // 员工薪资发放比例
            BigDecimal salaryGrantRatio = userDetail.getSalaryGrantRatio() == null ? new BigDecimal("0.00") : userDetail.getSalaryGrantRatio();
            // 赋值 计算后的员工计薪工资
            BigDecimal computeStandardSalary = standardSalary.multiply(salaryGrantRatio).setScale(2, BigDecimal.ROUND_HALF_UP);
            userDetail.setComputeStandardSalary(computeStandardSalary);
            userDetail.setComputeProbationSalary(computeStandardSalary);
            // 赋值银行代发工资标准，这里校验用户的  标准薪资*薪资发放比例    是否小于预设银行代发工资
            if (computeStandardSalary.compareTo(userDetail.getBankSalary()) == -1) {
                // 小于
                userDetail.setBankSalary(computeStandardSalary);
            }
            // 赋值  预设他行代发工资金额=计算后的员工计薪工资 - 预设银行代发工资金额
            userDetail.setOtherBankSalary(computeStandardSalary.subtract(userDetail.getBankSalary()));

            // 绩效占工资比例
            BigDecimal performanceRatio = userDetail.getPerformanceRatio() == null ? new BigDecimal("0.00") : userDetail.getPerformanceRatio();
            // 赋值基本工资
            userDetail.setBaseSalary((new BigDecimal("1.00").subtract(performanceRatio)).multiply(computeStandardSalary));
            // 赋值绩效工资
            userDetail.setPerformanceSalary(performanceRatio.multiply(computeStandardSalary));
            // 专项扣除部分
            // 子女教育
            BigDecimal childEducation = userDetail.getChildEducation() == null ? new BigDecimal("0.00") : userDetail.getChildEducation();
            // 继续教育
            BigDecimal continueEducation = userDetail.getContinueEducation() == null ? new BigDecimal("0.00") : userDetail.getContinueEducation();
            // 住房贷款利息
            BigDecimal homeLoanInterest = userDetail.getHomeLoanInterest() == null ? new BigDecimal("0.00") : userDetail.getHomeLoanInterest();
            // 住房租金
            BigDecimal homeRents = userDetail.getHomeRents() == null ? new BigDecimal("0.00") : userDetail.getHomeRents();
            // 赡养老人
            BigDecimal supportParents = userDetail.getSupportParents() == null ? new BigDecimal("0.00") : userDetail.getSupportParents();
            // 其他扣除
            BigDecimal otherDeduct = userDetail.getOtherDeduct() == null ? new BigDecimal("0.00") : userDetail.getOtherDeduct();
            // 赋值专项扣除总计金额
            userDetail.setSpecialDeductTotal(childEducation.add(continueEducation).add(homeLoanInterest).add(homeRents).add(supportParents).add(otherDeduct));
            // 赋值病假补偿比例  这里按50%写
            userDetail.setPersonSickStandard(new BigDecimal("0.50"));
            // 入库
            userDetailMapper.insert(userDetail);
            return user.getId();
        } else {
            // 修改
            // 修改赋值
            user.setEditId(userSessionVO.getUserAccount());
            user.setEditName(userSessionVO.getUserName());
            user.setEditTime(new Date());
            // 这里不允许用户修改用户盐值和密码
            user.setUserSalt(null);
            user.setUserPassword(null);
            // 入库
            userMapper.updateById(user);
            // 校验是否选择了角色 （单用户会存在多角色）
            if (StringUtils.isNotBlank(userSaveVO.getRoleIds())) {
                // 将用户所拥有的老角色删掉
                userRoleMapper.delete(Condition.create().eq("user_id", user.getId()));
                // 分隔角色
                List<Long> roleIdList = Arrays.asList(userSaveVO.getRoleIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
                // 校验
                if (!CollectionUtils.isEmpty(roleIdList)) {
                    // 遍历新增用户角色
                    roleIdList.forEach(s -> {
                        userRoleMapper.insert(new UserRole() {{
                            setUserId(user.getId());
                            setRoleId(s);
                        }});
                    });
                }
            }
            // 修改用户详情
            // 常量赋值
            userDetail.setUserId(user.getId());
            userDetail.setUserAccount(user.getUserAccount());
            userDetail.setEditId(userSessionVO.getUserAccount());
            userDetail.setEditName(userSessionVO.getUserName());
            userDetail.setEditTime(new Date());
            // 需要计算录入部分
            // 员工标准薪资
            BigDecimal standardSalary = userDetail.getStandardSalary() == null ? new BigDecimal("0.00") : userDetail.getStandardSalary();
            // 员工薪资发放比例
            BigDecimal salaryGrantRatio = userDetail.getSalaryGrantRatio() == null ? new BigDecimal("0.00") : userDetail.getSalaryGrantRatio();
            // 赋值 计算后的员工计薪工资
            BigDecimal computeStandardSalary = standardSalary.multiply(salaryGrantRatio).setScale(2, BigDecimal.ROUND_HALF_UP);
            userDetail.setComputeStandardSalary(computeStandardSalary);
            userDetail.setComputeProbationSalary(computeStandardSalary);
            // 赋值银行代发工资标准，这里校验用户的  标准薪资*薪资发放比例    是否小于预设银行代发工资
            if (computeStandardSalary.compareTo(userDetail.getBankSalary()) == -1) {
                // 小于
                userDetail.setBankSalary(computeStandardSalary);
            }

            // 赋值  预设他行代发工资金额=计算后的员工计薪工资 - 预设银行代发工资金额
            userDetail.setOtherBankSalary(computeStandardSalary.subtract(userDetail.getBankSalary()));
            // 绩效占工资比例
            BigDecimal performanceRatio = userDetail.getPerformanceRatio() == null ? new BigDecimal("0.00") : userDetail.getPerformanceRatio();
            // 赋值基本工资
            userDetail.setBaseSalary((new BigDecimal("1.00").subtract(performanceRatio)).multiply(computeStandardSalary));
            // 赋值绩效工资
            userDetail.setPerformanceSalary(performanceRatio.multiply(computeStandardSalary));
            // 专项扣除部分
            // 子女教育
            BigDecimal childEducation = userDetail.getChildEducation() == null ? new BigDecimal("0.00") : userDetail.getChildEducation();
            // 继续教育
            BigDecimal continueEducation = userDetail.getContinueEducation() == null ? new BigDecimal("0.00") : userDetail.getContinueEducation();
            // 住房贷款利息
            BigDecimal homeLoanInterest = userDetail.getHomeLoanInterest() == null ? new BigDecimal("0.00") : userDetail.getHomeLoanInterest();
            // 住房租金
            BigDecimal homeRents = userDetail.getHomeRents() == null ? new BigDecimal("0.00") : userDetail.getHomeRents();
            // 赡养老人
            BigDecimal supportParents = userDetail.getSupportParents() == null ? new BigDecimal("0.00") : userDetail.getSupportParents();
            // 其他扣除
            BigDecimal otherDeduct = userDetail.getOtherDeduct() == null ? new BigDecimal("0.00") : userDetail.getOtherDeduct();
            // 赋值专项扣除总计金额
            userDetail.setSpecialDeductTotal(childEducation.add(continueEducation).add(homeLoanInterest).add(homeRents).add(supportParents).add(otherDeduct));
            // 赋值病假补偿比例  这里按50%写
            userDetail.setPersonSickStandard(new BigDecimal("0.50"));
            // 入库
            userDetailMapper.updateById(userDetail);
            return user.getId();
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:48 2020/9/16
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 查询当前薪资核算负责人关联的员工列表
     **/
    @Override
    public ApiResult selectUserList(UserQueryVO userQueryVO, boolean adminFlag, UserSessionVO userSessionVO) {
        // 定义薪资核算部门列表
        List<Long> salaryDeptIdList = null;
        // 校验用户角色  用户角色类型  false--薪资核算人员角色；true--总经理/副总角色
        if (false == adminFlag) {
            // 获取当前核算人员所管理的部门集合
            List<UserSalaryDept> userSalaryDeptList = userSalaryDeptMapper.selectList(Condition.create().eq("user_id", userSessionVO.getId().intValue()).eq("delete_flag", 0));
            salaryDeptIdList = userSalaryDeptList.stream().map(UserSalaryDept::getSalaryDeptId).collect(Collectors.toList());
        } else {
            // 获取所有的薪资核算部门集合
            List<SalaryDept> salaryDeptList = salaryDeptMapper.selectList(Condition.create().eq("delete_flag", 0));
            salaryDeptIdList = salaryDeptList.stream().map(SalaryDept::getId).collect(Collectors.toList());
        }
        if (StringUtils.isNotBlank(userQueryVO.getUserName())) {
            userQueryVO.setUserName("%" + userQueryVO.getUserName() + "%");
        }
        // 查询数据库
        PageHelper.startPage(userQueryVO.getPageNum(), userQueryVO.getPageSize());
        List<UserBaseResultVO> dataList = userMapper.selectUserList(userQueryVO, salaryDeptIdList);
        PageInfo<UserBaseResultVO> info = new PageInfo<>(dataList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<UserBaseResultVO> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:45 2020/9/27
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 查询全量人员基础信息列表
     **/
    @Override
    public ApiResult selectAllUserList(UserQueryVO userQueryVO, UserSessionVO userSessionVO) {
        // 查询数据库
        PageHelper.startPage(userQueryVO.getPageNum(), userQueryVO.getPageSize());
        List<UserBaseResultVO> dataList = userMapper.selectAllUserList(userQueryVO);
        PageInfo<UserBaseResultVO> info = new PageInfo<>(dataList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<UserBaseResultVO> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:40 2020/10/12
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 修改密码
     **/
    @Override
    public ApiResult updateUserPassword(UpdatePasswordVO updatePasswordVO, UserSessionVO userSessionVO) {
// 校验
        if (null == updatePasswordVO) {
            return ApiResult.getFailedApiResponse("参数对象为空！");
        }
        // 校验类别和账号
        if (null == updatePasswordVO.getUpdateFlag() || StringUtils.isBlank(updatePasswordVO.getAccount())) {
            return ApiResult.getFailedApiResponse("账号为空！");
        }
        // 是否为总经理或副总
        boolean adminFlag = false;
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (!CollectionUtils.isEmpty(roleIdList) && (roleIdList.contains(Constants.ADMIN_ROLE_ID) || roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            adminFlag = true;
        }
        // 查询该用户信息
        User user = new User();
        user.setUserAccount(updatePasswordVO.getAccount());
        user.setDeleteFlag(0);
        user = userMapper.selectOne(user);
        // 校验
        if (null == user) {
            return ApiResult.getFailedApiResponse("该用户不存在！");
        }
        // 类别处理
        // updateFlag == 1  （管理员权限）为重置密码，只需要 account 即可，将新生成密码放入返回结果对象
        if (1 == updatePasswordVO.getUpdateFlag().intValue()) {
            // 校验当前登录用户权限
            if (!adminFlag) {
                return ApiResult.getFailedApiResponse("您无权操作！");
            }
            // 生成新salt
            // 获取32位随机用户盐值
            String userSalt = PasswordUtil.randomGenerate(32);
            // 获取32位加密密码
            String passwordBySalt = PasswordUtil.getPasswordBySalt(user.getUserAccount(), "tyzq-123456", userSalt);
            // 修改入库
            user.setUserSalt(userSalt);
            user.setUserPassword(passwordBySalt);
            user.setEditId(user.getUserAccount());
            user.setEditName(user.getUserName());
            user.setEditTime(new Date());
            userMapper.updateById(user);
            // 密码返回
            return ApiResult.getSuccessApiResponse("tyzq-123456");
        }
        // updateFlag == 2  为修改密码，必传【account  oldPassword  newPassword confirmPassword】
        if (2 == updatePasswordVO.getUpdateFlag().intValue()) {
            // 校验
            if (StringUtils.isBlank(updatePasswordVO.getOldPassword()) || StringUtils.isBlank(updatePasswordVO.getNewPassword()) || StringUtils.isBlank(updatePasswordVO.getConfirmPassword())) {
                return ApiResult.getFailedApiResponse("原密码/新密码/确认密码必填！");
            }
            // 密码校验
            // 校验密码
            if (!user.getUserPassword().equals(PasswordUtil.getPasswordBySalt(updatePasswordVO.getAccount(), updatePasswordVO.getOldPassword(), user.getUserSalt()))) {
                return ApiResult.getFailedApiResponse("原密码错误！");
            }
            // 生成新salt
            // 获取32位随机用户盐值
            String userSalt = PasswordUtil.randomGenerate(32);
            // 获取32位加密密码
            String passwordBySalt = PasswordUtil.getPasswordBySalt(user.getUserAccount(), "tyzq-123456", userSalt);
            // 修改入库
            user.setUserSalt(userSalt);
            user.setUserPassword(passwordBySalt);
            user.setEditId(user.getUserAccount());
            user.setEditName(user.getUserName());
            user.setEditTime(new Date());
            userMapper.updateById(user);
            // 密码返回
            return ApiResult.getSuccessApiResponse("tyzq-123456");
        }
        // updateFlag == 3  (管理员权限) 为修改账号并重置密码，必传【account  newAccount】将新生成密码放入返回结果对象
        if (3 == updatePasswordVO.getUpdateFlag().intValue()) {
            // 校验当前登录用户权限
            if (!adminFlag) {
                return ApiResult.getFailedApiResponse("您无权操作！");
            }
            // 校验
            if (StringUtils.isBlank(updatePasswordVO.getNewAccount())) {
                return ApiResult.getFailedApiResponse("新账号必填！");
            }
            // 生成新salt
            // 获取32位随机用户盐值
            String userSalt = PasswordUtil.randomGenerate(32);
            // 获取32位加密密码
            String passwordBySalt = PasswordUtil.getPasswordBySalt(updatePasswordVO.getNewAccount(), "tyzq-123456", userSalt);
            // 修改入库
            user.setUserAccount(updatePasswordVO.getNewAccount());
            user.setUserSalt(userSalt);
            user.setUserPassword(passwordBySalt);
            user.setEditId(user.getUserAccount());
            user.setEditName(user.getUserName());
            user.setEditTime(new Date());
            userMapper.updateById(user);
            // 密码返回
            return ApiResult.getSuccessApiResponse("tyzq-123456");
        }
        return ApiResult.getFailedApiResponse("类别填写有误！");
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 12:29 2020/10/17
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  校验登录人权限----管理员/副总 角色   还是普通用户角色
     **/
    @Override
    public Boolean checkUserSessionAuthrity(UserSessionVO userSessionVO) {
        // 校验

        return null;
    }

    public ApiResult checkUserRoleConfig(UserSessionVO userSessionVO) {
        // 校验是否登录
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("您未登录！");
        }
        // 校验是否配置了角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList)) {
            return ApiResult.getFailedApiResponse("您尚未配置角色！");
        }
        return null;
    }
}
