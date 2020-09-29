package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 流程配置明细表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-27
 */
public class BaseFlowConfigDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 流程配置主表id
     */
    private Long baseFlowConfigId;
    /**
     * 审批序号
     */
    private Integer sortNum;
    /**
     * 是否第一个节点：0--不是，1--是
     */
    private Integer firstFlag;
    /**
     * 是否最后一个节点：0--不是，1--是
     */
    private Integer lastFlag;
    /**
     * 受理人ids字符串，多个用英文逗号分隔
     */
    private String approverIds;
    /**
     * 受理人名称字符串，多个用英文逗号分隔
     */
    private String approverNames;
    /**
     * 节点名称
     */
    private String nodeName;
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

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getFirstFlag() {
        return firstFlag;
    }

    public void setFirstFlag(Integer firstFlag) {
        this.firstFlag = firstFlag;
    }

    public Integer getLastFlag() {
        return lastFlag;
    }

    public void setLastFlag(Integer lastFlag) {
        this.lastFlag = lastFlag;
    }

    public String getApproverIds() {
        return approverIds;
    }

    public void setApproverIds(String approverIds) {
        this.approverIds = approverIds;
    }

    public String getApproverNames() {
        return approverNames;
    }

    public void setApproverNames(String approverNames) {
        this.approverNames = approverNames;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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
        return "BaseFlowConfigDetail{" +
        "id=" + id +
        ", baseFlowConfigId=" + baseFlowConfigId +
        ", sortNum=" + sortNum +
        ", firstFlag=" + firstFlag +
        ", lastFlag=" + lastFlag +
        ", approverIds=" + approverIds +
        ", approverNames=" + approverNames +
        ", nodeName=" + nodeName +
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
