package com.tyzq.salary.mapper;

import com.tyzq.salary.model.UserSalary;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tyzq.salary.model.vo.cost.ProjectSalaryResultVO;
import com.tyzq.salary.model.vo.external.ExternalSalaryResultVO;
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
    List<UserComputeResultVO> selectUserListByIds(@Param("userVO") UserComputeSalaryQueryVO userComputeSalaryQueryVO, @Param("salaryIdList") List<Long> salaryIdList);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 11:08 2020/9/28
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询历史工资单列表
     **/
    List<SalaryHistoryResultVO> selectHistorySalaryList(@Param("queryVO") SalaryHistoryQueryVO salaryHistoryQueryVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:48 2021/1/27
     * @Param:
     * @return:
     * @Description: //TODO 根据账号和时间，查询用户薪资数据信息
     **/
    List<ExternalSalaryResultVO> querySalaryExternal(@Param("userAccount") String userAccount, @Param("salaryDate") String salaryDate);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 10:27 2021/2/23
     * @Param:
     * @return:
     * @Description: //TODO 查员工薪资数据用于生成用户当月成本。项目人员表+项目表+用户表+薪资表  4张表，根据所传日期 && 用户账号集合
     **/
    List<ProjectSalaryResultVO> selectUserSalaryWithProjectCost(@Param("salaryDate") String salaryDate, @Param("userAccountList") List<String> userAccountList);
}
