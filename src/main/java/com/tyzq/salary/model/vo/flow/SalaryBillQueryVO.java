package com.tyzq.salary.model.vo.flow;

import com.tyzq.salary.model.vo.base.PageUtilVO;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 22:28
 * @Description: //TODO 查询个人发起的流程列表参数VO
 **/
public class SalaryBillQueryVO extends PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 单据编号*/
    private String applicationCode;

    /* 单据状态：0--未提交，1--审批中，2--驳回，3--审批通过，4--作废*/
    private Integer applicationStatus;

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Integer applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
