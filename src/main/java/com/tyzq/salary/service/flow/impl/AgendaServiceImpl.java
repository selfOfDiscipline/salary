package com.tyzq.salary.service.flow.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.*;
import com.tyzq.salary.model.*;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.flow.FlowHandleParamVO;
import com.tyzq.salary.model.vo.flow.FlowRecordQueryVO;
import com.tyzq.salary.model.vo.flow.SalaryBillQueryVO;
import com.tyzq.salary.model.vo.salary.UserComputeResultVO;
import com.tyzq.salary.service.config.BaseService;
import com.tyzq.salary.service.flow.AgendaService;
import com.tyzq.salary.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;


/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 20:14
 * @Description: //TODO 待办管理实现类
 **/
@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

    @Resource
    private BaseFlowRecordMapper baseFlowRecordMapper;

    @Resource
    private SalaryFlowBillMapper salaryFlowBillMapper;

    @Resource
    private UserSalaryMapper userSalaryMapper;

    @Resource
    private BaseFlowConfigDetailMapper baseFlowConfigDetailMapper;

    @Resource
    private BaseFlowGenerateMapper baseFlowGenerateMapper;

    @Resource
    private UserDetailMapper userDetailMapper;

    @Resource
    private BaseService baseService;

    @Resource
    private BaseFlowConfigMapper baseFlowConfigMapper;

    @Resource
    private SalaryDeptMapper salaryDeptMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:21 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询个人待办列表
     **/
    @Override
    public ApiResult selectPersonAgendaList(FlowRecordQueryVO flowRecordQueryVO, UserSessionVO userSessionVO) {
//        // 校验权限是否为总经理/副总角色
//        List<Long> roleIdList = userSessionVO.getRoleIdList();
//        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
//            return ApiResult.getFailedApiResponse("您无权查看流程配置信息！");
//        }
        // 条件构造   approverStatus审批状态：0--待审，1--驳回，2--通过
        Wrapper wrapper = Condition.create().eq("approver_id", userSessionVO.getId());
        // 校验 流程编号
        if (StringUtils.isNotBlank(flowRecordQueryVO.getApplicationCode())) {
            wrapper.like("application_code", flowRecordQueryVO.getApplicationCode());
        }
        // 校验 审批状态
        if (null != flowRecordQueryVO.getApproverStatus()) {
            wrapper.eq("approver_status", flowRecordQueryVO.getApproverStatus().intValue());
        }
        // 最后条件 + 倒序排序
        wrapper.eq("delete_flag", 0).orderBy("create_time", false);
        // 分页查询
        Page<BaseFlowRecord> page = new Page<>(flowRecordQueryVO.getPageNum(), flowRecordQueryVO.getPageSize());
        List<BaseFlowRecord> dataList = baseFlowRecordMapper.selectPage(page, wrapper);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<BaseFlowRecord> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(page.getTotal());
        tablePageVO.setPages(page.getPages());
        tablePageVO.setDataList(dataList);
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:29 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据单据编号，查询该单据对应的所有薪资列表
     **/
    @Override
    public ApiResult getSalaryInfoByApplicationCode(String applicationCode) {
        // 根据单据编号查询该薪资申请单对象
        SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
        salaryFlowBill.setApplicationCode(applicationCode);
        salaryFlowBill.setDeleteFlag(0);
        // 查询
        salaryFlowBill = salaryFlowBillMapper.selectOne(salaryFlowBill);
        // 校验
        if (null == salaryFlowBill) {
            return ApiResult.getFailedApiResponse("未查询到薪资单！");
        }
        // 获取薪资单id集合
        List<Long> stringList = (List<Long>) JSONArray.parse(salaryFlowBill.getUserSalaryIds());
        // 查询薪资单列表
        List<UserComputeResultVO> userComputeResultVOList = userSalaryMapper.selectUserListByIds(stringList);
        return ApiResult.getSuccessApiResponse(userComputeResultVOList);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 21:19 2020/9/27
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 处理当前节点(通过/驳回)
     **/
    @Override
    public ApiResult handleThisNode(FlowHandleParamVO handleParamVO, UserSessionVO userSessionVO) {
        // 查询到当前待办对象
        BaseFlowRecord baseFlowRecord = baseFlowRecordMapper.selectById(handleParamVO.getId());
        // 校验只能处理待审的单据
        if (null == baseFlowRecord || 0 != baseFlowRecord.getApproverStatus().intValue()) {
            return ApiResult.getFailedApiResponse("非待审状态的单据不可处理！");
        }
        // 查询审批明细对象
        BaseFlowConfigDetail detail = baseFlowConfigDetailMapper.selectById(baseFlowRecord.getBaseFlowConfigDetailId());
        // 校验
        if (null == detail) {
            return ApiResult.getFailedApiResponse("流程出现异常，该流程审批明细未查询到！");
        }
        // 这里先判断用户的操作类型   0--通过，1--驳回
        if (1 == handleParamVO.getApproverStatus()) {
            // TODO 驳回业务处理: 驳回到薪资核算人
            // 将流程记录表中所有的节点的流程全部删掉
            baseFlowRecordMapper.delete(Condition.create().eq("application_code", baseFlowRecord.getApplicationCode()).eq("flow_code", baseFlowRecord.getFlowCode()));
            // 将该单据状态改为驳回
            SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
            salaryFlowBill.setApplicationCode(baseFlowRecord.getApplicationCode());
            salaryFlowBill.setDeleteFlag(0);
            salaryFlowBill = salaryFlowBillMapper.selectOne(salaryFlowBill);
            // 将状态改为驳回    单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
            salaryFlowBill.setApplicationStatus(2);
            salaryFlowBill.setHandleId(userSessionVO.getId());
            salaryFlowBill.setHandleAccount(userSessionVO.getUserAccount());
            salaryFlowBill.setHandleName(userSessionVO.getUserName());
            salaryFlowBill.setHandleOpinion(handleParamVO.getOpinion());
            salaryFlowBill.setHandleDate(DateUtils.getNowDateString());
            // 修改
            salaryFlowBillMapper.updateById(salaryFlowBill);
            // 将该流程置为已结束
            baseFlowGenerateMapper.update(new BaseFlowGenerate() {{
                // 该流程状态：0--在审，1--审批完结，2--驳回，3--作废
                setApproverStatus(2);
            }}, Condition.create().eq("flow_code", salaryFlowBill.getFlowCode()).eq("application_code", salaryFlowBill.getApplicationCode()));
            // 将所有的薪资的  是否允许再次结算  改为允许    是否允许再次计算：0--允许，1--不允许
            List<Long> userSalaryIdList = (List<Long>) JSONArray.parse(salaryFlowBill.getUserSalaryIds());
            // 批量修改
            userSalaryMapper.update(new UserSalary() {{
                setAgainComputeFlag(0);
            }}, Condition.create().in("id", userSalaryIdList));
            // 驳回完毕
            return ApiResult.getSuccessApiResponse();
        }
        // TODO========审批通过：将该节点状态改为审批通过；将该流程单改为审批通过
        // 1、将处在该节点流程的所有待审改为审批通过
        baseFlowRecordMapper.update(new BaseFlowRecord() {{
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
            // 审批状态：0--待审，1--驳回，2--通过
            setApproverStatus(2);
            setApproverOpinion(handleParamVO.getOpinion());
        }}, Condition.create().eq("application_code", baseFlowRecord.getApplicationCode()).eq("flow_code", baseFlowRecord.getFlowCode())
                .eq("base_flow_config_detail_id", baseFlowRecord.getBaseFlowConfigDetailId()));
        // 2、判断当前流程节点是否为最后一个节点
        if (1 == detail.getLastFlag().intValue()) {
            // 是最后一个节点，直接将该节点处理成功！
            // 查询单据对象
            SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
            salaryFlowBill.setApplicationCode(baseFlowRecord.getApplicationCode());
            salaryFlowBill.setDeleteFlag(0);
            salaryFlowBill = salaryFlowBillMapper.selectOne(salaryFlowBill);
            // 将状态改为审批通过    单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
            salaryFlowBill.setApplicationStatus(3);
            salaryFlowBill.setHandleId(userSessionVO.getId());
            salaryFlowBill.setHandleAccount(userSessionVO.getUserAccount());
            salaryFlowBill.setHandleName(userSessionVO.getUserName());
            salaryFlowBill.setHandleOpinion(handleParamVO.getOpinion());
            salaryFlowBill.setHandleDate(DateUtils.getNowDateString());
            // 修改
            salaryFlowBillMapper.updateById(salaryFlowBill);
            // 将该流程置为已结束
            baseFlowGenerateMapper.update(new BaseFlowGenerate() {{
                // 该流程状态：0--在审，1--审批完结，2--驳回，3--作废
                setApproverStatus(1);
            }}, Condition.create().eq("flow_code", salaryFlowBill.getFlowCode()).eq("application_code", salaryFlowBill.getApplicationCode()));
            // TODO =======================员工年度累计个人所得税等计算赋值========================
            // 校验权限是否为  人力资源总监角色    到该角色的单子，通过后即进行年度累计金额累加
            List<Long> roleIdList = userSessionVO.getRoleIdList();
            // 校验角色
            if (CollectionUtils.isNotEmpty(roleIdList) && roleIdList.contains(Constants.FINANCE_ROLE_ID)) {
                // 给员工累加年度金额
                annualMoneyAccumulation(salaryFlowBill, userSessionVO);
            }
            return ApiResult.getSuccessApiResponse();
        }
        // 查询单据对象
        SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
        salaryFlowBill.setApplicationCode(baseFlowRecord.getApplicationCode());
        salaryFlowBill.setDeleteFlag(0);
        salaryFlowBill = salaryFlowBillMapper.selectOne(salaryFlowBill);
        salaryFlowBill.setHandleId(userSessionVO.getId());
        salaryFlowBill.setHandleAccount(userSessionVO.getUserAccount());
        salaryFlowBill.setHandleName(userSessionVO.getUserName());
        salaryFlowBill.setHandleOpinion(handleParamVO.getOpinion());
        salaryFlowBill.setHandleDate(DateUtils.getNowDateString());
        // 修改
        salaryFlowBillMapper.updateById(salaryFlowBill);
        // 不是最后一个节点，查询到下一个节点
        BaseFlowConfigDetail nextDetail = new BaseFlowConfigDetail();
        nextDetail.setBaseFlowConfigId(detail.getBaseFlowConfigId());
        nextDetail.setDeleteFlag(0);
        // 自增查询下一个节点
        Integer sortNum = detail.getSortNum();
        nextDetail.setSortNum(++sortNum);
        // 查询
        nextDetail = baseFlowConfigDetailMapper.selectOne(nextDetail);
        // 校验
        if (null == nextDetail) {
            return ApiResult.getFailedApiResponse("未查询到下一个流程节点！");
        }
        // 拆分出用户id集合
        List<Long> approverIdList = Arrays.asList(nextDetail.getApproverIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        List<String> approverNameList = Arrays.asList(nextDetail.getApproverNames().split(","));
        // 定义序号
        int number = 0;
        // 遍历 新增该节点流程记录表
        for (Long approverId : approverIdList) {
            // 定义对象并赋值
            BaseFlowRecord record = new BaseFlowRecord();
            record.setBaseFlowConfigId(nextDetail.getBaseFlowConfigId());
            record.setBaseFlowConfigDetailId(nextDetail.getId());
            record.setNodeName(nextDetail.getNodeName());
            record.setApproverId(approverId);
            record.setApproverName(approverNameList.get(number));
            record.setApplicationCode(baseFlowRecord.getApplicationCode());
            record.setFlowCode(baseFlowRecord.getFlowCode());
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
        return ApiResult.getSuccessApiResponse();
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 11:58 2020/10/15
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 私有方法----员工的年度金额累加
     **/
    private void annualMoneyAccumulation(SalaryFlowBill salaryFlowBill, UserSessionVO userSessionVO) {
        // 获取该单据关联的工资数据
        List<String> userSalaryIdList = Arrays.asList(salaryFlowBill.getUserSalaryIds());
        if (CollectionUtils.isNotEmpty(userSalaryIdList)) {
            List<UserSalary> userSalaryList = userSalaryMapper.selectBatchIds(userSalaryIdList);
            // 循环
            for (UserSalary userSalary : userSalaryList) {
                // 获取到用户
                UserDetail userDetail = new UserDetail();
                userDetail.setUserAccount(userSalary.getUserAccount());
                userDetail.setUserId(userSalary.getUserId());
                userDetail = userDetailMapper.selectOne(userDetail);
                // 校验
                if (null != userDetail) {
                    // 用户详情表赋值
                    // 赋值 年度累计收入金额 = 年度累计收入金额 + 预设银行代发工资金额
                    userDetail.setTotalIncomeMoney(userDetail.getTotalIncomeMoney().add(userDetail.getBankSalary()));
                    // 赋值 年度累计应纳税所得额 = 年度累计应纳税所得额 + 本月应纳税所得额
                    userDetail.setTotalIncomeMoney(userDetail.getTotalIncomeMoney().add(userSalary.getBankTaxableSelfMoney()));
                    // 赋值 年度累计已纳税额 = 年度累计已纳税额 + 本月已纳税额
                    userDetail.setTotalIncomeMoney(userDetail.getTotalIncomeMoney().add(userSalary.getBankRealityShouldTaxMoney()));
                    // 赋值 年度累计减除费用金额 = 年度累计减除费用金额 + 国家纳税起步金额
                    userDetail.setTotalDeductMoney(userDetail.getTotalDeductMoney().add(userDetail.getStipulationStartTaxMoney()));
                    // 赋值 年度累计专项附加扣除金额 = 年度累计专项附加扣除金额 + 本月专项附加扣除的总金额
                    userDetail.setTotalSpecialDeductMoney(userDetail.getTotalSpecialDeductMoney().add(userDetail.getSpecialDeductTotal()));
                    // 赋值 年度累计子女教育扣除金额 = 年度累计子女教育扣除金额 + 本月子女教育扣除金额
                    userDetail.setTotalChildEducation(userDetail.getTotalChildEducation().add(userDetail.getChildEducation()));
                    // 赋值 年度累计继续教育扣除金额 = 年度累计继续教育扣除金额 + 本月继续教育扣除金额
                    userDetail.setTotalContinueEducation(userDetail.getTotalContinueEducation().add(userDetail.getContinueEducation()));
                    // 赋值 年度累计住房贷款利息扣除金额 = 年度累计住房贷款利息扣除金额 + 本月住房贷款利息扣除金额
                    userDetail.setTotalHomeLoanInterest(userDetail.getTotalHomeLoanInterest().add(userDetail.getHomeLoanInterest()));
                    // 赋值 年度累计住房公积金扣除金额 = 年度累计住房公积金扣除金额 + 本月住房公积金扣除金额
                    userDetail.setTotalHomeRents(userDetail.getTotalHomeRents().add(userDetail.getHomeRents()));
                    // 赋值 年度累计赡养老人扣除金额 = 年度累计赡养老人扣除金额 + 本月赡养老人扣除金额
                    userDetail.setTotalSupportParents(userDetail.getTotalSupportParents().add(userDetail.getSupportParents()));
                    // 赋值 年度累计专项扣除金额（个人社保公积金部分） = 年度累计专项扣除金额 + 工资表  本月费用个人总计
                    userDetail.setTotalOtherDeduct(userDetail.getTotalOtherDeduct().add(userSalary.getMonthPersonPayTotal()));
                    userDetail.setEditId(userSessionVO.getUserAccount());
                    userDetail.setEditName(userSessionVO.getUserName());
                    userDetail.setEditTime(new Date());
                    // 更改入库
                    userDetailMapper.updateById(userDetail);
                }
            }
        }
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 22:30 2020/9/27
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 个人发起的流程列表
     **/
    @Override
    public ApiResult selectMineAgendaList(SalaryBillQueryVO salaryBillQueryVO, UserSessionVO userSessionVO) {
        // 条件构造
        Wrapper wrapper = Condition.create().eq("create_id", userSessionVO.getUserAccount());
        // 校验 单据编号
        if (StringUtils.isNotBlank(salaryBillQueryVO.getApplicationCode())) {
            wrapper.like("application_code", salaryBillQueryVO.getApplicationCode());
        }
        // 校验 审批状态
        if (null != salaryBillQueryVO.getApplicationStatus()) {
            wrapper.eq("application_status", salaryBillQueryVO.getApplicationStatus().intValue());
        }
        // 最后条件 + 倒序排序
        wrapper.eq("delete_flag", 0).orderBy("create_time", false);
        // 分页查询
        Page<SalaryFlowBill> page = new Page<>(salaryBillQueryVO.getPageNum(), salaryBillQueryVO.getPageSize());
        List<SalaryFlowBill> dataList = salaryFlowBillMapper.selectPage(page, wrapper);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<SalaryFlowBill> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(page.getTotal());
        tablePageVO.setPages(page.getPages());
        tablePageVO.setDataList(dataList);
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:42 2020/10/13
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询该流程工资列表
     **/
    @Override
    public ApiResult selectSalaryByApplicationCode(String applicationCode, UserSessionVO userSessionVO) {
        SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
        salaryFlowBill.setApplicationCode(applicationCode);
        salaryFlowBill.setDeleteFlag(0);
        // 查询
        salaryFlowBill = salaryFlowBillMapper.selectOne(salaryFlowBill);
        // 校验
        if (null == salaryFlowBill) {
            return ApiResult.getFailedApiResponse("未查询到该单据信息！");
        }
        // 获取薪资表id集合
        String userSalaryIds = salaryFlowBill.getUserSalaryIds();
        if (StringUtils.isBlank(userSalaryIds)) {
            return ApiResult.getSuccessApiResponse("该单据未关联薪资数据！");
        }
        List<Long> salaryIdList = (List<Long>) JSONArray.parse(salaryFlowBill.getUserSalaryIds());
        // 查询薪资表数据信息
        List<UserComputeResultVO> userComputeResultVOList = userSalaryMapper.selectUserListByIds(salaryIdList);
        return ApiResult.getSuccessApiResponse(userComputeResultVOList);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:59 2020/10/15
     * @Param 
     * @return 
     * @Version 1.0
     * @Description //TODO 汇总待办列表
     **/
    @Override
    public ApiResult collectTheMonthSalaryFlow(String ids, UserSessionVO userSessionVO) {
        // 校验角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (!roleIdList.contains(Constants.FINANCE_ROLE_ID)) {
            return ApiResult.getFailedApiResponse("您无权汇总待办流程数据！");
        }
        // 查询到各个流程单据数据
        List<String> stringList = Arrays.asList(ids.split(","));
        // 查询集合
        List<SalaryFlowBill> flowBillList = salaryFlowBillMapper.selectBatchIds(stringList);
        // 校验
        if (CollectionUtils.isEmpty(flowBillList)) {
            return ApiResult.getFailedApiResponse("未查询到流程单数据！");
        }
        // TODO 校验他们是否为同一个月份
        // 根据月份去重
        List<SalaryFlowBill> collectBillList = flowBillList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(SalaryFlowBill::getSalaryDate))), ArrayList::new));
        if (collectBillList.size() > 1) {
            return ApiResult.getFailedApiResponse("请选择同一个月份的流程单据汇总！");
        }
        // 校验是否是全部的单据
        // 查询所有薪资归属部门集合
        Integer salaryDeptCount = salaryDeptMapper.selectCount(Condition.create().eq("delete_flag", 0));
        // 拿薪资归属部门条数  +  1   （管理岗也是一条流程）
        int thisCount = salaryDeptCount.intValue() + 1;
        // 校验
        if (thisCount != flowBillList.size()) {
            return ApiResult.getFailedApiResponse("请等待本月各部门所有单据一并汇总！");
        }
        // 合并成一个新的
        // 定义一个存放薪资表id的集合
        List<Long> salaryIdList = Lists.newArrayList();
        // 循环取值
        for (SalaryFlowBill flowBill : flowBillList) {
            salaryIdList.addAll((List<Long>) JSONArray.parse(flowBill.getUserSalaryIds()));
        }
        // 获取流程
        // 查询人力资源总监角色的流程
        BaseFlowConfig baseFlowConfig = new BaseFlowConfig();
        baseFlowConfig.setDeleteFlag(0);
        baseFlowConfig.setUseFlag(0);
        baseFlowConfig.setFlowRoleId(Constants.FINANCE_ROLE_ID);
        baseFlowConfig = baseFlowConfigMapper.selectOne(baseFlowConfig);
        // 校验
        if (null == baseFlowConfig) {
            return ApiResult.getFailedApiResponse("未查询到您所属薪资审批流程！");
        }
        Long flowConfigId = baseFlowConfig.getId();
        // 重新生成新单据
        // 获取  汇总薪资单据编号
        String applicationCode = baseService.getBillCodeByBillType(2);
        // 获取流程code
        String flowCode = baseService.getFlowCodeByApplicationCode(applicationCode);
        SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
        // 赋值
        salaryFlowBill.setSalaryDate(flowBillList.get(0).getSalaryDate());
        salaryFlowBill.setApplicationCode(applicationCode);
        salaryFlowBill.setFlowCode(flowCode);
        salaryFlowBill.setBaseFlowConfigId(flowConfigId);
        salaryFlowBill.setApplicationType(baseFlowConfig.getFlowType());
        salaryFlowBill.setDeleteFlag(0);
        salaryFlowBill.setCreateId(userSessionVO.getUserAccount());
        salaryFlowBill.setCreateName(userSessionVO.getUserName());
        salaryFlowBill.setCreateTime(new Date());
        salaryFlowBill.setEditTime(new Date());
        salaryFlowBill.setSalaryDeptId(888888L);// 人力资源总监这里声明薪资归属部门id为 888888L
        salaryFlowBill.setRoleId(Constants.FINANCE_ROLE_ID);
        salaryFlowBill.setRoleName("人力资源总监");
        // 单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
        salaryFlowBill.setApplicationStatus(1);
        salaryFlowBill.setUserSalaryIds(JSONArray.toJSONString(salaryIdList));
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
        List<String> approverNameList = Arrays.asList(configDetail.getApproverNames().split(","));
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
}
