package com.tyzq.salary.model.vo.salary;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-22 13:39
 * @Description: //TODO 计薪参数VO
 **/
public class ComputeSalaryParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 该条数据id*/
    private Long id;
    /**
     * 新入职出勤天数
     */
    private BigDecimal newEntryAttendanceDays;
    /**
     * 转正前应出勤天数
     */
    private BigDecimal positiveBeforeAttendanceDays;
    /**
     * 转正前其他缺勤天数
     */
    private BigDecimal positiveBeforeOtherAttendanceDays;
    /**
     * 转正前病假缺勤天数
     */
    private BigDecimal positiveBeforeSickAttendanceDays;
    /**
     * 转成后应出勤天数
     */
    private BigDecimal positiveAfterAttendanceDays;
    /**
     * 转正后其他缺勤天数
     */
    private BigDecimal positiveAfterOtherAttendanceDays;
    /**
     * 转正后病假缺勤天数
     */
    private BigDecimal positiveAfterSickAttendanceDays;
    /**
     * 其他缺勤天数
     */
    private BigDecimal otherAbsenceDays;
    /**
     * 病假缺勤天数
     */
    private BigDecimal sickAbsenceDays;
    /**
     * 本月绩效比例
     */
    private BigDecimal monthPerformanceRatio;
    /**
     * 本月奖惩金额(可为正负)
     */
    private BigDecimal monthRewordsMoney;

    /* computeSocailSecurityFlag  本月是否计算社保标识，为0计算，为1则不计算，默认为0*/
    private int computeSocailSecurityFlag = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getNewEntryAttendanceDays() {
        return newEntryAttendanceDays;
    }

    public void setNewEntryAttendanceDays(BigDecimal newEntryAttendanceDays) {
        this.newEntryAttendanceDays = newEntryAttendanceDays;
    }

    public BigDecimal getPositiveBeforeAttendanceDays() {
        return positiveBeforeAttendanceDays;
    }

    public void setPositiveBeforeAttendanceDays(BigDecimal positiveBeforeAttendanceDays) {
        this.positiveBeforeAttendanceDays = positiveBeforeAttendanceDays;
    }

    public BigDecimal getPositiveBeforeOtherAttendanceDays() {
        return positiveBeforeOtherAttendanceDays;
    }

    public void setPositiveBeforeOtherAttendanceDays(BigDecimal positiveBeforeOtherAttendanceDays) {
        this.positiveBeforeOtherAttendanceDays = positiveBeforeOtherAttendanceDays;
    }

    public BigDecimal getPositiveBeforeSickAttendanceDays() {
        return positiveBeforeSickAttendanceDays;
    }

    public void setPositiveBeforeSickAttendanceDays(BigDecimal positiveBeforeSickAttendanceDays) {
        this.positiveBeforeSickAttendanceDays = positiveBeforeSickAttendanceDays;
    }

    public BigDecimal getPositiveAfterAttendanceDays() {
        return positiveAfterAttendanceDays;
    }

    public void setPositiveAfterAttendanceDays(BigDecimal positiveAfterAttendanceDays) {
        this.positiveAfterAttendanceDays = positiveAfterAttendanceDays;
    }

    public BigDecimal getPositiveAfterOtherAttendanceDays() {
        return positiveAfterOtherAttendanceDays;
    }

    public void setPositiveAfterOtherAttendanceDays(BigDecimal positiveAfterOtherAttendanceDays) {
        this.positiveAfterOtherAttendanceDays = positiveAfterOtherAttendanceDays;
    }

    public BigDecimal getPositiveAfterSickAttendanceDays() {
        return positiveAfterSickAttendanceDays;
    }

    public void setPositiveAfterSickAttendanceDays(BigDecimal positiveAfterSickAttendanceDays) {
        this.positiveAfterSickAttendanceDays = positiveAfterSickAttendanceDays;
    }

    public BigDecimal getOtherAbsenceDays() {
        return otherAbsenceDays;
    }

    public void setOtherAbsenceDays(BigDecimal otherAbsenceDays) {
        this.otherAbsenceDays = otherAbsenceDays;
    }

    public BigDecimal getSickAbsenceDays() {
        return sickAbsenceDays;
    }

    public void setSickAbsenceDays(BigDecimal sickAbsenceDays) {
        this.sickAbsenceDays = sickAbsenceDays;
    }

    public BigDecimal getMonthPerformanceRatio() {
        return monthPerformanceRatio;
    }

    public void setMonthPerformanceRatio(BigDecimal monthPerformanceRatio) {
        this.monthPerformanceRatio = monthPerformanceRatio;
    }

    public BigDecimal getMonthRewordsMoney() {
        return monthRewordsMoney;
    }

    public void setMonthRewordsMoney(BigDecimal monthRewordsMoney) {
        this.monthRewordsMoney = monthRewordsMoney;
    }

    public int getComputeSocailSecurityFlag() {
        return computeSocailSecurityFlag;
    }

    public void setComputeSocailSecurityFlag(int computeSocailSecurityFlag) {
        this.computeSocailSecurityFlag = computeSocailSecurityFlag;
    }
}
