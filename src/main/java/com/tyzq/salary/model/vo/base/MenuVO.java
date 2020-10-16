package com.tyzq.salary.model.vo.base;

import com.tyzq.salary.model.Menu;

import java.io.Serializable;
import java.util.List;

/**
 * @ProJectName: SALARY
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-28 10:52
 * @Description: //TODO 菜单子类
 **/
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 主键id*/
    private Long id;

    /* 菜单文件夹路径*/
    private String component;

    /* 菜单标题名称*/
    private String title;

    /* 菜单icon*/
    private String icon;

    /* 菜单名称*/
    private String name;

    /* 菜单path*/
    private String path;

    /* 菜单重定向到浏览器的路径*/
    private String redirect;

    /**
     * 父级菜单id
     */
    private Long pid;
    /**
     * 排序
     */
    private Integer sortNum;
    /**
     * 菜单全路径
     */
    private String fullPath;

    /* 该菜单是否被选中，true代表选中，false代表未选中*/
    private boolean checkFlag = false;

    /* 菜单子类集合*/
    private List<MenuVO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }
}
