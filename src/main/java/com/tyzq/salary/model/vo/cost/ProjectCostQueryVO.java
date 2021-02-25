package com.tyzq.salary.model.vo.cost;

import java.io.Serializable;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 15:46 2021/2/24
 * @Description: //TODO 项目成本详情查询参数VO
 **/
public class ProjectCostQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 项目编号*/
    private String projectCode;

    /* 成本归属日期，格式：yyyy-MM*/
    private String costDate;

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
}
