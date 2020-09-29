package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 16:37
 * @Description: //TODO 社保等级表查询VO
 **/
public class SocialSecurityQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 社保等级名称*/
    private String socialSecurityLevel;

    public String getSocialSecurityLevel() {
        return socialSecurityLevel;
    }

    public void setSocialSecurityLevel(String socialSecurityLevel) {
        this.socialSecurityLevel = socialSecurityLevel;
    }
}
