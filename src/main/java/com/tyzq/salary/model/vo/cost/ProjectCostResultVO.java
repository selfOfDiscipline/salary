package com.tyzq.salary.model.vo.cost;

import com.tyzq.salary.model.Project;
import com.tyzq.salary.model.ProjectCost;

import java.io.Serializable;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 9:30 2021/2/25
 * @Description: //TODO 项目成本计算页面的参数VO
 **/
public class ProjectCostResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 项目表对象*/
    private Project project;

    /* 项目成本对象集合*/
    private List<ProjectCost> projectCostList;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectCost> getProjectCostList() {
        return projectCostList;
    }

    public void setProjectCostList(List<ProjectCost> projectCostList) {
        this.projectCostList = projectCostList;
    }
}
