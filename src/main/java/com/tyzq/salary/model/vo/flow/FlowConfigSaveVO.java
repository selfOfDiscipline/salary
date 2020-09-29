package com.tyzq.salary.model.vo.flow;

import com.tyzq.salary.model.BaseFlowConfig;
import com.tyzq.salary.model.BaseFlowConfigDetail;

import java.io.Serializable;
import java.util.List;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 15:27
 * @Description: //TODO 新增或修改流程配置
 **/
public class FlowConfigSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 流程配置主表*/
    private BaseFlowConfig baseFlowConfig;

    /* 流程配置明细表集合*/
    private List<BaseFlowConfigDetail> baseFlowConfigDetailList;

    public BaseFlowConfig getBaseFlowConfig() {
        return baseFlowConfig;
    }

    public void setBaseFlowConfig(BaseFlowConfig baseFlowConfig) {
        this.baseFlowConfig = baseFlowConfig;
    }

    public List<BaseFlowConfigDetail> getBaseFlowConfigDetailList() {
        return baseFlowConfigDetailList;
    }

    public void setBaseFlowConfigDetailList(List<BaseFlowConfigDetail> baseFlowConfigDetailList) {
        this.baseFlowConfigDetailList = baseFlowConfigDetailList;
    }
}
