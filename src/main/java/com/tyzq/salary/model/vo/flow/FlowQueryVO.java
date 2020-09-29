package com.tyzq.salary.model.vo.flow;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 15:16
 * @Description: //TODO 流程主表查询参数VO
 **/
public class FlowQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String flowEnCode;

    public String getFlowEnCode() {
        return flowEnCode;
    }

    public void setFlowEnCode(String flowEnCode) {
        this.flowEnCode = flowEnCode;
    }
}
