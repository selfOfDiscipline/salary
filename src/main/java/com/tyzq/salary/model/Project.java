package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2021-02-02
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    private String projectCode;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 业务归属部门id
     */
    private Long deptId;
    /**
     * 业务归属部门名称
     */
    private String deptName;
    /**
     * 客户公司id
     */
    private Long companyId;
    /**
     * 客户公司名称
     */
    private String companyName;
    /**
     * 合同开始日期
     */
    private Date contractStartDate;
    /**
     * 合同结束日期
     */
    private Date contractEndDate;
    /**
     * 项目类型：0--外包，1--总包，2--自研，3--委托制
     */
    private Integer projectType;
    /**
     * 项目状态：0--正常，1--完结，2--废弃
     */
    private Integer projectStatus;
    /**
     * 项目总金额
     */
    private BigDecimal totalMoney;
    /**
     * 委托方金额
     */
    private BigDecimal clientMoney;
    /**
     * 项目完成总进度
     */
    private BigDecimal projectFinishRatio;
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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getClientMoney() {
        return clientMoney;
    }

    public void setClientMoney(BigDecimal clientMoney) {
        this.clientMoney = clientMoney;
    }

    public BigDecimal getProjectFinishRatio() {
        return projectFinishRatio;
    }

    public void setProjectFinishRatio(BigDecimal projectFinishRatio) {
        this.projectFinishRatio = projectFinishRatio;
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
        return "Project{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", projectName=" + projectName +
        ", deptId=" + deptId +
        ", deptName=" + deptName +
        ", companyId=" + companyId +
        ", companyName=" + companyName +
        ", contractStartDate=" + contractStartDate +
        ", contractEndDate=" + contractEndDate +
        ", projectType=" + projectType +
        ", projectStatus=" + projectStatus +
        ", totalMoney=" + totalMoney +
        ", clientMoney=" + clientMoney +
        ", projectFinishRatio=" + projectFinishRatio +
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
