package com.tyzq.salary.model.vo.salary;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 13:11 2021/3/5
 * @Description: //TODO 季度绩效核算接口参数VO
 **/
public class PerformanceComputeVO implements Serializable {

    private static final long serialVersionUID =1L;

    /**
     *  主键ID
     */
    private Long id;

    /**
     * 绩效系数
     */
    private BigDecimal performanceRatio;

    /**
     * 其他金额增减项
     */
    private BigDecimal quarterRewordsMoney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPerformanceRatio() {
        return performanceRatio;
    }

    public void setPerformanceRatio(BigDecimal performanceRatio) {
        this.performanceRatio = performanceRatio;
    }

    public BigDecimal getQuarterRewordsMoney() {
        return quarterRewordsMoney;
    }

    public void setQuarterRewordsMoney(BigDecimal quarterRewordsMoney) {
        this.quarterRewordsMoney = quarterRewordsMoney;
    }
}
