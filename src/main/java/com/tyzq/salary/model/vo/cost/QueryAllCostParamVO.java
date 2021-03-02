package com.tyzq.salary.model.vo.cost;

import java.io.Serializable;

public class QueryAllCostParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 月度查询时必传：月份  格式：yyyy-MM*/
    private String monthDate;

    /* 年度和季度查询时必传：年份  格式：yyyy*/
    private String yearDate;

    /* 季度查询时必传：季度  格式：1或者2或者3或者4*/
    private int quarterDate = 1;

    /**
     * 查询类型必传：0--月度，1--季度，2--年度
     */
    private int queryFlag = 0;

    public String getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(String monthDate) {
        this.monthDate = monthDate;
    }

    public String getYearDate() {
        return yearDate;
    }

    public void setYearDate(String yearDate) {
        this.yearDate = yearDate;
    }

    public int getQuarterDate() {
        return quarterDate;
    }

    public void setQuarterDate(int quarterDate) {
        this.quarterDate = quarterDate;
    }

    public int getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(int queryFlag) {
        this.queryFlag = queryFlag;
    }
}
