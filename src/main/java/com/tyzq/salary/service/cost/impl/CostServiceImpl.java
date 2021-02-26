package com.tyzq.salary.service.cost.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.ProjectCostMapper;
import com.tyzq.salary.mapper.ProjectMapper;
import com.tyzq.salary.mapper.ProjectUserMapper;
import com.tyzq.salary.mapper.UserSalaryMapper;
import com.tyzq.salary.model.Project;
import com.tyzq.salary.model.ProjectCost;
import com.tyzq.salary.model.ProjectUser;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.ProjectCostQueryVO;
import com.tyzq.salary.model.vo.cost.ProjectCostResultVO;
import com.tyzq.salary.model.vo.cost.ProjectSalaryResultVO;
import com.tyzq.salary.service.cost.CostService;
import com.tyzq.salary.utils.DateUtils;
import com.tyzq.salary.utils.PasswordUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:39 2021/2/2
 * @Description: //TODO 成本中心接口实现类
 **/
@Service
@Transactional
public class CostServiceImpl implements CostService {

    private static final Logger logger = LoggerFactory.getLogger(CostServiceImpl.class);

    @Resource
    private UserSalaryMapper userSalaryMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectUserMapper projectUserMapper;

    @Resource
    private ProjectCostMapper projectCostMapper;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 10:09 2021/2/22
     * @Param:
     * @return:
     * @Description: //TODO 一键生成当月基础成本数据信息, 日期不存在就默认生成上个月的,日期格式：2021-02
     **/
    @Override
    public ApiResult generateCostDataByDate(String date, UserSessionVO userSessionVO) {
        // 校验日期
        if (StringUtils.isBlank(date)) {
            date = DateUtils.getDateString(DateUtils.getThisDateLastMonth(), "yyyy-MM");
        }
        // 查询该公司内所有未删除项目下关联的人员信息
        List<String> projectCodeList = projectMapper.selectObjs(Condition.create().setSqlSelect("project_code").ne("project_status", 2).eq("delete_flag", 0));
        // 校验
        if (CollectionUtils.isEmpty(projectCodeList)) {
            // 记录日志
            logger.info("生成" + date + "成本基础数据成功！共生成0条数据！备注：查询到的非作废且未删除状态下的项目列表为空！");
            return ApiResult.getFailedApiResponse("生成" + date + "成本基础数据成功！共生成0条数据！备注：查询到的非作废且未删除状态下的项目列表为空！");
        }
        // 查询所有项目下关联的员工账号信息  条件：员工来源：0--系统获取，1--手工录入 为系统获取
        List<String> userAccountList = projectUserMapper.selectObjs(Condition.create().setSqlSelect("user_account").in("project_code", projectCodeList).eq("user_source", 0).eq("delete_flag", 0));
        // 查询所有项目下关联的员工账号信息  条件：员工来源：0--系统获取，1--手工录入 为手工录入
        List<ProjectUser> projectUserList = projectUserMapper.selectList(Condition.create().in("project_code", projectCodeList).eq("user_source", 1).eq("delete_flag", 0));
        // 校验
        if (CollectionUtils.isEmpty(userAccountList) && CollectionUtils.isEmpty(projectUserList)) {
            // 记录日志
            logger.info("生成" + date + "成本基础数据成功！共生成0条数据！备注：查询到的以上项目关联的员工数量为空！");
            return ApiResult.getFailedApiResponse("生成" + date + "成本基础数据成功！共生成0条数据！备注：查询到的以上项目关联的员工数量为空！");
        }
        // 查员工薪资数据用于生成用户当月成本。项目人员表+项目表+用户表+薪资表  4张表，根据所传日期 && 用户账号集合
        List<ProjectSalaryResultVO> projectSalaryResultVOList = userSalaryMapper.selectUserSalaryWithProjectCost(date + "-01 00:00:00", userAccountList);
        // 校验
        if (CollectionUtils.isEmpty(projectSalaryResultVOList)) {
            // 记录日志
            logger.info("生成" + date + "成本基础数据成功！共生成0条数据！备注：查询到以上月份的员工薪资数据为空！");
            return ApiResult.getFailedApiResponse("生成" + date + "成本基础数据成功！共生成0条数据！备注：查询到以上月份的员工薪资数据为空！");
        }
        // 获取所传日期的下一个月的日期
        Date theDateNextMonth = DateUtils.stepMonthWithDate(DateUtils.getDate(date + "-01 00:00:00", "yyyy-MM-dd HH:mm:ss"), 1);
        // 生成成本基础数据列表
        for (ProjectSalaryResultVO resultVO : projectSalaryResultVOList) {
            ProjectCost projectCost = new ProjectCost();
            // 赋值
            BeanUtils.copyProperties(resultVO, projectCost);
            // 赋值基本信息
            projectCost.setDeleteFlag(0);
            projectCost.setCreateId(userSessionVO.getUserAccount());
            projectCost.setCreateName(userSessionVO.getUserName());
            projectCost.setCreateTime(new Date());
            // 赋值
            // 出勤天数
            BigDecimal days = attendanceDays(resultVO, theDateNextMonth);
            projectCost.setAttendanceDays(days);
            // 工资成本
            projectCost.setCostSalary(resultVO.getMonthSalaryRealityTotal());
            // 社保成本
            projectCost.setCostSocialSecurity(resultVO.getMonthCompanyPayTotal());
            // 税务成本
            projectCost.setCostTax(resultVO.getOtherBankShouldTaxMoney());
            // 报销成本
            projectCost.setCostReimburse(BigDecimal.ZERO);
            // 判断项目类型：项目类型：0--外包，1--总包，2--自研，3--委托制
            int projectType = resultVO.getProjectType().intValue();
            if (0 == projectType) {
                // 外包
                // 员工总收入
                BigDecimal totalEarningMoney;
                // 判断员工收入类型：0--天结，1--月结
                if (resultVO.getEarningType().intValue() == 0) {
                    // 天结
                    // 员工总收入金额 = 出勤天数 * 收入金额
                    totalEarningMoney = days.multiply(resultVO.getEarningMoney());
                } else {
                    // 月结
                    // 员工总收入金额 = 出勤天数 * 收入金额 / 员工标准计薪天数（一般是21.75）
                    totalEarningMoney = days.multiply(resultVO.getEarningMoney()).divide(Constants.STANDARD_SALARY_RATIO, 2, BigDecimal.ROUND_HALF_UP);
                }
                // 员工总成本金额 = 工资成本 + 社保成本 + 税务成本 + 报销成本 + 成本其他金额增减项
                BigDecimal totalCostMoney = resultVO.getMonthSalaryRealityTotal().add(resultVO.getMonthCompanyPayTotal())
                        .add(resultVO.getOtherBankShouldTaxMoney());
                // 毛利金额 = 员工总收入金额 - 员工总成本金额
                BigDecimal costProfit = totalEarningMoney.subtract(totalCostMoney);
                // 毛利率 = 毛利/总收入金额
                BigDecimal costRatio = costProfit.divide(totalEarningMoney, 2, BigDecimal.ROUND_HALF_UP);
                // 赋值
                // 总收入金额
                projectCost.setTotalEarningMoney(totalEarningMoney);
                // 总成本金额
                projectCost.setTotalCostMoney(totalCostMoney);
                // 毛利金额
                projectCost.setCostProfit(costProfit);
                // 毛利率
                projectCost.setCostRatio(costRatio);
            } else if (1 == projectType || 2 == projectType) {
                // 总包 || 自研， 没法计算毛利和收入
                // 员工总成本金额 = 工资成本 + 社保成本 + 税务成本 + 报销成本 + 成本其他金额增减项
                BigDecimal totalCostMoney = resultVO.getMonthSalaryRealityTotal().add(resultVO.getMonthCompanyPayTotal())
                        .add(resultVO.getOtherBankShouldTaxMoney());
                // 赋值
                // 总成本金额
                projectCost.setTotalCostMoney(totalCostMoney);
                // 项目完成总进度
                projectCost.setProjectTotalFinishRatio(resultVO.getProjectFinishRatio());
            } else if (3 == projectType) {
                // 委托制
                // 不做处理，委托制如果不挂员工，是没有数据的
            }
            // 新增入库
            projectCostMapper.insert(projectCost);
        }
        // 定义手动录入员工数为0
        int size = 0;
        // 校验
        if (CollectionUtils.isNotEmpty(projectUserList)) {
            size = projectUserList.size();
            // 生成成本手动录入部分的员工
            for (ProjectUser projectUser : projectUserList) {
                ProjectCost projectCost = new ProjectCost();
                // 赋值
                BeanUtils.copyProperties(projectUser, projectCost);
                // 赋值基本信息
                projectCost.setDeleteFlag(0);
                projectCost.setCreateId(userSessionVO.getUserAccount());
                projectCost.setCreateName(userSessionVO.getUserName());
                projectCost.setCreateTime(new Date());
                // 新增入库
                projectCostMapper.insert(projectCost);
            }
        }
        // 记录日志
        logger.info("生成" + date + "成本基础数据成功！共生成" + projectSalaryResultVOList.size() + size + "条数据！");
        return ApiResult.getFailedApiResponse("生成" + date + "成本基础数据成功！共生成" + projectSalaryResultVOList.size() + size + "条数据！");
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 13:32 2021/2/23
     * @Param:
     * @return:
     * @Description: //TODO 校验该员工当月出勤天数
     **/
    private BigDecimal attendanceDays(ProjectSalaryResultVO resultVO, Date theDateNextMonth) {
        // 获取 薪资归属日期 & 薪资归属的次月日期 & 员工入职日期 & 员工实际转正日期  这四个日期各自时间戳
        long salaryDate = resultVO.getSalaryDate().getTime();
        long nextMonthDate = theDateNextMonth.getTime();
        long userEntryDate = resultVO.getUserEntryDate().getTime();
        long changeFormalDate = resultVO.getRealityChangeFormalDate() == null ? 0 : resultVO.getRealityChangeFormalDate().getTime();
        // 定义出勤天数
        BigDecimal days = new BigDecimal("0");
        // 校验 是上月入职 || 上月转正 || 正式员工
        if ((changeFormalDate < salaryDate || changeFormalDate >= nextMonthDate)
                && (userEntryDate < salaryDate || userEntryDate > nextMonthDate)) {
            // 正常员工
            // 取员工的出勤天数为：员工月标准全勤天数（一般为21.75） - 员工病假缺勤天数 - 员工事假缺勤天数
            days = Constants.STANDARD_SALARY_RATIO.subtract(resultVO.getSickAdsenceDays()).subtract(resultVO.getOtherAbsenceDays());
        } else if (userEntryDate >= salaryDate && userEntryDate < nextMonthDate) {
            // 上月入职员工
            // 取员工的出勤天数为：员工出勤天数
            days = resultVO.getNewEntryAttendanceDays();
        } else if (changeFormalDate >= salaryDate && changeFormalDate < nextMonthDate) {
            // 上月转正员工
            // 取员工的出勤天数为：员工月标准全勤天数（一般为21.75） - 员工转正前病假缺勤天数 - 员工转正前事假缺勤天数
            // - 员工转正后病假缺勤天数 - 员工转正后事假缺勤天数
            days = Constants.STANDARD_SALARY_RATIO.subtract(resultVO.getPositiveBeforeSickAttendanceDays())
                    .subtract(resultVO.getPositiveBeforeOtherAttendanceDays())
                    .subtract(resultVO.getPositiveAfterSickAttendanceDays())
                    .subtract(resultVO.getPositiveAfterOtherAttendanceDays());
        }
        return days;
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 15:49 2021/2/24
     * @Param:
     * @return:
     * @Description: //TODO 查询项目的月度成本详情,根据项目编号 + 所选月份(格式：yyyy-MM)，月份不传默认为上个月，查询该项目下所有成本，用于计算
     **/
    @Override
    public ApiResult getProjectCostByCondition(ProjectCostQueryVO costQueryVO) {
        // 校验日期是否存在
        String costDate = costQueryVO.getCostDate();
        if (StringUtils.isBlank(costDate)) {
            // 获取上个月日期
            costDate = DateUtils.getDateString(DateUtils.getThisDateLastMonth(), "yyyy-MM-dd HH:mm:ss");
        } else {
            costDate += "-01 00:00:00";
        }
        // 查询成本详情
        Project project = new Project();
        project.setProjectCode(costQueryVO.getProjectCode());
        project.setDeleteFlag(0);
        project = projectMapper.selectOne(project);
        // 校验
        if (null == project) {
            return ApiResult.getFailedApiResponse("该项目不存在！");
        }
        // 查询该项目该月份明细数据
        List<ProjectCost> projectCostList = projectCostMapper.selectList(Condition.create().eq("project_code", project.getProjectCode()).eq("salary_date", costDate).eq("delete_flag", 0));
        ProjectCostResultVO projectCostResultVO = new ProjectCostResultVO();
        projectCostResultVO.setProject(project);
        projectCostResultVO.setProjectCostList(projectCostList);
        return ApiResult.getSuccessApiResponse(projectCostResultVO);
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:28 2021/2/25
     * @Param:
     * @return:
     * @Description: //TODO 计算当前明细（人员） 计算当前明细（人员），有id是修改，无id新增
     **/
    @Override
    public ApiResult computeThisCost(String projectCode, String costDate, ProjectCost projectCost, UserSessionVO userSessionVO) {
        // 获取日期并拼接
        costDate += "-01 00:00:00";
        // 获取项目详情
        Project project = new Project();
        project.setProjectCode(projectCode);
        project.setDeleteFlag(0);
        project = projectMapper.selectOne(project);
        // 校验
        if (null == project) {
            return ApiResult.getFailedApiResponse("该项目不存在！");
        }
        // 判断项目类型：项目类型：0--外包，1--总包，2--自研，3--委托制
        int projectType = project.getProjectType().intValue();
        // 校验
        if (null == projectCost.getId()) {
            // 新增
            // 判断项目类型：项目类型：0--外包，1--总包，2--自研，3--委托制
            if (0 == projectType) {
                // 外包
                // 校验参数：收入类型 & 出勤天数 & 总收入金额 & 总成本金额
                if (null == projectCost.getEarningType()
                        || null == projectCost.getAttendanceDays()
                        || null == projectCost.getTotalEarningMoney()
                        || null == projectCost.getTotalCostMoney()) {
                    return ApiResult.getFailedApiResponse("外包项目的：收入类型/出勤天数/总收入金额/总成本金额，均不能为空！");
                }
                // 毛利金额 = 员工总收入金额 - 员工总成本金额
                BigDecimal costProfit = projectCost.getTotalEarningMoney().subtract(projectCost.getTotalCostMoney());
                // 毛利率 = 毛利/总收入金额
                BigDecimal costRatio = costProfit.divide(projectCost.getTotalEarningMoney(), 2, BigDecimal.ROUND_HALF_UP);
                // 赋值
                // 毛利金额
                projectCost.setCostProfit(costProfit);
                // 毛利率
                projectCost.setCostRatio(costRatio);
            } else if (1 == projectType || 2 == projectType) {
                // 总包
                // 校验参数：收入类型 & 出勤天数 & 总收入金额 & 总成本金额
                if (null == projectCost.getEarningType()
                        || null == projectCost.getAttendanceDays()
                        || null == projectCost.getTotalEarningMoney()
                        || null == projectCost.getTotalCostMoney()) {
                    return ApiResult.getFailedApiResponse("总包或者自研项目的：收入类型/出勤天数/总收入金额/总成本金额，均不能为空！");
                }
            } else if (3 == projectType) {
                // 委托制
                // 不做处理，委托制如果不挂员工，是没有数据的
            }
            // 新增入库
            // 随机员工账号
            projectCost.setUserAccount("tyzq-cost-" + PasswordUtil.randomGenerate(4));
            // 赋值基本信息
            projectCost.setDeleteFlag(0);
            projectCost.setCreateId(userSessionVO.getUserAccount());
            projectCost.setCreateName(userSessionVO.getUserName());
            projectCost.setCreateTime(new Date());
            // 赋值项目信息
            projectCost.setProjectCode(project.getProjectCode());
            projectCost.setProjectName(project.getProjectName());
            projectCost.setUserDeptId(project.getDeptId());
            projectCost.setUserDeptName(project.getDeptName());
            projectCost.setProjectType(project.getProjectType());
            projectCost.setSalaryDate(DateUtils.getDate(costDate, "yyyy-MM-dd HH:mm:ss"));
            // 入库
            projectCostMapper.insert(projectCost);
        } else {
            // 修改，即二次计算
            // 获取用户关联的项目信息
            ProjectUser projectUser = new ProjectUser();
            projectUser.setProjectCode(project.getProjectCode());
            projectUser.setUserAccount(projectCost.getUserAccount());
            projectUser.setDeleteFlag(0);
            projectUser = projectUserMapper.selectOne(projectUser);
            // 将 工资成本 & 社保成本 & 税务成本 & 报销成本  先置为0
            // 工资成本
            projectCost.setCostSalary(BigDecimal.ZERO);
            // 社保成本
            projectCost.setCostSocialSecurity(BigDecimal.ZERO);
            // 税务成本
            projectCost.setCostTax(BigDecimal.ZERO);
            // 报销成本
            projectCost.setCostReimburse(BigDecimal.ZERO);
            // 判断项目类型：项目类型：0--外包，1--总包，2--自研，3--委托制
            if (0 == projectType) {
                // 外包
                // 员工总收入
                BigDecimal totalEarningMoney;
                // 员工总成本
                BigDecimal totalCostMoney;
                // 判断员工收入类型：0--天结，1--月结
                if (projectCost.getEarningType().intValue() == 0) {
                    // 天结
                    // 员工总收入金额 = 出勤天数 * 收入金额
                    totalEarningMoney = projectCost.getAttendanceDays().multiply(projectUser.getEarningMoney());
                    // 员工总成本 = （工资成本 + 社保成本 + 税务成本 + 报销成本）* 出勤天数/21.75 + 成本其他金额增减项
                    totalCostMoney = projectCost.getTotalCostMoney().multiply(projectCost.getAttendanceDays())
                            .divide(Constants.STANDARD_SALARY_RATIO, 2, BigDecimal.ROUND_HALF_UP).add(projectCost.getMonthRewordsMoney());
                } else {
                    // 月结
                    // 员工总收入金额 = 出勤天数 * 收入金额 / 员工标准计薪天数（一般是21.75）
                    totalEarningMoney = projectCost.getAttendanceDays().multiply(projectUser.getEarningMoney()).divide(Constants.STANDARD_SALARY_RATIO, 2, BigDecimal.ROUND_HALF_UP);
                    // 员工总成本 = （工资成本 + 社保成本 + 税务成本 + 报销成本）* 出勤天数/21.75 + 成本其他金额增减项
                    totalCostMoney = projectCost.getTotalCostMoney().multiply(projectCost.getAttendanceDays())
                            .divide(Constants.STANDARD_SALARY_RATIO, 2, BigDecimal.ROUND_HALF_UP).add(projectCost.getMonthRewordsMoney());
                }
                // 毛利金额 = 员工总收入金额 - 员工总成本金额
                BigDecimal costProfit = totalEarningMoney.subtract(totalCostMoney);
                // 毛利率 = 毛利/总收入金额
                BigDecimal costRatio = costProfit.divide(totalEarningMoney, 2, BigDecimal.ROUND_HALF_UP);
                // 赋值
                // 总收入金额
                projectCost.setTotalEarningMoney(totalEarningMoney);
                // 总成本金额
                projectCost.setTotalCostMoney(totalCostMoney);
                // 毛利金额
                projectCost.setCostProfit(costProfit);
                // 毛利率
                projectCost.setCostRatio(costRatio);
            } else if (1 == projectType || 2 == projectType) {
                // 总包
                // 总成本
            } else if (3 == projectType) {
                // 委托制
                // 不做处理，委托制如果不挂员工，是没有数据的
            }
            // 新增入库
            // 赋值基本信息
            projectCost.setEditId(userSessionVO.getUserAccount());
            projectCost.setEditName(userSessionVO.getUserName());
            projectCost.setEditTime(new Date());
            // 入库
            projectCostMapper.updateById(projectCost);
        }
        return ApiResult.getSuccessApiResponse(projectCost.getId());
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:59 2021/2/25
     * @Param:
     * @return:
     * @Description: //TODO 计算项目的毛利，根据项目编号，所选月份，所填写的项目本月完成度
     **/
    @Override
    public ApiResult computeThisProject(String projectCode, String costDate, String monthFinishRatio, UserSessionVO userSessionVO) {
        // 给日期拼接后缀
        costDate += "-01 00:00:00";
        // 获取项目详情
        Project project = new Project();
        project.setProjectCode(projectCode);
        project.setDeleteFlag(0);
        project = projectMapper.selectOne(project);
        // 校验
        if (null == project) {
            return ApiResult.getFailedApiResponse("该项目不存在！");
        }
        // 本月 项目完成进度
        BigDecimal monthRatio = new BigDecimal(monthFinishRatio);
        // 计算该项目本月完成进度下 的总收入为多少
        BigDecimal totalEarningMoney = project.getTotalMoney().multiply(monthRatio).divide(new BigDecimal("100"));
        // 项目已完成总进度（不加本次计算的完成度）
        BigDecimal projectFinishRatio = project.getProjectFinishRatio();
        // 本月完成总进度
        // 判断该月是否已经计算过该项目的完成度
        if (StringUtils.isNotBlank(project.getLastComputeDate()) && project.getLastComputeDate().equals(costDate)) {
            // 代表本月已计算过
            // 将该项目的已完成总进度更新 = 已完成总进度 - 上月完成总进度
            // 该项目已完成 总进度
            projectFinishRatio = project.getProjectFinishRatio().subtract(project.getLastComputeRatio());
        }
        // 更新本月项目完成总进度 = 已完成总进度 + 该月完成总进度
        projectFinishRatio = projectFinishRatio.add(monthRatio);
        // 获取该项目下所有的 该月成本数据
        List<ProjectCost> projectCostList = projectCostMapper.selectList(Condition.create().eq("project_code", project.getProjectCode()).eq("salary_date", costDate).eq("delete_flag", 0));
        // 校验
        if (CollectionUtils.isNotEmpty(projectCostList)) {
            // 不为空
            // 获取所有的成本总金额
            BigDecimal totalCostMoney = projectCostList.stream().map(ProjectCost::getTotalCostMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
            // 计算毛利
            // 毛利金额 = 员工总收入金额 - 员工总成本金额
            BigDecimal costProfit = totalEarningMoney.subtract(totalCostMoney);
            // 毛利率 = 毛利/总收入金额
            BigDecimal costRatio = costProfit.divide(totalEarningMoney, 2, BigDecimal.ROUND_HALF_UP);
            // 赋值入库
            for (ProjectCost projectCost : projectCostList) {
                // 赋值
                projectCost.setEditId(userSessionVO.getUserAccount());
                projectCost.setEditName(userSessionVO.getUserName());
                projectCost.setEditTime(new Date());
                // 总收入金额
                projectCost.setTotalEarningMoney(totalEarningMoney);
                // 总成本金额
                projectCost.setTotalCostMoney(totalCostMoney);
                // 毛利金额
                projectCost.setCostProfit(costProfit);
                // 毛利率
                projectCost.setCostRatio(costRatio);
                // 项目本月完成进度
                projectCost.setProjectMonthFinishRatio(monthRatio);
                // 项目已完成总进度
                projectCost.setProjectTotalFinishRatio(projectFinishRatio);
                // 修改入库
                projectCostMapper.updateById(projectCost);
            }
        }
        // 给项目信息 赋值
        // 最终计算月份
        project.setLastComputeDate(costDate);
        // 最红计算月份所完成进度
        project.setLastComputeRatio(monthRatio);
        // 项目已完成总进度
        project.setProjectFinishRatio(projectFinishRatio);
        // 修改入库
        projectMapper.updateById(project);
        return ApiResult.getSuccessApiResponse("计算成功！");
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 10:03 2021/2/26
     * @Param:
     * @return:
     * @Description: //TODO 批量删除项目成本明细，根据所传项目成本明细id字符串，多个用英文逗号拼接
     **/
    @Override
    public ApiResult deleteProjectCostByIds(String ids, UserSessionVO userSessionVO) {
        // 转换
        // 按照英文逗号分隔
        List<Long> longIdList = Arrays.asList(ids.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        // 校验
        if (CollectionUtils.isEmpty(longIdList)) {
            return ApiResult.getSuccessApiResponse("操作成功！您本次共删除：0条数据！");
        }
        // 修改项目成本表
        projectCostMapper.update(new ProjectCost() {{
            setDeleteFlag(1);
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
        }}, Condition.create().in("id", longIdList));
        return ApiResult.getSuccessApiResponse("操作成功！您本次共删除：" + longIdList.size() + "条数据！");
    }
}
