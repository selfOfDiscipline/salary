package com.tyzq.salary.mapper;

import com.tyzq.salary.model.Menu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-14
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:16 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据用户id查询该用户的菜单
     **/
    List<Menu> selectMenuListByUserId(Long id);
}
