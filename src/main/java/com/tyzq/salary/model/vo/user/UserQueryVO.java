package com.tyzq.salary.model.vo.user;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-09 15:20
 * @Description: //TODO 根据条件查询本事业部门内的员工信息
 **/
public class UserQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户名称*/
    private String userName;

    /* 用户所属薪资核算部门ID*/
    private Long userSalaryDeptId;

    /* 员工在职情况：0--试用期，1--正式，2--离职*/
    private Integer userRankType;

    /* 员工岗位类型：0--管理岗，1--成本岗，2--技术岗*/
    private Integer userPostType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserSalaryDeptId() {
        return userSalaryDeptId;
    }

    public void setUserSalaryDeptId(Long userSalaryDeptId) {
        this.userSalaryDeptId = userSalaryDeptId;
    }

    public Integer getUserRankType() {
        return userRankType;
    }

    public void setUserRankType(Integer userRankType) {
        this.userRankType = userRankType;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
    }
}
