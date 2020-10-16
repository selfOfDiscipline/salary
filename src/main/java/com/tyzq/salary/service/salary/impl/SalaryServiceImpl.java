package com.tyzq.salary.service.salary.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.Condition;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.*;
import com.tyzq.salary.model.*;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.salary.*;
import com.tyzq.salary.model.vo.user.UserDetailVO;
import com.tyzq.salary.service.config.BaseService;
import com.tyzq.salary.service.salary.SalaryService;
import com.tyzq.salary.utils.DateUtils;
import com.tyzq.salary.utils.ExcelExpUtil;
import com.tyzq.salary.utils.RedisUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-09 09:51
 * @Description: //TODO 薪资实现类
 **/
@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {

    private static final Logger logger = LoggerFactory.getLogger(SalaryServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserSalaryDeptMapper userSalaryDeptMapper;

    @Resource
    private UserSalaryMapper userSalaryMapper;

    @Resource
    private SalaryDeptMapper salaryDeptMapper;

    @Resource
    private UserDetailMapper userDetailMapper;

    @Resource
    private SalaryPersonTaxMapper salaryPersonTaxMapper;

    @Resource
    private SalaryNonPersonTaxMapper salaryNonPersonTaxMapper;

    @Resource
    private BaseService baseService;

    @Resource
    private BaseFlowConfigMapper baseFlowConfigMapper;

    @Resource
    private SalaryFlowBillMapper salaryFlowBillMapper;

    @Resource
    private BaseFlowConfigDetailMapper baseFlowConfigDetailMapper;

    @Resource
    private BaseFlowRecordMapper baseFlowRecordMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:29 2020/9/14
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 计薪列表查询
     **/
    @Override
    public ApiResult selectUserListBySalaryUser(UserComputeSalaryQueryVO userComputeSalaryQueryVO, UserSessionVO userSessionVO) {
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
            // 查询登录人是否在薪资管理人员表中
            List<UserSalaryDept> userSalaryDeptList = userSalaryDeptMapper.selectList(Condition.create().eq("user_id", userSessionVO.getId()).eq("delete_flag", 0));
            // 校验
            if (CollectionUtils.isEmpty(userSalaryDeptList)) {
                return ApiResult.getFailedApiResponse("您无权操作员工信息！");
            }
            // 取出登录人所负责的部门id
            List<Long> salaryDeptIdList = userSalaryDeptList.stream().map(UserSalaryDept::getSalaryDeptId).collect(Collectors.toList());
            // 先校验用户是否是按部门id查询的
            if (null != userComputeSalaryQueryVO.getUserSalaryDeptId()) {
                // 校验
                if (!salaryDeptIdList.contains(userComputeSalaryQueryVO.getUserSalaryDeptId())) {
                    return ApiResult.getFailedApiResponse("您无权查看该部门信息！");
                }
            }
            // 校验是否传了部门
            if (null == userComputeSalaryQueryVO.getUserSalaryDeptId()) {
                // 赋值部门id
                userComputeSalaryQueryVO.setUserSalaryDeptId(salaryDeptIdList.get(0));
            }
        } else {
            userComputeSalaryQueryVO.setUserPostType(0);
            // 获取所有的薪资核算部门集合
            List<SalaryDept> salaryDeptList = salaryDeptMapper.selectList(Condition.create().eq("delete_flag", 0));
            // 校验
            if (CollectionUtils.isEmpty(salaryDeptList)) {
                return ApiResult.getFailedApiResponse("基础数据薪资管理部门为空！");
            }
            // 校验是否传了部门
            if (null == userComputeSalaryQueryVO.getUserSalaryDeptId()) {
                // 赋值部门id
                userComputeSalaryQueryVO.setUserSalaryDeptId(salaryDeptList.get(0).getId());
            }
        }
        // 获取当月日期
        userComputeSalaryQueryVO.setThisDateMonth(DateUtils.getThisDateMonth());
        // 获取上月日期
        userComputeSalaryQueryVO.setThisDateLastMonth(DateUtils.getThisDateLastMonth());
        // 查询计薪列表
        PageHelper.startPage(userComputeSalaryQueryVO.getPageNum(), userComputeSalaryQueryVO.getPageSize());
        List<UserComputeResultVO> userComputeResultVOList = userSalaryMapper.selectUserListBySalaryUser(userComputeSalaryQueryVO);
        PageInfo<UserComputeResultVO> info = new PageInfo<>(userComputeResultVOList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<UserComputeResultVO> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:25 2020/9/21
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 一键生成该负责人对应员工的本月基础工资单
     **/
    @Override
    public ApiResult generateTheMonthBasePayroll(UserSessionVO userSessionVO, boolean adminFlag) {
        // 定义薪资核算部门列表
        List<Long> salaryDeptIdList = null;
        // 定义查询岗位类型
        Integer userPostType = null;
        // 校验用户角色  用户角色类型  false--薪资核算人员角色；true--总经理/副总角色
        if (false == adminFlag) {

            // 获取当前核算人员所管理的部门集合
            List<UserSalaryDept> userSalaryDeptList = userSalaryDeptMapper.selectList(Condition.create().eq("user_id", userSessionVO.getId().intValue()).eq("delete_flag", 0));
            salaryDeptIdList = userSalaryDeptList.stream().map(UserSalaryDept::getSalaryDeptId).collect(Collectors.toList());
        } else {
            // 获取所有的薪资核算部门集合
            List<SalaryDept> salaryDeptList = salaryDeptMapper.selectList(Condition.create().eq("delete_flag", 0));
            salaryDeptIdList = salaryDeptList.stream().map(SalaryDept::getId).collect(Collectors.toList());
            userPostType = 0;
        }
        // 查询用户薪资明细列表
        List<UserDetailVO> userDetailList = userMapper.selectUserDetailList(userPostType, salaryDeptIdList);
        // 校验
        if (CollectionUtils.isEmpty(userDetailList)) {
            return ApiResult.getSuccessApiResponse("未查询到您关联的员工信息！");
        }
        // 获取用户id集合
        List<Long> userIdList = userDetailList.stream().map(UserDetailVO::getId).collect(Collectors.toList());
        // 查询以上人员的本月基础工资单是否被创建过
        List<UserSalary> userSalaryList = userSalaryMapper.selectList(Condition.create().eq("salary_date", DateUtils.getThisDateLastMonth()).in("user_id", userIdList).eq("delete_flag", 0));
        // 校验
        if (!CollectionUtils.isEmpty(userSalaryList)) {
            // 校验条数是否一致
            if (userIdList.size() == userSalaryList.size()) {
                return ApiResult.getSuccessApiResponse("您关联员工的本月基础工资单已全部生成请勿重复生成！");
            } else {
                // TODO  匹配未生成的人员并重新生成
                // 定义已经生成基本数据的员工id集合
                List<Long> alreadyIdList = Lists.newArrayListWithCapacity(userSalaryList.size());
                // 遍历
                userDetailList.forEach(userDetailVO -> {
                    userSalaryList.forEach(userSalary -> {
                        if (userDetailVO.getUserId().intValue() == userSalary.getUserId().intValue()) {
                            alreadyIdList.add(userDetailVO.getUserId());
                        }
                    });
                });
                // 去重
                userIdList.removeAll(alreadyIdList);
                // 遍历去重后的用户集合  并重新赋值
                // 获取当前日期的上一个月
                Date thisDate = DateUtils.getThisDateLastMonth();
                // 未生成，这里我们批量生成
                for (UserDetailVO user : userDetailList) {
                    for (Long id : userIdList) {
                        // 匹配
                        if (id.intValue() == user.getUserId().intValue()) {
                            // 定义薪资对象
                            UserSalary userSalary = new UserSalary();
                            // 常量赋值
                            userSalary.setAgainComputeFlag(0);
                            userSalary.setDeleteFlag(0);
                            userSalary.setCreateId(userSessionVO.getUserAccount());
                            userSalary.setCreateName(userSessionVO.getUserName());
                            userSalary.setCreateTime(new Date());
                            userSalary.setEditTime(new Date());
                            // 固定值 赋值
                            userSalary.setUserId(user.getUserId());
                            userSalary.setUserAccount(user.getUserAccount());
                            userSalary.setSalaryDate(thisDate);
                            userSalary.setSalaryDeptId(user.getSalaryDeptId());
                            userSalary.setSalaryDeptName(user.getSalaryDeptName());
                            userSalary.setUserRoleName(user.getRoleName());
                            // 薪资部分 赋值
                            userSalary.setMonthBankSalary(user.getBankSalary());// 本月银行代发工资
                            userSalary.setMonthOtherBankSalary(user.getOtherBankSalary());// 本月他行代发工资
                            userSalary.setMonthBaseSalary(user.getBaseSalary());// 本月基本工资
                            userSalary.setMonthPerformanceSalary(user.getPerformanceSalary());// 本月绩效工资
                            userSalary.setMonthPerformanceRatio(new BigDecimal("1.00"));// 本月绩效比例
                            userSalary.setMonthPostSalary(user.getPostSalary());// 本月岗位工资
                            userSalary.setMonthPostSubsidy(user.getPostSubsidy());// 本月岗位津贴
                            userSalary.setMonthOtherSubsidy(user.getOtherSubsidy());// 本月其他补贴
                            // 新增入库
                            userSalaryMapper.insert(userSalary);
                        }
                    }
                }
                return ApiResult.getSuccessApiResponse("关联员工本月基本数据补充生成完毕！共补充生成：" + userIdList.size() + "人！");
            }
        }
        // 获取当前日期
        Date thisDate = DateUtils.getThisDateLastMonth();
        // 未生成，这里我们批量生成
        for (UserDetailVO user : userDetailList) {
            // 定义薪资对象
            UserSalary userSalary = new UserSalary();
            // 常量赋值
            userSalary.setDeleteFlag(0);
            userSalary.setCreateId(userSessionVO.getUserAccount());
            userSalary.setCreateName(userSessionVO.getUserName());
            userSalary.setCreateTime(new Date());
            userSalary.setEditTime(new Date());
            // 固定值 赋值
            userSalary.setUserId(user.getUserId());
            userSalary.setUserAccount(user.getUserAccount());
            userSalary.setSalaryDate(thisDate);
            userSalary.setSalaryDeptId(user.getSalaryDeptId());
            userSalary.setSalaryDeptName(user.getSalaryDeptName());
            userSalary.setUserRoleName(user.getRoleName());
            // 薪资部分 赋值
            userSalary.setMonthBankSalary(user.getBankSalary());// 本月银行代发工资
            userSalary.setMonthOtherBankSalary(user.getOtherBankSalary());// 本月他行代发工资
            userSalary.setMonthBaseSalary(user.getBaseSalary());// 本月基本工资
            userSalary.setMonthPerformanceSalary(user.getPerformanceSalary());// 本月绩效工资
            userSalary.setMonthPerformanceRatio(new BigDecimal("1.00"));// 本月绩效比例
            userSalary.setMonthPostSalary(user.getPostSalary());// 本月岗位工资
            userSalary.setMonthPostSubsidy(user.getPostSubsidy());// 本月岗位津贴
            userSalary.setMonthOtherSubsidy(user.getOtherSubsidy());// 本月其他补贴
            // 新增入库
            userSalaryMapper.insert(userSalary);
        }
        return ApiResult.getSuccessApiResponse("关联员工本月基本数据生成完毕！共生成：" + userIdList.size() +"人！");
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:00 2020/9/24
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 计算社保公积金，根据标识来区分是否缴纳社保
     **/
    public void computeSocialSecurity (boolean computeFlag, User user, UserSalary userSalary, UserDetail userDetail) {
        // 校验
        if (computeFlag) {
            // 计算社保公积金
            // 养老  计算个人/公司  缴纳金额
            userSalary.setYanglPersonPayMoney(userDetail.getYanglShiyBaseMoney().multiply(userDetail.getYanglPersonRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            userSalary.setYanglCompanyPayMoney(userDetail.getYanglShiyBaseMoney().multiply(userDetail.getYanglCompanyRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 失业  计算个人/公司  缴纳金额
            // 失业 城镇户口缴费，农村户口个人不缴费      户口类型：0--城镇，1--农村
            if (0 == user.getHouseholdType().intValue()) {
                userSalary.setShiyPersonPayMoney(userDetail.getYanglShiyBaseMoney().multiply(userDetail.getShiyPersonRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                userSalary.setShiyPersonPayMoney(new BigDecimal("0.00"));
            }
            userSalary.setShiyCompanyPayMoney(userDetail.getYanglShiyBaseMoney().multiply(userDetail.getShiyCompanyRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 工伤  计算个人/公司  缴纳金额
            userSalary.setGongsPersonPayMoney(userDetail.getYilGongsShengyBaseMoney().multiply(userDetail.getGongsPersonRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            userSalary.setGongsCompanyPayMoney(userDetail.getYilGongsShengyBaseMoney().multiply(userDetail.getGongsCompanyRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 生育  计算个人/公司  缴纳金额
            userSalary.setShengyPersonPayMoney(userDetail.getYilGongsShengyBaseMoney().multiply(userDetail.getShengyPersonRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            userSalary.setShengyCompanyPayMoney(userDetail.getYilGongsShengyBaseMoney().multiply(userDetail.getShengyCompanyRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 医疗  计算个人/公司  缴纳金额
            userSalary.setYilPersonPayMoney(userDetail.getYilGongsShengyBaseMoney().multiply(userDetail.getYilPersonRatio()).setScale(2, BigDecimal.ROUND_HALF_UP).add(userDetail.getYilPersonAddMoney()));
            userSalary.setYilCompanyPayMoney(userDetail.getYilGongsShengyBaseMoney().multiply(userDetail.getYilCompanyRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 公积金  计算个人/公司  缴纳金额
            userSalary.setHousingFundPersonPayTotal(userDetail.getHousingFundBaseMoney().multiply(userDetail.getHousingFundPersonRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            userSalary.setHousingFundCompanyPayTotal(userDetail.getHousingFundBaseMoney().multiply(userDetail.getHousingFundCompanyRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 社保  计算个人/公司承担总计
            userSalary.setSocialSecurityPersonPayTotal(
                    userSalary.getYanglPersonPayMoney()
                            .add(userSalary.getShiyPersonPayMoney())
                            .add(userSalary.getGongsPersonPayMoney())
                            .add(userSalary.getShengyPersonPayMoney())
                            .add(userSalary.getYilPersonPayMoney())
            );
            userSalary.setSocailSecurityCompanyPayTotal(
                    userSalary.getYanglCompanyPayMoney()
                            .add(userSalary.getShiyCompanyPayMoney())
                            .add(userSalary.getGongsCompanyPayMoney())
                            .add(userSalary.getShengyCompanyPayMoney())
                            .add(userSalary.getYilCompanyPayMoney())
            );
            // 社保+公积金   计算个人/公司承担总计
            userSalary.setMonthPersonPayTotal(userSalary.getSocialSecurityPersonPayTotal().add(userSalary.getHousingFundPersonPayTotal()));
            userSalary.setMonthCompanyPayTotal(userSalary.getSocailSecurityCompanyPayTotal().add(userSalary.getHousingFundCompanyPayTotal()));
            // 本月公司+个人的  社保公积金总计
            userSalary.setMonthPersonCompanyPayTotal(userSalary.getMonthPersonPayTotal().add(userSalary.getMonthCompanyPayTotal()));
        } else {
            // 不计社保公积金
            // 将所有社保公积金赋值为0
            // 养老  计算个人/公司  缴纳金额
            userSalary.setYanglPersonPayMoney(new BigDecimal("0.00"));
            userSalary.setYanglCompanyPayMoney(new BigDecimal("0.00"));
            // 失业  计算个人/公司  缴纳金额
            // 失业 城镇户口缴费，农村户口个人不缴费      户口类型：0--城镇，1--农村
            userSalary.setShiyPersonPayMoney(new BigDecimal("0.00"));
            userSalary.setShiyCompanyPayMoney(new BigDecimal("0.00"));
            // 工伤  计算个人/公司  缴纳金额
            userSalary.setGongsPersonPayMoney(new BigDecimal("0.00"));
            userSalary.setGongsCompanyPayMoney(new BigDecimal("0.00"));
            // 生育  计算个人/公司  缴纳金额
            userSalary.setShengyPersonPayMoney(new BigDecimal("0.00"));
            userSalary.setShengyCompanyPayMoney(new BigDecimal("0.00"));
            // 医疗  计算个人/公司  缴纳金额
            userSalary.setYilPersonPayMoney(new BigDecimal("0.00"));
            userSalary.setYilCompanyPayMoney(new BigDecimal("0.00"));
            // 公积金  计算个人/公司  缴纳金额
            userSalary.setHousingFundPersonPayTotal(new BigDecimal("0.00"));
            userSalary.setHousingFundCompanyPayTotal(new BigDecimal("0.00"));
            // 社保  计算个人/公司承担总计
            userSalary.setSocialSecurityPersonPayTotal(new BigDecimal("0.00"));
            userSalary.setSocailSecurityCompanyPayTotal(new BigDecimal("0.00"));
            // 社保+公积金   计算个人/公司承担总计
            userSalary.setMonthPersonPayTotal(new BigDecimal("0.00"));
            userSalary.setMonthCompanyPayTotal(new BigDecimal("0.00"));
            // 本月公司+个人的  社保公积金总计
            userSalary.setMonthPersonCompanyPayTotal(new BigDecimal("0.00"));
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:34 2020/9/22
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据所传社保开始缴纳日期与上月日期比较，来判断上月工资是否需要缴纳社保
     **/
    private boolean checkSocialFlag (Date startSocialDate) {
        String socialDate = DateUtils.getDateString(startSocialDate, "yyyy-MM");
        String dateLastMonth = DateUtils.getDateString(DateUtils.getThisDateLastMonth(), "yyyy-MM");
        // 校验
        if (dateLastMonth.compareTo(socialDate) > -1) {
            return true;
        }
        return false;
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:00 2020/9/22
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 上月入职员工计薪
     **/
    @Override
    public ApiResult lastMonthIncomeCompute(ComputeSalaryParamVO computeSalaryParamVO, UserSessionVO userSessionVO) {
        // 获取个人工资类所得税比例表数据
        // 从缓存
        List<SalaryPersonTax> salaryPersonTaxList = (List<SalaryPersonTax>) RedisUtil.get(Constants.REDIS_SALARY_PERSON_TAX);
        // 校验
        if (CollectionUtils.isEmpty(salaryPersonTaxList)) {
            // 从数据库
            salaryPersonTaxList = salaryPersonTaxMapper.selectList(null);
            // 校验
            if (!CollectionUtils.isEmpty(salaryPersonTaxList)) {
                // 放入缓存 1天
                RedisUtil.set(Constants.REDIS_SALARY_PERSON_TAX, salaryPersonTaxList, Constants.REDIS_COMMON_SECONDS);
            }
        }
        // 再次校验
        if (CollectionUtils.isEmpty(salaryPersonTaxList)) {
            return ApiResult.getFailedApiResponse("工资类个税基础数据为空！");
        }
        // 获取个人非工资类所得税比例表数据
        // 从缓存
        List<SalaryNonPersonTax> salaryNonPersonTaxList = (List<SalaryNonPersonTax>) RedisUtil.get(Constants.REDIS_SALARY_NON_PERSON_TAX);
        // 校验
        if (CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
            // 从数据库
            salaryNonPersonTaxList = salaryNonPersonTaxMapper.selectList(null);
            // 校验
            if (!CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
                // 放入缓存 1天
                RedisUtil.set(Constants.REDIS_SALARY_NON_PERSON_TAX, salaryNonPersonTaxList, Constants.REDIS_COMMON_SECONDS);
            }
        }
        // 再次校验
        if (CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
            return ApiResult.getFailedApiResponse("非工资类个税基础数据为空！");
        }
        // 查询到基础薪资数据
        UserSalary userSalary = userSalaryMapper.selectById(computeSalaryParamVO.getId());
        // 查询用户数据
        User user = userMapper.selectById(userSalary.getUserId());
        // 查询用户详情数据
        UserDetail userDetail = new UserDetail();
        userDetail.setDeleteFlag(0);
        userDetail.setUserId(userSalary.getUserId());
        userDetail = userDetailMapper.selectOne(userDetail);
        // ========先计算社保部分======
        computeSocialSecurity(computeSalaryParamVO.isComputeSocialFlag(), user, userSalary, userDetail);
        // 计算
        // PS:上月入职员工无绩效
        // 本月出勤工资 = （员工标准薪资*薪资发放比例/21.75）*出勤天数
        BigDecimal theDecimal = userDetail.getComputeStandardSalary().divide(Constants.STANDARD_SALARY_RATIO, 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal theMonthAttendanceSalary = theDecimal.multiply(computeSalaryParamVO.getNewEntryAttendanceDays()).setScale(2, BigDecimal.ROUND_HALF_UP);

        // 赋值本月基本工资
        userSalary.setMonthBaseSalary(theMonthAttendanceSalary);

        // 计算个税  这里要先判断  【本月出勤工资 >= 预设银行代发工资】
        // 接上：大于等于的话，1.拿 【预设银行代发工资】去计算银行代发工资个税，2.拿本月出勤工资 - 去计算他行代发工资个税
        // 接上：小于的话，不计算个税
        if (theMonthAttendanceSalary.compareTo(userDetail.getBankSalary()) > -1) {
            // 本月出勤工资 >= 预设银行代发工资
            // 第一：拿 【预设银行代发工资】去计算银行代发工资个税
            // 公式：本月银行代发工资个税 = （（预设银行代发工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额） + 本年度累计应纳税所得额）* 所在工资类区间税率 - 该区间国家减免税额 - 本年度累计已纳税额
            // 本月应纳税所得额 = 预设银行代发工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额
            BigDecimal theNeedMoney = userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getStipulationStartTaxMoney()).subtract(userDetail.getSpecialDeductTotal());
            // 校验是否大于0元，大于0元才需要计税
            if (theNeedMoney.compareTo(new BigDecimal("0.00")) == 1) {
                // 本月待计算应纳税所得额 = 本月应纳税所得额 + 本年度累计应纳税所得额
                BigDecimal theMonthSelfMoney = theNeedMoney.add(userDetail.getTotalTaxableSelfMoney());
                // 判断所在区间并计算
                for (SalaryPersonTax salaryPersonTax : salaryPersonTaxList) {
                    // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                    // 接上：是的时候，只判断当前钱数是否大于开始钱数
                    // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (0 == salaryPersonTax.getMaxTaxFlag().intValue()) {
                        // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1 && theMonthSelfMoney.compareTo(salaryPersonTax.getEndMoney()) < 1) {
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    } else {
                        // 是：只判断当前钱数是否大于开始钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1) {
                            // 大于
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    }
                }
            } else {
                // 小于等于0元，不需要计税
                // 赋值
                // 本月薪资表赋值
                // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金
                userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
//                // 用户详情表赋值
//                // 赋值 年度累计收入金额 = 年度累计收入金额 + 预设银行代发工资金额
//                userDetail.setTotalIncomeMoney(userDetail.getTotalIncomeMoney().add(userDetail.getBankSalary()));
//                // 赋值 年度累计减除费用金额 = 年度累计减除费用金额 + 国家纳税起步金额
//                userDetail.setTotalDeductMoney(userDetail.getTotalDeductMoney().add(userDetail.getStipulationStartTaxMoney()));
//                // 赋值 年度累计专项扣除金额 = 年度累计专项扣除金额 + 本月专项扣除的总金额
//                userDetail.setTotalSpecialDeductMoney(userDetail.getTotalSpecialDeductMoney().add(userDetail.getSpecialDeductTotal()));
//                // 赋值 年度累计子女教育扣除金额 = 年度累计子女教育扣除金额 + 本月子女教育扣除金额
//                userDetail.setTotalChildEducation(userDetail.getTotalChildEducation().add(userDetail.getChildEducation()));
//                // 赋值 年度累计继续教育扣除金额 = 年度累计继续教育扣除金额 + 本月继续教育扣除金额
//                userDetail.setTotalContinueEducation(userDetail.getTotalContinueEducation().add(userDetail.getContinueEducation()));
//                // 赋值 年度累计住房贷款利息扣除金额 = 年度累计住房贷款利息扣除金额 + 本月住房贷款利息扣除金额
//                userDetail.setTotalHomeLoanInterest(userDetail.getTotalHomeLoanInterest().add(userDetail.getHomeLoanInterest()));
//                // 赋值 年度累计住房公积金扣除金额 = 年度累计住房公积金扣除金额 + 本月住房公积金扣除金额
//                userDetail.setTotalHomeRents(userDetail.getTotalHomeRents().add(userDetail.getHomeRents()));
//                // 赋值 年度累计赡养老人扣除金额 = 年度累计赡养老人扣除金额 + 本月赡养老人扣除金额
//                userDetail.setTotalSupportParents(userDetail.getTotalSupportParents().add(userDetail.getSupportParents()));
//                // 赋值 年度累计其他款项扣除金额 = 年度累计其他款项扣除金额 + 本月其他款项扣除金额
//                userDetail.setTotalOtherDeduct(userDetail.getTotalOtherDeduct().add(userDetail.getOtherDeduct()));
            }
            // 第二：本月他行代发工资计算
            // 公式：他行个人需缴纳税额 = （（本月出勤工资 - 个人社保公积金 - 五项专项扣除合计 - 纳税起步金额） * 所在非工资类区间税率 - 该区间国家减免税额 - 银行代发部分本月缴纳税额）/2
            // 本月他行代发工资税前金额 = 本月出勤工资 - 预设银行代发工资金额
            BigDecimal monthOtherBankSalary = theMonthAttendanceSalary.subtract(userDetail.getBankSalary());
            // 他行代发工资应纳税所得额 = 本月出勤工资 - 个人社保公积金 - 五项专项扣除合计 - 纳税起步金额
            BigDecimal theMonthOtherBankSelfMoney = theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getSpecialDeductTotal()).subtract(userDetail.getStipulationStartTaxMoney());
            // 判断所在区间并计算
            for (SalaryNonPersonTax nonPersonTax : salaryNonPersonTaxList) {
                // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                // 接上：是的时候，只判断当前钱数是否大于开始钱数
                // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                if (0 == nonPersonTax.getMaxTaxFlag().intValue()) {
                    // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getStartMoney()) == 1 && theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getEndMoney()) < 1) {
                        // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                        BigDecimal theMultiplyMoney = theMonthOtherBankSelfMoney.multiply(nonPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本月应缴税额 = (计算税率相乘后金额 - 该区间国家减免税额 - 银行代发工资本月缴纳税额)/2
                        BigDecimal subtract = theMultiplyMoney.subtract(nonPersonTax.getDeductMoney()).subtract(userSalary.getBankRealityShouldTaxMoney());
                        BigDecimal divide = subtract.divide(new BigDecimal("2.00"), 2, BigDecimal.ROUND_HALF_UP);
                        // 赋值 他行代发部分个税
                        userSalary.setOtherBankShouldTaxMoney(divide);
                        // 赋值 他行实发小计
                        userSalary.setOtherBankRealitySalary(monthOtherBankSalary.subtract(divide));
                        // 赋值 本月总工资实发总计
                        userSalary.setMonthSalaryRealityTotal(userSalary.getBankRealitySalary().add(userSalary.getOtherBankRealitySalary()));
                        // 跳出循环
                        break;
                    }
                } else {
                    // 是：只判断当前钱数是否大于开始钱数
                    if (theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getStartMoney()) == 1) {
                        // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                        BigDecimal theMultiplyMoney = theMonthOtherBankSelfMoney.multiply(nonPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本月应缴税额 = (计算税率相乘后金额 - 该区间国家减免税额 - 银行代发工资本月缴纳税额)/2
                        BigDecimal subtract = theMultiplyMoney.subtract(nonPersonTax.getDeductMoney()).subtract(userSalary.getBankRealityShouldTaxMoney());
                        BigDecimal divide = subtract.divide(new BigDecimal("2.00"), 2, BigDecimal.ROUND_HALF_UP);
                        // 赋值 他行代发部分个税
                        userSalary.setOtherBankShouldTaxMoney(divide);
                        // 赋值 他行实发小计
                        userSalary.setOtherBankRealitySalary(monthOtherBankSalary.subtract(divide));
                        // 赋值 本月总工资实发总计
                        userSalary.setMonthSalaryRealityTotal(userSalary.getBankRealitySalary().add(userSalary.getOtherBankRealitySalary()));
                        // 跳出循环
                        break;
                    }
                }
            }
        } else {
            // 本月出勤工资 < 预设银行代发工资
            // 第一：拿 【本月出勤工资】去计算银行代发工资个税
            // 公式：本月银行代发工资个税 = （（本月出勤工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额） + 本年度累计应纳税所得额）* 所在工资类区间税率 - 该区间国家减免税额 - 本年度累计已纳税额
            // 本月应纳税所得额 = 本月出勤工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额
            BigDecimal theNeedMoney = theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getStipulationStartTaxMoney()).subtract(userDetail.getSpecialDeductTotal());
            // 校验是否大于0元，大于0元才需要计税
            if (theNeedMoney.compareTo(new BigDecimal("0.00")) == 1) {
                // 本月待计算应纳税所得额 = 本月应纳税所得额 + 本年度累计应纳税所得额
                BigDecimal theMonthSelfMoney = theNeedMoney.add(userDetail.getTotalTaxableSelfMoney());
                // 判断所在区间并计算
                for (SalaryPersonTax salaryPersonTax : salaryPersonTaxList) {
                    // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                    // 接上：是的时候，只判断当前钱数是否大于开始钱数
                    // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (0 == salaryPersonTax.getMaxTaxFlag().intValue()) {
                        // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1 && theMonthSelfMoney.compareTo(salaryPersonTax.getEndMoney()) < 1) {
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    } else {
                        // 是：只判断当前钱数是否大于开始钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1) {
                            // 大于
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    }
                }
            } else {
                // 小于等于0元，不需要计税
                // 赋值
                // 本月薪资表赋值
                // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金
                userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
            }
        }
        // 操作数据库
        // 将用户本月薪资表更新
        userSalary.setNewEntryAttendanceDays(computeSalaryParamVO.getNewEntryAttendanceDays());// 赋值上月出勤天数
        userSalary.setCurrentComputeFlag(1);// 给本月是否计算过标识赋值为 本月已计算过。
        // 将用户本月薪资表更新
        userSalaryMapper.updateById(userSalary);
        return ApiResult.getSuccessApiResponse(userSalary);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:57 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 上月转正员工计薪
     **/
    @Override
    public ApiResult lastMonthBecomeCompute(ComputeSalaryParamVO computeSalaryParamVO, UserSessionVO userSessionVO) {
        // 获取个人工资类所得税比例表数据
        // 从缓存
        List<SalaryPersonTax> salaryPersonTaxList = (List<SalaryPersonTax>) RedisUtil.get(Constants.REDIS_SALARY_PERSON_TAX);
        // 校验
        if (CollectionUtils.isEmpty(salaryPersonTaxList)) {
            // 从数据库
            salaryPersonTaxList = salaryPersonTaxMapper.selectList(null);
            // 校验
            if (!CollectionUtils.isEmpty(salaryPersonTaxList)) {
                // 放入缓存 1天
                RedisUtil.set(Constants.REDIS_SALARY_PERSON_TAX, salaryPersonTaxList, Constants.REDIS_COMMON_SECONDS);
            }
        }
        // 再次校验
        if (CollectionUtils.isEmpty(salaryPersonTaxList)) {
            return ApiResult.getFailedApiResponse("工资类个税基础数据为空！");
        }
        // 获取个人非工资类所得税比例表数据
        // 从缓存
        List<SalaryNonPersonTax> salaryNonPersonTaxList = (List<SalaryNonPersonTax>) RedisUtil.get(Constants.REDIS_SALARY_NON_PERSON_TAX);
        // 校验
        if (CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
            // 从数据库
            salaryNonPersonTaxList = salaryNonPersonTaxMapper.selectList(null);
            // 校验
            if (!CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
                // 放入缓存 1天
                RedisUtil.set(Constants.REDIS_SALARY_NON_PERSON_TAX, salaryNonPersonTaxList, Constants.REDIS_COMMON_SECONDS);
            }
        }
        // 再次校验
        if (CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
            return ApiResult.getFailedApiResponse("非工资类个税基础数据为空！");
        }
        // 查询到基础薪资数据
        UserSalary userSalary = userSalaryMapper.selectById(computeSalaryParamVO.getId());
        // 查询用户数据
        User user = userMapper.selectById(userSalary.getUserId());
        // 查询用户详情数据
        UserDetail userDetail = new UserDetail();
        userDetail.setDeleteFlag(0);
        userDetail.setUserId(userSalary.getUserId());
        userDetail = userDetailMapper.selectOne(userDetail);
        // ========先计算社保部分======
        computeSocialSecurity(computeSalaryParamVO.isComputeSocialFlag(), user, userSalary, userDetail);
        // 计算
        // PS:上月转正员工，转正前无绩效，转正后有绩效
        // 计算后比例工资 = (员工标准薪资*转正前薪资发放比例/21.75)
        // 转正前工资 = 计算后比例工资*转正前应出勤天数 - 计算后比例工资*转正前其他缺勤天数 - 计算后比例工资*转正前病假缺勤天数 + 计算后比例工资*病假比例

        // 转正后预估基本工资 = 计算后比例工资*转正后应出勤天数*（1-绩效占工资比例）
        // 转正后本月绩效工资 = 计算后比例工资*绩效占工资比例*本月绩效系数
        // 转正后全勤工资 = 转正后预估基本工资 + 转正后本月绩效工资
        // 转正后工资 = 转正后全勤工资 - 转正后全勤工资*转正后其他缺勤天数 - 转正后全勤工资*转正后病假缺勤天数 + 转正后全勤工资*病假比例

        // 本月出勤工资 = 转正前工资 + 转正后工资

        // 计算 转正前部分
        // (员工标准薪资*转正前薪资发放比例/21.75)
        BigDecimal theBeforeDecimal = userDetail.getComputeProbationSalary().divide(Constants.STANDARD_SALARY_RATIO, 2, BigDecimal.ROUND_HALF_UP);
        // 转正前工资
        BigDecimal beforeSalary = theBeforeDecimal.multiply(computeSalaryParamVO.getPositiveBeforeAttendanceDays()).setScale(2, BigDecimal.ROUND_HALF_UP)
                .subtract(theBeforeDecimal.multiply(computeSalaryParamVO.getPositiveBeforeOtherAttendanceDays()).setScale(2, BigDecimal.ROUND_HALF_UP))
                .subtract(theBeforeDecimal.multiply(computeSalaryParamVO.getPositiveBeforeSickAttendanceDays()).setScale(2, BigDecimal.ROUND_HALF_UP))
                .add(theBeforeDecimal.multiply(userDetail.getPersonSickStandard()).setScale(2, BigDecimal.ROUND_HALF_UP));
        // 转正后  计算后比例工资
        BigDecimal theAfterDecimal = userDetail.getComputeStandardSalary().divide(Constants.STANDARD_SALARY_RATIO, 2, BigDecimal.ROUND_HALF_UP);
        // 转正后预估基本工资
        BigDecimal afterBaseSalary = theAfterDecimal.multiply(computeSalaryParamVO.getPositiveAfterAttendanceDays()).multiply(new BigDecimal("1.00").subtract(userDetail.getPerformanceRatio())).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 转正后本月绩效工资
        BigDecimal allPerformaneSalary = theAfterDecimal.multiply(userDetail.getPerformanceRatio()).multiply(computeSalaryParamVO.getMonthPerformanceRatio()).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 转正后全勤工资
        BigDecimal allSalary = afterBaseSalary.add(allPerformaneSalary);
        // 转正后工资
        BigDecimal afterSalary = allSalary.subtract(allSalary.multiply(computeSalaryParamVO.getPositiveAfterOtherAttendanceDays()).setScale(2, BigDecimal.ROUND_HALF_UP))
                .subtract(allSalary.multiply(computeSalaryParamVO.getPositiveAfterSickAttendanceDays()).setScale(2, BigDecimal.ROUND_HALF_UP))
                .add(allSalary.multiply(userDetail.getPersonSickStandard()).setScale(2, BigDecimal.ROUND_HALF_UP));
        // 本月出勤工资
        BigDecimal theMonthAttendanceSalary = beforeSalary.add(afterSalary);
        // 本月出勤工资 = 本月出勤工资 + 电脑补 + 其他补 - 其他扣款
        theMonthAttendanceSalary = theMonthAttendanceSalary.add(userDetail.getAddComputerSubsidy()).add(userDetail.getAddOtherSubsidy()).subtract(userDetail.getDeductOther());
        // 赋值本月  电脑补、其他补、其他扣款、病假扣款、事假扣款
        userSalary.setAddComputerSubsidy(userDetail.getAddComputerSubsidy());
        userSalary.setAddOtherSubsidy(userDetail.getAddOtherSubsidy());
        userSalary.setDeductOther(userDetail.getDeductOther());
        // todo 计算
        userSalary.setDeductSick(allSalary.multiply(new BigDecimal("1.00").subtract(userDetail.getPersonSickStandard())).setScale(2, BigDecimal.ROUND_HALF_UP));
        userSalary.setDeductThing(allSalary.multiply(computeSalaryParamVO.getPositiveAfterOtherAttendanceDays()).setScale(2, BigDecimal.ROUND_HALF_UP));


        // 赋值本月基本工资 = 转正前工资 + 转正后工资*(1 - 绩效占工资比例)
        userSalary.setMonthBaseSalary(beforeSalary.add(afterSalary.multiply(new BigDecimal("1.00").subtract(userDetail.getPerformanceRatio())).setScale(2, BigDecimal.ROUND_HALF_UP)));
        // 赋值本月绩效工资 = 转正后工资*绩效占工资比例
        userSalary.setMonthPerformanceSalary(afterSalary.multiply(userDetail.getPerformanceRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));

        // 计算个税  这里要先判断  【本月出勤工资 >= 预设银行代发工资】
        // 接上：大于等于的话，1.拿 【预设银行代发工资】去计算银行代发工资个税，2.拿本月出勤工资 - 去计算他行代发工资个税
        // 接上：小于的话，不计算个税
        if (theMonthAttendanceSalary.compareTo(userDetail.getBankSalary()) > -1) {
            // 本月出勤工资 >= 预设银行代发工资
            // 第一：拿 【预设银行代发工资】去计算银行代发工资个税
            // 公式：本月银行代发工资个税 = （（预设银行代发工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额） + 本年度累计应纳税所得额）* 所在工资类区间税率 - 该区间国家减免税额 - 本年度累计已纳税额
            // 本月应纳税所得额 = 预设银行代发工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额
            BigDecimal theNeedMoney = userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getStipulationStartTaxMoney()).subtract(userDetail.getSpecialDeductTotal());
            // 校验是否大于0元，大于0元才需要计税
            if (theNeedMoney.compareTo(new BigDecimal("0.00")) == 1) {
                // 本月待计算应纳税所得额 = 本月应纳税所得额 + 本年度累计应纳税所得额
                BigDecimal theMonthSelfMoney = theNeedMoney.add(userDetail.getTotalTaxableSelfMoney());
                // 判断所在区间并计算
                for (SalaryPersonTax salaryPersonTax : salaryPersonTaxList) {
                    // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                    // 接上：是的时候，只判断当前钱数是否大于开始钱数
                    // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (0 == salaryPersonTax.getMaxTaxFlag().intValue()) {
                        // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1 && theMonthSelfMoney.compareTo(salaryPersonTax.getEndMoney()) < 1) {
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    } else {
                        // 是：只判断当前钱数是否大于开始钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1) {
                            // 大于
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    }
                }
            } else {
                // 小于等于0元，不需要计税
                // 赋值
                // 本月薪资表赋值
                // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金
                userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
            }
            // 第二：本月他行代发工资计算
            // 公式：他行个人需缴纳税额 = （（本月出勤工资 - 个人社保公积金 - 五项专项扣除合计 - 纳税起步金额） * 所在非工资类区间税率 - 该区间国家减免税额 - 银行代发部分本月缴纳税额）/2
            // 本月他行代发工资税前金额 = 本月出勤工资 - 预设银行代发工资金额
            BigDecimal monthOtherBankSalary = theMonthAttendanceSalary.subtract(userDetail.getBankSalary());
            // 他行代发工资应纳税所得额 = 本月出勤工资 - 个人社保公积金 - 五项专项扣除合计 - 纳税起步金额
            BigDecimal theMonthOtherBankSelfMoney = theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getSpecialDeductTotal()).subtract(userDetail.getStipulationStartTaxMoney());
            // 判断所在区间并计算
            for (SalaryNonPersonTax nonPersonTax : salaryNonPersonTaxList) {
                // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                // 接上：是的时候，只判断当前钱数是否大于开始钱数
                // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                if (0 == nonPersonTax.getMaxTaxFlag().intValue()) {
                    // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getStartMoney()) == 1 && theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getEndMoney()) < 1) {
                        // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                        BigDecimal theMultiplyMoney = theMonthOtherBankSelfMoney.multiply(nonPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本月应缴税额 = (计算税率相乘后金额 - 该区间国家减免税额 - 银行代发工资本月缴纳税额)/2
                        BigDecimal subtract = theMultiplyMoney.subtract(nonPersonTax.getDeductMoney()).subtract(userSalary.getBankRealityShouldTaxMoney());
                        BigDecimal divide = subtract.divide(new BigDecimal("2.00"), 2, BigDecimal.ROUND_HALF_UP);
                        // 赋值 他行代发部分个税
                        userSalary.setOtherBankShouldTaxMoney(divide);
                        // 赋值 他行实发小计
                        userSalary.setOtherBankRealitySalary(monthOtherBankSalary.subtract(divide));
                        // 赋值 本月总工资实发总计
                        userSalary.setMonthSalaryRealityTotal(userSalary.getBankRealitySalary().add(userSalary.getOtherBankRealitySalary()));
                        // 跳出循环
                        break;
                    }
                } else {
                    // 是：只判断当前钱数是否大于开始钱数
                    if (theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getStartMoney()) == 1) {
                        // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                        BigDecimal theMultiplyMoney = theMonthOtherBankSelfMoney.multiply(nonPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本月应缴税额 = (计算税率相乘后金额 - 该区间国家减免税额 - 银行代发工资本月缴纳税额)/2
                        BigDecimal subtract = theMultiplyMoney.subtract(nonPersonTax.getDeductMoney()).subtract(userSalary.getBankRealityShouldTaxMoney());
                        BigDecimal divide = subtract.divide(new BigDecimal("2.00"), 2, BigDecimal.ROUND_HALF_UP);
                        // 赋值 他行代发部分个税
                        userSalary.setOtherBankShouldTaxMoney(divide);
                        // 赋值 他行实发小计
                        userSalary.setOtherBankRealitySalary(monthOtherBankSalary.subtract(divide));
                        // 赋值 本月总工资实发总计
                        userSalary.setMonthSalaryRealityTotal(userSalary.getBankRealitySalary().add(userSalary.getOtherBankRealitySalary()));
                        // 跳出循环
                        break;
                    }
                }
            }
        } else {
            // 本月出勤工资 < 预设银行代发工资
            // 第一：拿 【本月出勤工资】去计算银行代发工资个税
            // 公式：本月银行代发工资个税 = （（本月出勤工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额） + 本年度累计应纳税所得额）* 所在工资类区间税率 - 该区间国家减免税额 - 本年度累计已纳税额
            // 本月应纳税所得额 = 本月出勤工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额
            BigDecimal theNeedMoney = theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getStipulationStartTaxMoney()).subtract(userDetail.getSpecialDeductTotal());
            // 校验是否大于0元，大于0元才需要计税
            if (theNeedMoney.compareTo(new BigDecimal("0.00")) == 1) {
                // 本月待计算应纳税所得额 = 本月应纳税所得额 + 本年度累计应纳税所得额
                BigDecimal theMonthSelfMoney = theNeedMoney.add(userDetail.getTotalTaxableSelfMoney());
                // 判断所在区间并计算
                for (SalaryPersonTax salaryPersonTax : salaryPersonTaxList) {
                    // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                    // 接上：是的时候，只判断当前钱数是否大于开始钱数
                    // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (0 == salaryPersonTax.getMaxTaxFlag().intValue()) {
                        // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1 && theMonthSelfMoney.compareTo(salaryPersonTax.getEndMoney()) < 1) {
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    } else {
                        // 是：只判断当前钱数是否大于开始钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1) {
                            // 大于
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    }
                }
            } else {
                // 小于等于0元，不需要计税
                // 赋值
                // 本月薪资表赋值
                // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金
                userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
            }
        }
        // 操作数据库
        // 赋值
        userSalary.setPositiveBeforeAttendanceDays(computeSalaryParamVO.getPositiveBeforeAttendanceDays());// 转正前应出勤天数
        userSalary.setPositiveBeforeOtherAttendanceDays(computeSalaryParamVO.getPositiveBeforeOtherAttendanceDays());// 转正前其他缺勤天数
        userSalary.setPositiveBeforeSickAttendanceDays(computeSalaryParamVO.getPositiveBeforeSickAttendanceDays());// 转正前病假缺勤天数
        userSalary.setPositiveAfterAttendanceDays(computeSalaryParamVO.getPositiveAfterAttendanceDays());// 转正后应出勤天数
        userSalary.setPositiveAfterOtherAttendanceDays(computeSalaryParamVO.getPositiveAfterOtherAttendanceDays());// 转正后其他缺勤天数
        userSalary.setPositiveAfterSickAttendanceDays(computeSalaryParamVO.getPositiveAfterSickAttendanceDays());// 转正后病假天数
        userSalary.setMonthPerformanceRatio(computeSalaryParamVO.getMonthPerformanceRatio());// 本月绩效比例
        userSalary.setCurrentComputeFlag(1);// 给本月是否计算过标识赋值为 本月已计算过。
        // 将用户本月薪资表更新
        userSalaryMapper.updateById(userSalary);
        return ApiResult.getSuccessApiResponse(userSalary);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 13:58 2020/9/23
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 正常员工计薪
     **/
    @Override
    public ApiResult lastMonthCompute(ComputeSalaryParamVO computeSalaryParamVO, UserSessionVO userSessionVO) {
        // 获取个人工资类所得税比例表数据
        // 从缓存
        List<SalaryPersonTax> salaryPersonTaxList = (List<SalaryPersonTax>) RedisUtil.get(Constants.REDIS_SALARY_PERSON_TAX);
        // 校验
        if (CollectionUtils.isEmpty(salaryPersonTaxList)) {
            // 从数据库
            salaryPersonTaxList = salaryPersonTaxMapper.selectList(null);
            // 校验
            if (!CollectionUtils.isEmpty(salaryPersonTaxList)) {
                // 放入缓存 1天
                RedisUtil.set(Constants.REDIS_SALARY_PERSON_TAX, salaryPersonTaxList, Constants.REDIS_COMMON_SECONDS);
            }
        }
        // 再次校验
        if (CollectionUtils.isEmpty(salaryPersonTaxList)) {
            return ApiResult.getFailedApiResponse("工资类个税基础数据为空！");
        }
        // 获取个人非工资类所得税比例表数据
        // 从缓存
        List<SalaryNonPersonTax> salaryNonPersonTaxList = (List<SalaryNonPersonTax>) RedisUtil.get(Constants.REDIS_SALARY_NON_PERSON_TAX);
        // 校验
        if (CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
            // 从数据库
            salaryNonPersonTaxList = salaryNonPersonTaxMapper.selectList(null);
            // 校验
            if (!CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
                // 放入缓存 1天
                RedisUtil.set(Constants.REDIS_SALARY_NON_PERSON_TAX, salaryNonPersonTaxList, Constants.REDIS_COMMON_SECONDS);
            }
        }
        // 再次校验
        if (CollectionUtils.isEmpty(salaryNonPersonTaxList)) {
            return ApiResult.getFailedApiResponse("非工资类个税基础数据为空！");
        }
        // 查询到基础薪资数据
        UserSalary userSalary = userSalaryMapper.selectById(computeSalaryParamVO.getId());
        // 查询用户数据
        User user = userMapper.selectById(userSalary.getUserId());
        // 查询用户详情数据
        UserDetail userDetail = new UserDetail();
        userDetail.setDeleteFlag(0);
        userDetail.setUserId(userSalary.getUserId());
        userDetail = userDetailMapper.selectOne(userDetail);
        // ========先计算社保部分======
        computeSocialSecurity(computeSalaryParamVO.isComputeSocialFlag(), user, userSalary, userDetail);
        // 计算
        // PS:正常员工  有绩效/无绩效
        // 计算后比例工资 = (员工标准薪资*转正前薪资发放比例/21.75)
        // 本月预估基本工资 = 标准薪资*(1 - 绩效占工资比例)
        // 本月预估绩效工资 = 标准薪资*绩效占工资比例*本月绩效评分比例
        // 本月全勤工资 = 本月预估基本工资 + 本月预估绩效工资
        // 本月出勤工资 = 本月全勤工资 - 本月全勤工资*其他缺勤天数 - 本月全勤工资*病假缺勤天数 + 本月全勤工资*病假比例

        // 计算
        // 本月预估基本工资
        BigDecimal baseSalary = userDetail.getComputeStandardSalary().multiply(new BigDecimal("1.00").subtract(userDetail.getPerformanceRatio())).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 本月预估绩效工资
        BigDecimal allPerformaneSalary = userDetail.getComputeStandardSalary().multiply(userDetail.getPerformanceRatio()).multiply(computeSalaryParamVO.getMonthPerformanceRatio()).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 本月全勤工资
        BigDecimal allSalary = baseSalary.add(allPerformaneSalary);
        // 本月出勤工资
        BigDecimal theMonthAttendanceSalary = allSalary.subtract(allSalary.multiply(computeSalaryParamVO.getOtherAbsenceDays()).setScale(2, BigDecimal.ROUND_HALF_UP))
                .subtract(allSalary.multiply(computeSalaryParamVO.getSickAbsenceDays()).setScale(2, BigDecimal.ROUND_HALF_UP))
                .add(allSalary.multiply(userDetail.getPersonSickStandard()).setScale(2, BigDecimal.ROUND_HALF_UP));

        // 本月出勤工资 = 本月出勤工资 + 电脑补 + 其他补 - 其他扣款
        theMonthAttendanceSalary = theMonthAttendanceSalary.add(userDetail.getAddComputerSubsidy()).add(userDetail.getAddOtherSubsidy()).subtract(userDetail.getDeductOther());
        // 赋值本月  电脑补、其他补、其他扣款、病假扣款、事假扣款
        userSalary.setAddComputerSubsidy(userDetail.getAddComputerSubsidy());
        userSalary.setAddOtherSubsidy(userDetail.getAddOtherSubsidy());
        userSalary.setDeductOther(userDetail.getDeductOther());
        userSalary.setDeductSick(allSalary.multiply(new BigDecimal("1.00").subtract(userDetail.getPersonSickStandard())).setScale(2, BigDecimal.ROUND_HALF_UP));
        userSalary.setDeductThing(allSalary.multiply(computeSalaryParamVO.getOtherAbsenceDays()).setScale(2, BigDecimal.ROUND_HALF_UP));

        // 赋值本月基本工资 = 本月出勤工资*(1 - 绩效占工资比例)
        userSalary.setMonthBaseSalary(theMonthAttendanceSalary.multiply(new BigDecimal("1.00").subtract(userDetail.getPerformanceRatio())).setScale(2, BigDecimal.ROUND_HALF_UP));
        // 赋值本月绩效工资 = 本月出勤工资*绩效占工资比例
        userSalary.setMonthPerformanceSalary(theMonthAttendanceSalary.multiply(userDetail.getPerformanceRatio()).setScale(2, BigDecimal.ROUND_HALF_UP));

        // 计算个税  这里要先判断  【本月出勤工资 >= 预设银行代发工资】
        // 接上：大于等于的话，1.拿 【预设银行代发工资】去计算银行代发工资个税，2.拿本月出勤工资 - 去计算他行代发工资个税
        // 接上：小于的话，不计算个税
        if (theMonthAttendanceSalary.compareTo(userDetail.getBankSalary()) > -1) {
            // 本月出勤工资 >= 预设银行代发工资
            // 第一：拿 【预设银行代发工资】去计算银行代发工资个税
            // 公式：本月银行代发工资个税 = （（预设银行代发工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额） + 本年度累计应纳税所得额）* 所在工资类区间税率 - 该区间国家减免税额 - 本年度累计已纳税额
            // 本月应纳税所得额 = 预设银行代发工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额
            BigDecimal theNeedMoney = userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getStipulationStartTaxMoney()).subtract(userDetail.getSpecialDeductTotal());
            // 校验是否大于0元，大于0元才需要计税
            if (theNeedMoney.compareTo(new BigDecimal("0.00")) == 1) {
                // 本月待计算应纳税所得额 = 本月应纳税所得额 + 本年度累计应纳税所得额
                BigDecimal theMonthSelfMoney = theNeedMoney.add(userDetail.getTotalTaxableSelfMoney());
                // 判断所在区间并计算
                for (SalaryPersonTax salaryPersonTax : salaryPersonTaxList) {
                    // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                    // 接上：是的时候，只判断当前钱数是否大于开始钱数
                    // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (0 == salaryPersonTax.getMaxTaxFlag().intValue()) {
                        // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1 && theMonthSelfMoney.compareTo(salaryPersonTax.getEndMoney()) < 1) {
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    } else {
                        // 是：只判断当前钱数是否大于开始钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1) {
                            // 大于
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    }
                }
            } else {
                // 小于等于0元，不需要计税
                // 赋值
                // 本月薪资表赋值
                // 赋值 银行代发税前应发金额 = 预设银行代发工资 - 个人社保公积金
                userSalary.setBankTaxBeforeShouldSalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
                // 赋值 银行代发工资实发总计 = 预设银行代发工资 - 个人社保公积金
                userSalary.setBankRealitySalary(userDetail.getBankSalary().subtract(userSalary.getMonthPersonPayTotal()));
            }
            // 第二：本月他行代发工资计算
            // 公式：他行个人需缴纳税额 = （（本月出勤工资 - 个人社保公积金 - 五项专项扣除合计 - 纳税起步金额） * 所在非工资类区间税率 - 该区间国家减免税额 - 银行代发部分本月缴纳税额）/2
            // 本月他行代发工资税前金额 = 本月出勤工资 - 预设银行代发工资金额
            BigDecimal monthOtherBankSalary = theMonthAttendanceSalary.subtract(userDetail.getBankSalary());
            // TODO 他行代发工资应纳税所得额 = 本月出勤工资 - 个人社保公积金 - 五项专项扣除合计 - 纳税起步金额
            BigDecimal theMonthOtherBankSelfMoney = theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getSpecialDeductTotal()).subtract(userDetail.getStipulationStartTaxMoney());
            // 判断所在区间并计算
            for (SalaryNonPersonTax nonPersonTax : salaryNonPersonTaxList) {
                // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                // 接上：是的时候，只判断当前钱数是否大于开始钱数
                // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                if (0 == nonPersonTax.getMaxTaxFlag().intValue()) {
                    // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getStartMoney()) == 1 && theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getEndMoney()) < 1) {
                        // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                        BigDecimal theMultiplyMoney = theMonthOtherBankSelfMoney.multiply(nonPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本月应缴税额 = (计算税率相乘后金额 - 该区间国家减免税额 - 银行代发工资本月缴纳税额)/2
                        BigDecimal subtract = theMultiplyMoney.subtract(nonPersonTax.getDeductMoney()).subtract(userSalary.getBankRealityShouldTaxMoney());
                        BigDecimal divide = subtract.divide(new BigDecimal("2.00"), 2, BigDecimal.ROUND_HALF_UP);
                        // 赋值 他行代发部分个税
                        userSalary.setOtherBankShouldTaxMoney(divide);
                        // 赋值 他行实发小计
                        userSalary.setOtherBankRealitySalary(monthOtherBankSalary.subtract(divide));
                        // 赋值 本月总工资实发总计
                        userSalary.setMonthSalaryRealityTotal(userSalary.getBankRealitySalary().add(userSalary.getOtherBankRealitySalary()));
                        // 跳出循环
                        break;
                    }
                } else {
                    // 是：只判断当前钱数是否大于开始钱数
                    if (theMonthOtherBankSelfMoney.compareTo(nonPersonTax.getStartMoney()) == 1) {
                        // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                        BigDecimal theMultiplyMoney = theMonthOtherBankSelfMoney.multiply(nonPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        // 本月应缴税额 = (计算税率相乘后金额 - 该区间国家减免税额 - 银行代发工资本月缴纳税额)/2
                        BigDecimal subtract = theMultiplyMoney.subtract(nonPersonTax.getDeductMoney()).subtract(userSalary.getBankRealityShouldTaxMoney());
                        BigDecimal divide = subtract.divide(new BigDecimal("2.00"), 2, BigDecimal.ROUND_HALF_UP);
                        // 赋值 他行代发部分个税
                        userSalary.setOtherBankShouldTaxMoney(divide);
                        // 赋值 他行实发小计
                        userSalary.setOtherBankRealitySalary(monthOtherBankSalary.subtract(divide));
                        // 赋值 本月总工资实发总计
                        userSalary.setMonthSalaryRealityTotal(userSalary.getBankRealitySalary().add(userSalary.getOtherBankRealitySalary()));
                        // 跳出循环
                        break;
                    }
                }
            }
        } else {
            // 本月出勤工资 < 预设银行代发工资
            // 第一：拿 【本月出勤工资】去计算银行代发工资个税
            // 公式：本月银行代发工资个税 = （（本月出勤工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额） + 本年度累计应纳税所得额）* 所在工资类区间税率 - 该区间国家减免税额 - 本年度累计已纳税额
            // 本月应纳税所得额 = 本月出勤工资 - 个人社保公积金 - 个税缴纳起步基数 - 五项专扣总额
            BigDecimal theNeedMoney = theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(userDetail.getStipulationStartTaxMoney()).subtract(userDetail.getSpecialDeductTotal());
            // 校验是否大于0元，大于0元才需要计税
            if (theNeedMoney.compareTo(new BigDecimal("0.00")) == 1) {
                // 本月待计算应纳税所得额 = 本月应纳税所得额 + 本年度累计应纳税所得额
                BigDecimal theMonthSelfMoney = theNeedMoney.add(userDetail.getTotalTaxableSelfMoney());
                // 判断所在区间并计算
                for (SalaryPersonTax salaryPersonTax : salaryPersonTaxList) {
                    // 规则：先判断当前数据是否是最大所得额标识：0--否，1--是
                    // 接上：是的时候，只判断当前钱数是否大于开始钱数
                    // 接上：否的时候，判断当前钱数要大于开始钱数，小于等于结束钱数
                    if (0 == salaryPersonTax.getMaxTaxFlag().intValue()) {
                        // 否：判断当前钱数要大于开始钱数，小于等于结束钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1 && theMonthSelfMoney.compareTo(salaryPersonTax.getEndMoney()) < 1) {
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    } else {
                        // 是：只判断当前钱数是否大于开始钱数
                        if (theMonthSelfMoney.compareTo(salaryPersonTax.getStartMoney()) == 1) {
                            // 大于
                            // 计算税率相乘后金额 = 本月应纳税所得额 * 所在区间税率
                            BigDecimal theMultiplyMoney = theMonthSelfMoney.multiply(salaryPersonTax.getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            // 本月纳税金额 = 计算税率相乘后金额 - 该区间国家减免税额 - 本年度累计已纳税额
                            BigDecimal theMonthTaxMoney = theMultiplyMoney.subtract(salaryPersonTax.getDeductMoney()).subtract(userDetail.getTotalAlreadyTaxableMoney());
                            // 本月薪资表赋值
                            // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                            userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                            // 赋值 银行代发工资的税率
                            userSalary.setBankTaxRatio(salaryPersonTax.getTax());
                            // 赋值 银行代发本月预估应缴税额  与  银行代发本月实际应缴税额
                            userSalary.setBankPlanShouldTaxMoney(theMonthTaxMoney);
                            userSalary.setBankRealityShouldTaxMoney(theMonthTaxMoney);
                            // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金 - 个税应缴税额
                            userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()).subtract(theMonthTaxMoney));
                            // 跳出循环
                            break;
                        }
                    }
                }
            } else {
                // 小于等于0元，不需要计税
                // 赋值
                // 本月薪资表赋值
                // 赋值 银行代发税前应发金额 = 本月出勤工资 - 个人社保公积金
                userSalary.setBankTaxBeforeShouldSalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
                // 赋值 银行代发工资实发总计 = 本月出勤工资 - 个人社保公积金
                userSalary.setBankRealitySalary(theMonthAttendanceSalary.subtract(userSalary.getMonthPersonPayTotal()));
            }
        }
        // 操作数据库
        // 赋值
        userSalary.setOtherAbsenceDays(computeSalaryParamVO.getOtherAbsenceDays());// 其他缺勤天数
        userSalary.setSickAdsenceDays(computeSalaryParamVO.getSickAbsenceDays());// 病假缺勤天数
        userSalary.setMonthPerformanceRatio(computeSalaryParamVO.getMonthPerformanceRatio());// 本月绩效比例
        userSalary.setCurrentComputeFlag(1);// 给本月是否计算过标识赋值为 本月已计算过。
        // 将用户本月薪资表更新
        userSalaryMapper.updateById(userSalary);
        return ApiResult.getSuccessApiResponse(userSalary);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:24 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 发起薪资审批流程，根据所传薪资归属部门id
     **/
    @Override
    public ApiResult startSalaryFlow(Long salaryDeptId, Integer userPostType, UserSessionVO userSessionVO) {
        // 校验权限是否为总经理/副总/薪资核算角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID) && !roleIdList.contains(Constants.SALARY_DEPT_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权发起流程！");
        }
        // 定义是否为总经理/副总 角色标识
        boolean adminFlag = false;
        if (roleIdList.contains(Constants.ADMIN_ROLE_ID) || roleIdList.contains(Constants.OTHER_ROLE_ID)) {
            adminFlag = true;
        }
        // 校验流程发起类型， userPostType == 0为副总发起的管理岗员工，此时salaryDeptId为空，会将公司所有的管理岗一并发起
        // userPostType == 1为薪资核算人员发起的技术岗员工，此时salaryDeptId必填，会按所传薪资归属部门发起流程

        // 校验  Integer类型的userPostType所选岗位类型，当userPostType==0代表管理岗，否则为技术岗
        if (0 == userPostType.intValue()) {
            // 管理岗计薪列表
            if (!adminFlag) {
                return ApiResult.getFailedApiResponse("您无权操作管理岗流程！");
            }
        } else {
            if (salaryDeptId == null) {
                return ApiResult.getFailedApiResponse("薪资归属部门ID必传！");
            }
        }
        String userPostTypeString = null;
        // 定义薪资流程的流程主表id
        Long flowConfigId = null;
        // 定义薪资归属部门id集合
        List<Long> salaryDeptIdList = Lists.newArrayList();
        // 校验是副总还是薪资核算人员
        if (adminFlag) {
            // 只查询管理岗
            userPostTypeString = "true";
            // 查询副总角色的流程
            BaseFlowConfig baseFlowConfig = new BaseFlowConfig();
            baseFlowConfig.setDeleteFlag(0);
            baseFlowConfig.setUseFlag(0);
            baseFlowConfig.setFlowRoleId(Constants.OTHER_ROLE_ID);
            baseFlowConfig = baseFlowConfigMapper.selectOne(baseFlowConfig);
            // 校验
            if (null == baseFlowConfig) {
                return ApiResult.getFailedApiResponse("未查询到您所属薪资审批流程！");
            }
            flowConfigId = baseFlowConfig.getId();
            // 查询所有的薪资归属部门id集合
            List<SalaryDept> salaryDeptList = salaryDeptMapper.selectList(Condition.create().eq("delete_flag", 0));
            salaryDeptIdList = salaryDeptList.stream().map(SalaryDept::getId).collect(Collectors.toList());
        } else {
            userPostTypeString = null;
            // 查询薪资核算角色的流程
            BaseFlowConfig baseFlowConfig = new BaseFlowConfig();
            baseFlowConfig.setDeleteFlag(0);
            baseFlowConfig.setUseFlag(0);
            baseFlowConfig.setFlowRoleId(Constants.SALARY_DEPT_ROLE_ID);
            baseFlowConfig.setFlowSalaryDeptId(salaryDeptId);
            baseFlowConfig = baseFlowConfigMapper.selectOne(baseFlowConfig);
            // 校验
            if (null == baseFlowConfig) {
                return ApiResult.getFailedApiResponse("未查询到您所属薪资审批流程！");
            }
            flowConfigId = baseFlowConfig.getId();
            // 赋值薪资归属部门id
            salaryDeptIdList.add(salaryDeptId);
        }
        // 获取上个月时间
        Date thisDateLastMonth = DateUtils.getThisDateLastMonth();
        // 查询薪资表id   条件为上个月份，且用户角色，薪资归属部门id集合， 另外加上“本月已经结算过”条件， currentComputeFlag == 0  未结算， currentComputeFlag == 1  已经结算
        Integer currentComputeFlag = 1;
        List<Long> userSalaryIdList = userSalaryMapper.selectUserSalaryList(userPostTypeString, thisDateLastMonth, salaryDeptIdList, currentComputeFlag);
        // 校验
        if (CollectionUtils.isEmpty(userSalaryIdList)) {
            return ApiResult.getFailedApiResponse("您所选部门的本月已计算过并且可发起流程工资单为空！");
        }
        // 再查询 【结算和不结算一起查】
        currentComputeFlag = null;
        List<Long> userSalaryIdListAll = userSalaryMapper.selectUserSalaryList(userPostTypeString, thisDateLastMonth, salaryDeptIdList, currentComputeFlag);
        // 校验
        if (userSalaryIdList.size() != userSalaryIdListAll.size()) {
            return ApiResult.getFailedApiResponse("请将您所关联的所有员工本月工资计薪后再发起流程！");
        }
        // 将薪资id的  是否允许再次编辑状态 批量改为 1 不允许   again_compute_flag
        UserSalary userSalary = new UserSalary();
        userSalary.setAgainComputeFlag(1);
        userSalaryMapper.update(userSalary, Condition.create().in("id", userSalaryIdList));
        // 获取薪资单据编号
        String applicationCode = baseService.getBillCodeByBillType(1);
        // 获取流程code
        String flowCode = baseService.getFlowCodeByApplicationCode(applicationCode);
        // 准备新增
        SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
        // 赋值
        salaryFlowBill.setApplicationCode(applicationCode);
        salaryFlowBill.setFlowCode(flowCode);
        salaryFlowBill.setBaseFlowConfigId(flowConfigId);
        salaryFlowBill.setApplicationType("薪资审批");
        salaryFlowBill.setHandleId(userSessionVO.getId());
        salaryFlowBill.setHandleAccount(userSessionVO.getUserAccount());
        salaryFlowBill.setHandleName(userSessionVO.getUserName());
        salaryFlowBill.setDeleteFlag(0);
        salaryFlowBill.setCreateId(userSessionVO.getUserAccount());
        salaryFlowBill.setCreateName(userSessionVO.getUserName());
        salaryFlowBill.setCreateTime(new Date());
        salaryFlowBill.setEditTime(new Date());

        // 校验
        if (adminFlag) {
            salaryFlowBill.setSalaryDeptId(0l);// 副总这里声明薪资归属部门id为0
            salaryFlowBill.setRoleId(Constants.OTHER_ROLE_ID);
            salaryFlowBill.setRoleName("副总");
        } else {
            salaryFlowBill.setSalaryDeptId(salaryDeptId);
            salaryFlowBill.setRoleId(Constants.SALARY_DEPT_ROLE_ID);
            salaryFlowBill.setRoleName("薪资核算人员");
        }
        // 单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
        salaryFlowBill.setApplicationStatus(1);
        salaryFlowBill.setUserSalaryIds(JSONArray.toJSONString(userSalaryIdList));
        // 新增
        salaryFlowBillMapper.insert(salaryFlowBill);
        // 新增一条待审记录到流程记录表中
        // 先查询流程详情表
        List<BaseFlowConfigDetail> detailList = baseFlowConfigDetailMapper.selectList(Condition.create().eq("base_flow_config_id", flowConfigId).eq("delete_flag", 0));
        // 定义流程详情对象
        BaseFlowConfigDetail configDetail = null;
        // 获取第一个审批节点
        for (BaseFlowConfigDetail detail : detailList) {
            // 校验
            if (1 == detail.getFirstFlag().intValue()) {
                // 赋值
                configDetail = detail;
                // 跳出
                break;
            }
        }
        // 拆分出用户id集合 && 用户名称集合
        List<Long> approverIdList = Arrays.asList(configDetail.getApproverIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        List<String> approverNameList = Arrays.asList(configDetail.getApproverIds().split(","));
        // 定义序号
        int number = 0;
        // 遍历
        for (Long approverId : approverIdList) {
            // 定义对象并赋值
            BaseFlowRecord record = new BaseFlowRecord();
            record.setBaseFlowConfigId(flowConfigId);
            record.setBaseFlowConfigDetailId(configDetail.getId());
            record.setNodeName(configDetail.getNodeName());
            record.setApproverId(approverId);
            record.setApproverName(approverNameList.get(number));
            record.setApplicationCode(applicationCode);
            record.setFlowCode(flowCode);
            // 审批状态：0--待审，1--驳回，2--通过
            record.setApproverStatus(0);
            record.setDeleteFlag(0);
            record.setCreateId(userSessionVO.getUserAccount());
            record.setCreateName(userSessionVO.getUserName());
            record.setCreateTime(new Date());
            record.setEditTime(new Date());
            // 新增入库
            baseFlowRecordMapper.insert(record);
            // 自增
            number++;
        }
        // 流程审批发起成功
        return ApiResult.getSuccessApiResponse();
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:05 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询历史工资单列表，默认查上个月
     **/
    @Override
    public ApiResult selectHistorySalaryList(SalaryHistoryQueryVO salaryHistoryQueryVO, UserSessionVO userSessionVO) {
        // 校验日期
        if (null == salaryHistoryQueryVO.getSalaryDate()) {
            // 查询上个月
            salaryHistoryQueryVO.setSalaryDate(DateUtils.getThisDateLastMonth());
        }
        // 查询
        // 查询计薪列表
        PageHelper.startPage(salaryHistoryQueryVO.getPageNum(), salaryHistoryQueryVO.getPageSize());
        List<SalaryHistoryResultVO> historyResultVOList = userSalaryMapper.selectHistorySalaryList(salaryHistoryQueryVO);
        PageInfo<SalaryHistoryResultVO> info = new PageInfo<>(historyResultVOList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<SalaryHistoryResultVO> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:45 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询汇总列表
     **/
    @Override
    public ApiResult selectCollectListBySalaryDate(Date salaryDate, UserSessionVO userSessionVO) {

        SalaryHistoryQueryVO salaryHistoryQueryVO = new SalaryHistoryQueryVO();
        salaryHistoryQueryVO.setSalaryDate(salaryDate);
        List<SalaryHistoryResultVO> historyResultVOList = userSalaryMapper.selectHistorySalaryList(salaryHistoryQueryVO);
        // 校验
        if (CollectionUtils.isEmpty(historyResultVOList)) {
            return ApiResult.getSuccessApiResponse();
        }
        // 按照薪资归属部门分组
        Map<Long, List<SalaryHistoryResultVO>> listMap = historyResultVOList.stream().collect(Collectors.groupingBy(SalaryHistoryResultVO::getSalaryDeptId));
        Set<Long> keySet = listMap.keySet();
        // 定义返回结果
        List<SalaryCollectResultVO> resultVOList = Lists.newArrayListWithCapacity(keySet.size());
        // 定义序号
        int thisNumber = 0;
        // 遍历
        for (Long key : keySet) {
            // 自增
            thisNumber++;
            // 获取集合
            List<SalaryHistoryResultVO> thisVOList = listMap.get(key);
            // 定义三个金额
            BigDecimal thisDeptMoneyTotal = new BigDecimal("0.00");
            BigDecimal thisDeptCostTotal = new BigDecimal("0.00");
            BigDecimal thisDeptSkillTotal = new BigDecimal("0.00");
            // 遍历
            for (SalaryHistoryResultVO resultVO : thisVOList) {
                // 校验
                if (0 == resultVO.getUserPostType().intValue()) {
                    thisDeptMoneyTotal = thisDeptMoneyTotal.add(resultVO.getMonthSalaryRealityTotal());
                } else if (1 == resultVO.getUserPostType().intValue()) {
                    thisDeptCostTotal = thisDeptCostTotal.add(resultVO.getMonthSalaryRealityTotal());
                } else if (2 == resultVO.getUserPostType().intValue()) {
                    thisDeptSkillTotal = thisDeptSkillTotal.add(resultVO.getMonthSalaryRealityTotal());
                }
            }
            // 定义对象并赋值
            SalaryCollectResultVO collectResultVO = new SalaryCollectResultVO();
            collectResultVO.setThisNumber(thisNumber);
            collectResultVO.setSalaryDate(salaryDate);
            collectResultVO.setSalaryDeptId(key);
            collectResultVO.setSalaryDeptName(thisVOList.get(0).getSalaryDeptName());
            collectResultVO.setSalaryDeptManageTotal(thisDeptMoneyTotal);
            collectResultVO.setSalaryDeptCostTotal(thisDeptCostTotal);
            collectResultVO.setSalaryDeptSkillTotal(thisDeptSkillTotal);
            collectResultVO.setSalaryDeptMoneyTotal(thisDeptMoneyTotal.add(thisDeptCostTotal).add(thisDeptSkillTotal));
            // 放入集合
            resultVOList.add(collectResultVO);
        }
        // 将本月份所有部门汇总
        // 定义三个金额
        BigDecimal thisDeptMoneyTotal = new BigDecimal("0.00");
        BigDecimal thisDeptCostTotal = new BigDecimal("0.00");
        BigDecimal thisDeptSkillTotal = new BigDecimal("0.00");
        // 遍历
        for (SalaryCollectResultVO resultVO : resultVOList) {
            thisDeptMoneyTotal = thisDeptMoneyTotal.add(resultVO.getSalaryDeptManageTotal());
            thisDeptCostTotal = thisDeptMoneyTotal.add(resultVO.getSalaryDeptCostTotal());
            thisDeptSkillTotal = thisDeptMoneyTotal.add(resultVO.getSalaryDeptSkillTotal());
        }
        // 定义对象并赋值
        SalaryCollectResultVO collectResultVO = new SalaryCollectResultVO();
        collectResultVO.setThisNumber(thisNumber++);
        collectResultVO.setSalaryDate(salaryDate);
        collectResultVO.setSalaryDeptId(0l);
        collectResultVO.setSalaryDeptName("所有部门");
        collectResultVO.setSalaryDeptManageTotal(thisDeptMoneyTotal);
        collectResultVO.setSalaryDeptCostTotal(thisDeptCostTotal);
        collectResultVO.setSalaryDeptSkillTotal(thisDeptSkillTotal);
        collectResultVO.setSalaryDeptMoneyTotal(thisDeptMoneyTotal.add(thisDeptCostTotal).add(thisDeptSkillTotal));
        // 放入集合
        resultVOList.add(collectResultVO);
        return ApiResult.getSuccessApiResponse(resultVOList);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:01 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 导出工资单，默认导出上月
     **/
    @Override
    public void exportSalaryBill(Date salaryDate, UserSessionVO userSessionVO, HttpServletResponse response) {
        SalaryHistoryQueryVO salaryHistoryQueryVO = new SalaryHistoryQueryVO();
        salaryHistoryQueryVO.setSalaryDate(salaryDate);
        List<SalaryHistoryResultVO> historyResultVOList = userSalaryMapper.selectHistorySalaryList(salaryHistoryQueryVO);
        // 校验
        if (CollectionUtils.isEmpty(historyResultVOList)) {
            throw new RuntimeException("空的薪资数据！");
        }
        // 定义自增序号
        int thisNumber = 0;
        // 遍历处理
        for (SalaryHistoryResultVO resultVO : historyResultVOList) {
            // 序号
            thisNumber++;
            // 处理
            resultVO.setThisNumber(thisNumber);
            // 入职日期
            resultVO.setEntryDate(DateUtils.getDateString(resultVO.getUserEntryDate(), "yyyy-MM-dd"));
            resultVO.setSocialDate(DateUtils.getDateString(resultVO.getSocialSecurityStartDate(), "yyyy-MM-dd"));
            resultVO.setUserStatus(resultVO.getUserRankType().intValue() == 0 ? "试用期" : resultVO.getUserRankType().intValue() == 1 ? "正式" : "离职");
            resultVO.setNewBaseMoney(new BigDecimal("0.00"));
            resultVO.setNewPerformanceMoney(new BigDecimal("0.00"));
        }


        Map<String, Object> map = new HashMap<>();
        map.put("exportList", historyResultVOList);
        TemplateExportParams params = new TemplateExportParams("exportAllSalaryTemplate.xlsx",true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        //导出
        ExcelExpUtil.excelDownload(workbook, response, "本月薪资表");
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:36 2020/10/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 驳回后再次发起流程，根据薪资流程记录表id
     *                 这里再次发起是重新建一个新流程code
     **/
    @Override
    public ApiResult updateSalaryFlowById(Long salaryFlowId, UserSessionVO userSessionVO) {
        // 校验流程id是否存在
        if (null == salaryFlowId) {
            return ApiResult.getFailedApiResponse("薪资流程表ID必传！");
        }
        // 查询
        SalaryFlowBill salaryFlowBill = salaryFlowBillMapper.selectById(salaryFlowId);
        // 校验 非空  或者 审批状态不为“驳回”  单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
        if (null == salaryFlowBill || 2 != salaryFlowBill.getApplicationStatus().intValue()) {
            return ApiResult.getFailedApiResponse("仅驳回的单据才能再次发起流程！");
        }
        // 获取新的流程code
        String flowCode = baseService.getFlowCodeByApplicationCode(salaryFlowBill.getApplicationCode());
        // 获取所有关联的薪资id
        List<Long> userSalaryIdList = (List<Long>) JSONArray.parse(salaryFlowBill.getUserSalaryIds());
        // 将薪资id的  是否允许再次编辑状态 批量改为 1 不允许   again_compute_flag
        UserSalary userSalary = new UserSalary();
        userSalary.setAgainComputeFlag(1);
        userSalaryMapper.update(userSalary, Condition.create().in("id", userSalaryIdList));
        // 重新赋值
        salaryFlowBill.setFlowCode(flowCode);
        salaryFlowBill.setApplicationType("薪资审批驳回后重新发起");
        salaryFlowBill.setHandleId(userSessionVO.getId());
        salaryFlowBill.setHandleAccount(userSessionVO.getUserAccount());
        salaryFlowBill.setHandleName(userSessionVO.getUserName());
        salaryFlowBill.setDeleteFlag(0);
        salaryFlowBill.setEditId(userSessionVO.getUserAccount());
        salaryFlowBill.setEditName(userSessionVO.getUserName());
        salaryFlowBill.setEditTime(new Date());
        // 单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
        salaryFlowBill.setApplicationStatus(1);
        // 新增
        salaryFlowBillMapper.updateById(salaryFlowBill);
        // 新增一条待审记录到流程记录表中
        Long flowConfigId = salaryFlowBill.getBaseFlowConfigId();
        String applicationCode = salaryFlowBill.getApplicationCode();
        // 先查询流程详情表
        List<BaseFlowConfigDetail> detailList = baseFlowConfigDetailMapper.selectList(Condition.create().eq("base_flow_config_id", flowConfigId).eq("delete_flag", 0));
        // 定义流程详情对象
        BaseFlowConfigDetail configDetail = null;
        // 获取第一个审批节点
        for (BaseFlowConfigDetail detail : detailList) {
            // 校验
            if (1 == detail.getFirstFlag().intValue()) {
                // 赋值
                configDetail = detail;
                // 跳出
                break;
            }
        }
        // 拆分出用户id集合 && 用户名称集合
        List<Long> approverIdList = Arrays.asList(configDetail.getApproverIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        List<String> approverNameList = Arrays.asList(configDetail.getApproverIds().split(","));
        // 定义序号
        int number = 0;
        // 遍历
        for (Long approverId : approverIdList) {
            // 定义对象并赋值
            BaseFlowRecord record = new BaseFlowRecord();
            record.setBaseFlowConfigId(flowConfigId);
            record.setBaseFlowConfigDetailId(configDetail.getId());
            record.setNodeName(configDetail.getNodeName());
            record.setApproverId(approverId);
            record.setApproverName(approverNameList.get(number));
            record.setApplicationCode(applicationCode);
            record.setFlowCode(flowCode);
            // 审批状态：审批状态：0--待审，1--驳回，2--通过
            record.setApproverStatus(0);
            record.setDeleteFlag(0);
            record.setCreateId(userSessionVO.getUserAccount());
            record.setCreateName(userSessionVO.getUserName());
            record.setCreateTime(new Date());
            record.setEditTime(new Date());
            // 新增入库
            baseFlowRecordMapper.insert(record);
            // 自增
            number++;
        }
        // 流程审批重新发起成功
        return ApiResult.getSuccessApiResponse();
    }

}
