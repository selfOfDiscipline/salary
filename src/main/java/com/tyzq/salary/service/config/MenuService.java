package com.tyzq.salary.service.config;

import com.tyzq.salary.model.Menu;

import java.util.List;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-28 10:09
 * @Description: //TODO 菜单模块
 **/
public interface MenuService {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:37 2019/10/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据当前用户Id,查询该用户所能操作的菜单
     **/
    List<Menu> selectMenuListById(Long id);
}