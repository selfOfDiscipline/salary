package com.tyzq.salary.model.vo.cost;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 9:33 2021/2/3
 * @Description: //TODO 查询项目列表接口 参数VO
 **/
public class ProjectQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 项目名称*/
    private String projectName;

    /* 用户业务归属部门id*/
    private Long deptId;

    /**
     * 客户公司id
     */
    private Long companyId;

    /**
     * 合同开始日期，区间开始：格式："yyyy-MM-dd" 示例："2021-02-03"
     */
    private String contractStartDateBegin;

    /**
     * 合同开始日期，区间结束：格式："yyyy-MM-dd" 示例："2021-02-03"
     */
    private String contractStartDateEnd;

    /**
     * 合同结束日期，区间开始：格式："yyyy-MM-dd" 示例："2021-02-03"
     */
    private String contractEndDateBegin;

    /**
     * 合同结束日期，区间结束：格式："yyyy-MM-dd" 示例："2021-02-03"
     */
    private String contractEndDateEnd;

    /**
     * 项目类型：0--外包，1--总包，2--自研，3--委托制
     */
    private Integer projectType;

    /**
     * 项目状态：0--正常，1--完结，2--废弃
     */
    private Integer projectStatus;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getContractStartDateBegin() {
        return contractStartDateBegin;
    }

    public void setContractStartDateBegin(String contractStartDateBegin) {
        this.contractStartDateBegin = contractStartDateBegin;
    }

    public String getContractStartDateEnd() {
        return contractStartDateEnd;
    }

    public void setContractStartDateEnd(String contractStartDateEnd) {
        this.contractStartDateEnd = contractStartDateEnd;
    }

    public String getContractEndDateBegin() {
        return contractEndDateBegin;
    }

    public void setContractEndDateBegin(String contractEndDateBegin) {
        this.contractEndDateBegin = contractEndDateBegin;
    }

    public String getContractEndDateEnd() {
        return contractEndDateEnd;
    }

    public void setContractEndDateEnd(String contractEndDateEnd) {
        this.contractEndDateEnd = contractEndDateEnd;
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
}
