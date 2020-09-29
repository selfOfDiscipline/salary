package com.tyzq.salary.model.vo.flow;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 21:15
 * @Description: //TODO 流程处理参数VO
 **/
public class FlowHandleParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 待办表id*/
    private Long id;

    /* 操作类型：0--审批通过，1--驳回*/
    private Integer approverStatus;

    /* 审批意见*/
    private String opinion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApproverStatus() {
        return approverStatus;
    }

    public void setApproverStatus(Integer approverStatus) {
        this.approverStatus = approverStatus;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
