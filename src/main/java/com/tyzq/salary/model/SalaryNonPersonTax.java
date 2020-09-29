package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 工资类的非个人所得税对应的税率及速算扣款表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-18
 */
public class SalaryNonPersonTax implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 个人所得税名称
     */
    private String taxName;
    /**
     * 区间开始钱数
     */
    private BigDecimal startMoney;
    /**
     * 区间结束钱数
     */
    private BigDecimal endMoney;
    /**
     * 对应的个人所得税率
     */
    private BigDecimal tax;
    /**
     * 对应的速算扣除钱数
     */
    private BigDecimal deductMoney;
    /**
     * 最大所得额标识：0--否，1--是
     */
    private Integer maxTaxFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public BigDecimal getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(BigDecimal startMoney) {
        this.startMoney = startMoney;
    }

    public BigDecimal getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(BigDecimal endMoney) {
        this.endMoney = endMoney;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(BigDecimal deductMoney) {
        this.deductMoney = deductMoney;
    }

    public Integer getMaxTaxFlag() {
        return maxTaxFlag;
    }

    public void setMaxTaxFlag(Integer maxTaxFlag) {
        this.maxTaxFlag = maxTaxFlag;
    }

    @Override
    public String toString() {
        return "SalaryNonPersonTax{" +
        "id=" + id +
        ", taxName=" + taxName +
        ", startMoney=" + startMoney +
        ", endMoney=" + endMoney +
        ", tax=" + tax +
        ", deductMoney=" + deductMoney +
        ", maxTaxFlag=" + maxTaxFlag +
        "}";
    }
}
