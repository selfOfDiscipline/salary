package com.tyzq.salary.mapper;

import com.tyzq.salary.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tyzq.salary.model.vo.cost.UserCostQueryVO;
import com.tyzq.salary.model.vo.cost.UserResultVO;
import com.tyzq.salary.model.vo.salary.PerformanceBaseUserVO;
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

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:48 2021/1/19
     * @Param:
     * @return:
     * @Description: //TODO 查询全量系统账号 用于配置流程
     **/
    List<UserBaseResultVO> selectAllAdminList(@Param("userQueryVO") UserQueryVO userQueryVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 17:54 2021/2/2
     * @Param:
     * @return:
     * @Description: //TODO 查询薪资模块的 基础员工信息列表（用于给项目挂员工）
     **/
    List<UserResultVO> selectBaseUserList(@Param("userCostQueryVO") UserCostQueryVO userCostQueryVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:36 2021/3/5
     * @Param:
     * @return:
     * @Description: //TODO 查询所有 有季度绩效的员工信息  条件：未删除，绩效类型为季度
     **/
    List<PerformanceBaseUserVO> selectAllQuarterUserList();
}
