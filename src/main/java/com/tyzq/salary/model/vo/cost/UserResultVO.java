package com.tyzq.salary.model.vo.cost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 17:48 2021/2/2
 * @Description: //TODO 员工列表（成本中心使用）
 **/
public class UserResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 员工岗位类型：0--管理岗，1--成本岗，2--技术岗， 3--外协
     */
    private Integer userPostType;
    /**
     * 业务归属部门id
     */
    private Long userDeptId;
    /**
     * 业务归属部门名称
     */
    private String userDeptName;
    /**
     * 职位，多个用英文逗号分隔
     */
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getUserPostType() {
        return userPostType;
    }

    public void setUserPostType(Integer userPostType) {
        this.userPostType = userPostType;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
