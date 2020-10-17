package com.tyzq.salary.service.config;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.user.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ProJectName: zhongyi
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-25 16:52
 * @Description: //TODO 用户接口层
 **/
public interface UserService {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:55 2019/10/25
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 登录  先查询是否为管理员，再查询是否为客户
     **/
    ApiResult login(String account, String password, HttpSession session);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:31 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 新增或修改用户信息有id为修改，无id为新增，（用户所属的薪资核算部门必选）
     **/
    Long updateUserByCondition(UserSaveVO userSaveVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:48 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量修改员工在职状态，根据所传员工在职类型（Integer类型的员工在职类型userRankType：0--试用期，1--正式，2--离职）及用户ids字符串，多个用户id用英文逗号分隔
     **/
    ApiResult updateUserRankByCondition(UpdateUserRankParamVO userRankParamVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:58 2020/9/14
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 批量删除员工，根据所传用户ids字符串，多个用户id用英文逗号分隔
     **/
    String deleteUserByIds(String ids, HttpServletRequest request);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 11:05 2020/9/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 管理岗新增或修改用户信息有id为修改，无id为新增，（用户所属的薪资核算部门必选）
     **/
    Long saveOrUpdateManageUser(UserSaveVO userSaveVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 15:47 2020/9/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询当前薪资核算负责人关联的员工列表
     **/
    ApiResult selectUserList(UserQueryVO userQueryVO, boolean adminFlag, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 20:45 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询全量人员基础信息列表
     **/
    ApiResult selectAllUserList(UserQueryVO userQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:39 2020/10/12
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  修改密码
     **/
    ApiResult updateUserPassword(UpdatePasswordVO updatePasswordVO, UserSessionVO userSessionVO);

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 12:28 2020/10/17
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 校验登录人权限
     **/
    Boolean checkUserSessionAuthrity(UserSessionVO userSessionVO);
}