package com.tyzq.salary.model.vo.salary;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-22 09:20
 * @Description: //TODO 查询计薪列表VO
 **/
public class UserComputeSalaryQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户名称*/
    private String userName;

    /* 用户所属薪资核算部门ID*/
    private Long userSalaryDeptId;

    /* 日期区间开始*/
    private Date thisDateMonth;

    /* 日期区间结束*/
    private Date thisDateLastMonth;

    /* 员工岗位类型：0--管理岗，1--成本岗，2--技术岗*/
    private Integer userPostType;

    /* 查询的菜单类型  menuType==0为上月入职员工计薪；menuType==1为上月转正员工计薪；menuType==2为正常员工计薪【默认为2】*/
    private int menuType = 2;

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

    public Date getThisDateMonth() {
        return thisDateMonth;
    }

    public void setThisDateMonth(Date thisDateMonth) {
        this.thisDateMonth = thisDateMonth;
    }

    public Date getThisDateLastMonth() {
        return thisDateLastMonth;
    }

    public void setThisDateLastMonth(Date thisDateLastMonth) {
        this.thisDateLastMonth = thisDateLastMonth;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
    }
}
