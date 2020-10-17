package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 流程处理结果记录表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-27
 */
public class BaseFlowRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 流程主表id
     */
    private Long baseFlowConfigId;
    /**
     * 流程明细表id
     */
    private Long baseFlowConfigDetailId;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 处理人id
     */
    private Long approverId;
    /**
     * 处理人账号
     */
    private String approverAccount;
    /**
     * 处理人名称
     */
    private String approverName;
    /**
     * 流程编号
     */
    private String flowCode;
    /**
     * 单据编号
     */
    private String applicationCode;
    /**
     * 审批状态：0--待审，1--驳回，2--通过
     */
    private Integer approverStatus;
    /**
     * 审批意见
     */
    private String approverOpinion;
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

    public Long getBaseFlowConfigId() {
        return baseFlowConfigId;
    }

    public void setBaseFlowConfigId(Long baseFlowConfigId) {
        this.baseFlowConfigId = baseFlowConfigId;
    }

    public Long getBaseFlowConfigDetailId() {
        return baseFlowConfigDetailId;
    }

    public void setBaseFlowConfigDetailId(Long baseFlowConfigDetailId) {
        this.baseFlowConfigDetailId = baseFlowConfigDetailId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getApproverAccount() {
        return approverAccount;
    }

    public void setApproverAccount(String approverAccount) {
        this.approverAccount = approverAccount;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public Integer getApproverStatus() {
        return approverStatus;
    }

    public void setApproverStatus(Integer approverStatus) {
        this.approverStatus = approverStatus;
    }

    public String getApproverOpinion() {
        return approverOpinion;
    }

    public void setApproverOpinion(String approverOpinion) {
        this.approverOpinion = approverOpinion;
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
        return "BaseFlowRecord{" +
        "id=" + id +
        ", baseFlowConfigId=" + baseFlowConfigId +
        ", baseFlowConfigDetailId=" + baseFlowConfigDetailId +
        ", nodeName=" + nodeName +
        ", approverId=" + approverId +
        ", approverAccount=" + approverAccount +
        ", approverName=" + approverName +
        ", flowCode=" + flowCode +
        ", applicationCode=" + applicationCode +
        ", approverStatus=" + approverStatus +
        ", approverOpinion=" + approverOpinion +
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
