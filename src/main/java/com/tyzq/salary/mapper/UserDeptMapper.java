package com.tyzq.salary.mapper;

import com.tyzq.salary.model.UserDept;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户业务归属部门关联表 Mapper 接口
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2021-02-22
 */
@Mapper
public interface UserDeptMapper extends BaseMapper<UserDept> {

}
