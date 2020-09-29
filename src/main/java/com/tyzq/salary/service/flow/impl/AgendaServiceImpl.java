package com.tyzq.salary.service.flow.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.BaseFlowConfigDetailMapper;
import com.tyzq.salary.mapper.BaseFlowRecordMapper;
import com.tyzq.salary.mapper.SalaryFlowBillMapper;
import com.tyzq.salary.mapper.UserSalaryMapper;
import com.tyzq.salary.model.BaseFlowConfigDetail;
import com.tyzq.salary.model.BaseFlowRecord;
import com.tyzq.salary.model.SalaryFlowBill;
import com.tyzq.salary.model.UserSalary;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.flow.FlowHandleParamVO;
import com.tyzq.salary.model.vo.flow.FlowRecordQueryVO;
import com.tyzq.salary.model.vo.flow.SalaryBillQueryVO;
import com.tyzq.salary.model.vo.salary.UserComputeResultVO;
import com.tyzq.salary.service.flow.AgendaService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
        // 校验权限是否为总经理/副总角色
        List<Long> roleIdList = userSessionVO.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList) || (!roleIdList.contains(Constants.ADMIN_ROLE_ID) && !roleIdList.contains(Constants.OTHER_ROLE_ID))) {
            return ApiResult.getFailedApiResponse("您无权查看流程配置信息！");
        }
        // 条件构造
        Wrapper wrapper = Condition.create().eq("approver_id", userSessionVO.getId()).eq("approver_status", 0);
        // 校验 流程编号
        if (StringUtils.isNotBlank(flowRecordQueryVO.getApplicationCode())) {
            wrapper.like("application_code", flowRecordQueryVO.getApplicationCode());
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
        List<String> stringList = Arrays.asList(salaryFlowBill.getUserSalaryIds());
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
     * @Description //TODO 处理当前节点
     **/
    @Override
    public ApiResult handleThisNode(FlowHandleParamVO handleParamVO, UserSessionVO userSessionVO) {
        // 查询到当前待办对象
        BaseFlowRecord baseFlowRecord = baseFlowRecordMapper.selectById(handleParamVO.getId());
        // 查询审批明细对象
        BaseFlowConfigDetail detail = baseFlowConfigDetailMapper.selectById(baseFlowRecord.getBaseFlowConfigDetailId());
        // 校验
        if (null == detail) {
            return ApiResult.getFailedApiResponse("流程出现异常！");
        }
        // 这里先判断用户的操作类型   0--通过，1--驳回
        if (1 == handleParamVO.getApproverStatus()) {
            // 驳回业务处理
            // 查询单据对象
            SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
            salaryFlowBill.setApplicationCode(baseFlowRecord.getApplicationCode());
            salaryFlowBill.setDeleteFlag(0);
            salaryFlowBill = salaryFlowBillMapper.selectOne(salaryFlowBill);
            // 将状态改为驳回    单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
            salaryFlowBill.setApplicationStatus(2);
            // 修改
            salaryFlowBillMapper.updateById(salaryFlowBill);
            // 将处在该节点流程的所有待审改为  驳回
            baseFlowRecordMapper.update(new BaseFlowRecord() {{
                setEditId(userSessionVO.getUserAccount());
                setEditName(userSessionVO.getUserName());
                setEditTime(new Date());
                // 审批状态：0--待审，1--驳回，2--通过
                setApproverStatus(1);
            }}, Condition.create().eq("application_code", baseFlowRecord.getApplicationCode()).eq("flow_code", baseFlowRecord.getFlowCode())
                    .eq("base_flow_config_detail_id", baseFlowRecord.getBaseFlowConfigDetailId()));
            return ApiResult.getSuccessApiResponse();
        }
        // 审批通过
        // 一、将处在该节点流程的所有待审改为审批通过
        baseFlowRecordMapper.update(new BaseFlowRecord() {{
            setEditId(userSessionVO.getUserAccount());
            setEditName(userSessionVO.getUserName());
            setEditTime(new Date());
            // 审批状态：0--待审，1--驳回，2--通过
            setApproverStatus(2);
        }}, Condition.create().eq("application_code", baseFlowRecord.getApplicationCode()).eq("flow_code", baseFlowRecord.getFlowCode())
                .eq("base_flow_config_detail_id", baseFlowRecord.getBaseFlowConfigDetailId()));

        // 判断当前流程节点是否为最后一个节点
        if (1 == detail.getLastFlag().intValue()) {
            // 直接将该节点处理成功！
            // 查询单据对象
            SalaryFlowBill salaryFlowBill = new SalaryFlowBill();
            salaryFlowBill.setApplicationCode(baseFlowRecord.getApplicationCode());
            salaryFlowBill.setDeleteFlag(0);
            salaryFlowBill = salaryFlowBillMapper.selectOne(salaryFlowBill);
            // 将状态改为驳回    单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
            salaryFlowBill.setApplicationStatus(3);
            // 修改
            salaryFlowBillMapper.updateById(salaryFlowBill);
            // 将所有的薪资的  是否允许再次结算  改为允许    是否允许再次计算：0--允许，1--不允许
            List<String> stringList = Arrays.asList(salaryFlowBill.getUserSalaryIds());
            // 批量修改
            userSalaryMapper.update(new UserSalary() {{
                setAgainComputeFlag(0);
            }}, Condition.create().in("id", stringList));

            // TODO =======================员工年度累计个人所得税等计算赋值========================
            return ApiResult.getSuccessApiResponse();
        } else {
            // 查询到下一个节点
            BaseFlowConfigDetail nextDetail = new BaseFlowConfigDetail();
            nextDetail.setBaseFlowConfigId(detail.getBaseFlowConfigId());
            nextDetail.setDeleteFlag(0);
            // 自增查询下一个节点
            Integer sortNum = detail.getSortNum();
            nextDetail.setSortNum(sortNum++);
            // 查询
            nextDetail = baseFlowConfigDetailMapper.selectOne(nextDetail);
            // 校验
            if (null == nextDetail) {
                return ApiResult.getFailedApiResponse("未查询到下一个流程节点！");
            }
            // 拆分出用户id集合
            List<Long> approverIdList = Arrays.asList(nextDetail.getApproverIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
            List<String> approverNameList = Arrays.asList(nextDetail.getApproverIds().split(","));
            // 定义序号
            int number = 0;
            // 遍历
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
        Wrapper wrapper = Condition.create().eq("handle_id", userSessionVO.getId());
        // 校验 单据编号
        if (StringUtils.isNotBlank(salaryBillQueryVO.getApplicationCode())) {
            wrapper.like("application_code", salaryBillQueryVO.getApplicationCode());
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
}
