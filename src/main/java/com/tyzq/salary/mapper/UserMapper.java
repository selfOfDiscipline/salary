package com.tyzq.salary.mapper;

import com.tyzq.salary.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tyzq.salary.model.vo.user.UserBaseResultVO;
import com.tyzq.salary.model.vo.user.UserDetailVO;
import com.tyzq.salary.model.vo.user.UserQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:45 2020/9/21
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 条查用户列表
     **/
    List<UserBaseResultVO> selectUserList(@Param("userQueryVO") UserQueryVO userQueryVO, @Param("salaryDeptIdList") List<Long> salaryDeptIdList);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:45 2020/9/21
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询用户薪资部分
     **/
    List<UserDetailVO> selectUserDetailList(@Param("userPostType") Integer userPostType, @Param("salaryDeptIdList") List<Long> salaryDeptIdList);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:55 2020/10/19
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询全量用户列表
     **/
    List<UserBaseResultVO> selectAllUserList(@Param("userQueryVO") UserQueryVO userQueryVO);
}
