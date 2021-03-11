package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 薪资审批单据表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2021-03-10
 */
public class SalaryFlowBill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 单据编号
     */
    private String applicationCode;
    /**
     * 薪资归属日期
     */
    private Date salaryDate;
    /**
     * 关联流程主表id
     */
    private Long baseFlowConfigId;
    /**
     * 流程code
     */
    private String flowCode;
    /**
     * 单据类型
     */
    private String applicationType;
    /**
     * 薪资归属部门id
     */
    private Long salaryDeptId;
    /**
     * 薪资归属部门名称
     */
    private String salaryDeptName;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废
     */
    private Integer applicationStatus;
    /**
     * 处理人id
     */
    private Long handleId;
    /**
     * 处理人账号
     */
    private String handleAccount;
    /**
     * 处理人名称
     */
    private String handleName;
    /**
     * 处理意见
     */
    private String handleOpinion;
    /**
     * 处理时间
     */
    private String handleDate;
    /**
     * 薪资表id字符串，多个用英文逗号分隔
     */
    private String userSalaryIds;
    /**
     * 是否被汇总：0--否，1--是
     */
    private Integer collectFlag;
    /**
     * 是否删除：0--正常，1--已删除
     */
    private Integer deleteFlag;
    /**
     * 创建人账号
     */
    private String createId;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人账号
     */
    private String editId;
    /**
     * 修改人名称
     */
    private String editName;
    /**
     * 修改时间
     */
    private Date editTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    public Long getBaseFlowConfigId() {
        return baseFlowConfigId;
    }

    public void setBaseFlowConfigId(Long baseFlowConfigId) {
        this.baseFlowConfigId = baseFlowConfigId;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Long getSalaryDeptId() {
        return salaryDeptId;
    }

    public void setSalaryDeptId(Long salaryDeptId) {
        this.salaryDeptId = salaryDeptId;
    }

    public String getSalaryDeptName() {
        return salaryDeptName;
    }

    public void setSalaryDeptName(String salaryDeptName) {
        this.salaryDeptName = salaryDeptName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Integer applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getHandleId() {
        return handleId;
    }

    public void setHandleId(Long handleId) {
        this.handleId = handleId;
    }

    public String getHandleAccount() {
        return handleAccount;
    }

    public void setHandleAccount(String handleAccount) {
        this.handleAccount = handleAccount;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getHandleOpinion() {
        return handleOpinion;
    }

    public void setHandleOpinion(String handleOpinion) {
        this.handleOpinion = handleOpinion;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getUserSalaryIds() {
        return userSalaryIds;
    }

    public void setUserSalaryIds(String userSalaryIds) {
        this.userSalaryIds = userSalaryIds;
    }

    public Integer getCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(Integer collectFlag) {
        this.collectFlag = collectFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    @Override
    public String toString() {
        return "SalaryFlowBill{" +
        "id=" + id +
        ", applicationCode=" + applicationCode +
        ", salaryDate=" + salaryDate +
        ", baseFlowConfigId=" + baseFlowConfigId +
        ", flowCode=" + flowCode +
        ", applicationType=" + applicationType +
        ", salaryDeptId=" + salaryDeptId +
        ", salaryDeptName=" + salaryDeptName +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", applicationStatus=" + applicationStatus +
        ", handleId=" + handleId +
        ", handleAccount=" + handleAccount +
        ", handleName=" + handleName +
        ", handleOpinion=" + handleOpinion +
        ", handleDate=" + handleDate +
        ", userSalaryIds=" + userSalaryIds +
        ", collectFlag=" + collectFlag +
        ", deleteFlag=" + deleteFlag +
        ", createId=" + createId +
        ", createName=" + createName +
        ", createTime=" + createTime +
        ", editId=" + editId +
        ", editName=" + editName +
        ", editTime=" + editTime +
        "}";
    }
}
