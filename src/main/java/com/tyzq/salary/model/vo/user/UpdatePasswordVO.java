package com.tyzq.salary.model.vo.user;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-10-12 10:56
 * @Description: //TODO 修改密码参数VO
 **/
public class UpdatePasswordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    * 修改类别：
    * updateFlag == 1  （管理员权限）为重置密码，只需要 account 即可，将新生成密码放入返回结果对象
    * updateFlag == 2  为修改密码，必传【account  oldPassword  newPassword confirmPassword】
    * updateFlag == 3  (管理员权限) 为修改账号并重置密码，必传【account  newAccount】将新生成密码放入返回结果对象
    *  默认为2
    * */
    private int updateFlag = 2;

    /* 用户账号*/
    private String account;

    /* 旧密码*/
    private String oldPassword;

    /* 新密码*/
    private String newPassword;

    /* 确认密码*/
    private String confirmPassword;

    /* 用户新账号*/
    private String newAccount;

    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(String newAccount) {
        this.newAccount = newAccount;
    }
}
