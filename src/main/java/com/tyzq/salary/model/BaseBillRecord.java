package com.tyzq.salary.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 单据编号记录表
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-23
 */
public class BaseBillRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 单据类型英文编码
     */
    private String enCode;
    /**
     * 单据类型名称
     */
    private String billType;
    /**
     * 单据编号
     */
    private String billCode;
    /**
     * 单据前缀
     */
    private String billPrefix;
    /**
     * 单据后缀
     */
    private String billSuffix;
    /**
     * 当日单据排序
     */
    private Integer sortNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillPrefix() {
        return billPrefix;
    }

    public void setBillPrefix(String billPrefix) {
        this.billPrefix = billPrefix;
    }

    public String getBillSuffix() {
        return billSuffix;
    }

    public void setBillSuffix(String billSuffix) {
        this.billSuffix = billSuffix;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "BaseBillRecord{" +
        "id=" + id +
        ", enCode=" + enCode +
        ", billType=" + billType +
        ", billCode=" + billCode +
        ", billPrefix=" + billPrefix +
        ", billSuffix=" + billSuffix +
        ", sortNum=" + sortNum +
        "}";
    }
}
