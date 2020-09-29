package com.tyzq.salary.model.vo.flow;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 20:19
 * @Description: //TODO 流程查询参数VO
 **/
public class FlowRecordQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 单据编号*/
    private String applicationCode;

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }
}
