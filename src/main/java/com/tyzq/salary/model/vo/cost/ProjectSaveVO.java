package com.tyzq.salary.model.vo.cost;

import com.tyzq.salary.model.Project;
import com.tyzq.salary.model.ProjectUser;

import java.io.Serializable;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:11 2021/2/4
 * @Description: //TODO 新增修改项目参数VO
 **/
public class ProjectSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 项目对象实体*/
    private Project project;

    /* 项目人员对象集合 可为空*/
    private List<ProjectUser> projectUserList;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectUser> getProjectUserList() {
        return projectUserList;
    }

    public void setProjectUserList(List<ProjectUser> projectUserList) {
        this.projectUserList = projectUserList;
    }
}
