package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 16:21
 * @Description: //TODO 绩效查询VO
 **/
public class PerformanceQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 绩效名称*/
    private String performanceName;

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }
}
