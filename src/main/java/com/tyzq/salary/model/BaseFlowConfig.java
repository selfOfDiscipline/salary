package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 流程配置主表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-27
 */
public class BaseFlowConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 流程编号
     */
    private String flowEnCode;
    /**
     * 流程类型
     */
    private String flowType;
    /**
     * 流程名称
     */
    private String flowName;
    /**
     * 流程适用部门id
     */
    private Long flowSalaryDeptId;
    /**
     * 流程适用部门名称
     */
    private String flowSalaryDeptName;
    /**
     * 流程适用角色id
     */
    private Long flowRoleId;
    /**
     * 流程适用角色名称
     */
    private String flowRoleName;
    /**
     * 是否启用：0--启用，1--停用
     */
    private Integer useFlag;
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

    public String getFlowEnCode() {
        return flowEnCode;
    }

    public void setFlowEnCode(String flowEnCode) {
        this.flowEnCode = flowEnCode;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public Long getFlowSalaryDeptId() {
        return flowSalaryDeptId;
    }

    public void setFlowSalaryDeptId(Long flowSalaryDeptId) {
        this.flowSalaryDeptId = flowSalaryDeptId;
    }

    public String getFlowSalaryDeptName() {
        return flowSalaryDeptName;
    }

    public void setFlowSalaryDeptName(String flowSalaryDeptName) {
        this.flowSalaryDeptName = flowSalaryDeptName;
    }

    public Long getFlowRoleId() {
        return flowRoleId;
    }

    public void setFlowRoleId(Long flowRoleId) {
        this.flowRoleId = flowRoleId;
    }

    public String getFlowRoleName() {
        return flowRoleName;
    }

    public void setFlowRoleName(String flowRoleName) {
        this.flowRoleName = flowRoleName;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
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
        return "BaseFlowConfig{" +
        "id=" + id +
        ", flowEnCode=" + flowEnCode +
        ", flowType=" + flowType +
        ", flowName=" + flowName +
        ", flowSalaryDeptId=" + flowSalaryDeptId +
        ", flowSalaryDeptName=" + flowSalaryDeptName +
        ", flowRoleId=" + flowRoleId +
        ", flowRoleName=" + flowRoleName +
        ", useFlag=" + useFlag +
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
