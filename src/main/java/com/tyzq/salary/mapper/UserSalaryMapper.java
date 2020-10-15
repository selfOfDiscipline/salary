package com.tyzq.salary.mapper;

import com.tyzq.salary.model.UserSalary;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tyzq.salary.model.vo.salary.SalaryHistoryQueryVO;
import com.tyzq.salary.model.vo.salary.SalaryHistoryResultVO;
import com.tyzq.salary.model.vo.salary.UserComputeResultVO;
import com.tyzq.salary.model.vo.salary.UserComputeSalaryQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户薪资表 Mapper 接口
 * </p>
 *
 * @author zwc_503@163.com
 * @since 2020-09-18
 */
@Mapper
public interface UserSalaryMapper extends BaseMapper<UserSalary> {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:28 2020/9/22
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 员工计薪列表查询
     **/
    List<UserComputeResultVO> selectUserListBySalaryUser(@Param("userVO") UserComputeSalaryQueryVO userComputeSalaryQueryVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 17:48 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询薪资表id集合
     **/
    List<Long> selectUserSalaryList(@Param("userPostType") String userPostType, @Param("thisDateLastMonth") Date thisDateLastMonth,
                                    @Param("salaryDeptIdList") List<Long> salaryDeptIdList, @Param("currentComputeFlag") Integer currentComputeFlag);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:39 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  根据id集合，查询薪资表
     **/
    List<UserComputeResultVO> selectUserListByIds(@Param("salaryIdList") List<Long> salaryIdList);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 11:08 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询历史工资单列表
     **/
    List<SalaryHistoryResultVO> selectHistorySalaryList(@Param("queryVO") SalaryHistoryQueryVO salaryHistoryQueryVO);
}
