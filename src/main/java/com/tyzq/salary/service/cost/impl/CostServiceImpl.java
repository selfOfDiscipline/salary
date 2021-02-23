package com.tyzq.salary.service.cost.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.ProjectCostMapper;
import com.tyzq.salary.mapper.ProjectMapper;
import com.tyzq.salary.mapper.ProjectUserMapper;
import com.tyzq.salary.mapper.UserSalaryMapper;
import com.tyzq.salary.model.ProjectCost;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.ProjectSalaryResultVO;
import com.tyzq.salary.service.cost.CostService;
import com.tyzq.salary.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
        // 查询所有项目下关联的员工账号信息
        List<String> userAccountList = projectUserMapper.selectObjs(Condition.create().setSqlSelect("user_account").in("project_code", projectCodeList).eq("delete_flag", 0));
        // 校验
        if (CollectionUtils.isEmpty(userAccountList)) {
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
        // 记录日志
        logger.info("生成" + date + "成本基础数据成功！共生成" + projectSalaryResultVOList.size() + "条数据！");
        return ApiResult.getFailedApiResponse("生成" + date + "成本基础数据成功！共生成" + projectSalaryResultVOList.size() + "条数据！");
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
}
