package com.tyzq.salary.service.config.impl;

import com.tyzq.salary.mapper.MenuMapper;
import com.tyzq.salary.model.Menu;
import com.tyzq.salary.service.config.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProJectName: zhongyi
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-28 10:09
 * @Description: //TODO 菜单模块
 **/
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:42 2019/10/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据用户ID查询该用户的菜单  flag为1代表管理员，为2代表普通用户
     **/
    @Override
    public List<Menu> selectMenuListById(Long id) {
        return menuMapper.selectMenuListByUserId(id);
    }
}
