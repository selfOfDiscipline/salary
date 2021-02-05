package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 项目人员表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2021-02-04
 */
public class ProjectUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * OA账号
     */
    private String userAccount;
    /**
     * 员工名称
     */
    private String userName;
    /**
     * 员工业务归属部门id
     */
    private Long userDeptId;
    /**
     * 员工业务归属部门名称
     */
    private String userDeptName;
    /**
     * 项目编号
     */
    private String projectCode;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 员工角色，多个英文逗号分开
     */
    private String userRoleNames;
    /**
     * 员工岗位类型：0--管理岗，1--成本岗，2--技术岗，3--外协
     */
    private Integer userPostType;
    /**
     * 入项日期
     */
    private Date entryDate;
    /**
     * 收入类型：0--天结，1--月结
     */
    private Integer earningType;
    /**
     * 员工状态：0--正常，1--完结
     */
    private Integer userStatus;
    /**
     * 员工来源：0--系统获取，1--手工录入
     */
    private Integer userSource;
    /**
     * 收入金额
     */
    private BigDecimal earningMoney;
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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
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

    public String getUserRoleNames() {
        return userRoleNames;
    }

    public void setUserRoleNames(String userRoleNames) {
        this.userRoleNames = userRoleNames;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getEarningType() {
        return earningType;
    }

    public void setEarningType(Integer earningType) {
        this.earningType = earningType;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserSource() {
        return userSource;
    }

    public void setUserSource(Integer userSource) {
        this.userSource = userSource;
    }

    public BigDecimal getEarningMoney() {
        return earningMoney;
    }

    public void setEarningMoney(BigDecimal earningMoney) {
        this.earningMoney = earningMoney;
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
        return "ProjectUser{" +
        "id=" + id +
        ", userAccount=" + userAccount +
        ", userName=" + userName +
        ", userDeptId=" + userDeptId +
        ", userDeptName=" + userDeptName +
        ", projectCode=" + projectCode +
        ", projectName=" + projectName +
        ", userRoleNames=" + userRoleNames +
        ", userPostType=" + userPostType +
        ", entryDate=" + entryDate +
        ", earningType=" + earningType +
        ", userStatus=" + userStatus +
        ", userSource=" + userSource +
        ", earningMoney=" + earningMoney +
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
