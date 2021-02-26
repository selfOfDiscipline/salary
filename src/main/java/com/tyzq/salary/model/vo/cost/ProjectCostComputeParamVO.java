package com.tyzq.salary.model.vo.cost;

import com.tyzq.salary.model.ProjectCost;

import java.io.Serializable;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:42 2021/2/26
 * @Description: //TODO 计算项目明细成本接口参数VO
 **/
public class ProjectCostComputeParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 项目编号*/
    private String projectCode;

    /* 计算日期*/
    private String costDate;

    /* 项目成本对象*/
    private ProjectCost projectCost;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public ProjectCost getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(ProjectCost projectCost) {
        this.projectCost = projectCost;
    }
}
