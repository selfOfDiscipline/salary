package com.tyzq.salary.model.vo.base;

import java.io.Serializable;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-09 14:34
 * @Description: //TODO 分页VO工具类
 **/
public class PageUtilVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 分页开始页，默认为第一页*/
    private int pageNum = 1;

    /* 每页条数，默认为10条*/
    private int pageSize = 10;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
