package com.tyzq.salary.service.cost;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.ProjectQueryVO;
import com.tyzq.salary.model.vo.cost.ProjectSaveVO;
import com.tyzq.salary.model.vo.cost.UserCostQueryVO;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:36 2021/2/2
 * @Description: //TODO 项目管理接口
 **/
public interface ProjectService {

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 16:53 2021/2/2
     * @Param:
     * @return:
     * @Description: //TODO 查询薪资模块的 基础员工信息列表（用于给项目挂员工）
     **/
    ApiResult selectBaseUserList(UserCostQueryVO userCostQueryVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:39 2021/2/3
     * @Param:
     * @return:
     * @Description: //TODO 查询项目列表
     **/
    ApiResult selectProjectList(ProjectQueryVO projectQueryVO, UserSessionVO userSessionVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:15 2021/2/4
     * @Param:
     * @return:
     * @Description: //TODO 新增修改项目列表
     **/
    ApiResult saveOrUpdateProject(ProjectSaveVO projectSaveVO, UserSessionVO userSessionVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:13 2021/2/5
     * @Param:
     * @return:
     * @Description: //TODO 根据项目id，查询项目详情包含项目所属人员列表
     **/
    ApiResult getProjectInfoById(Long id);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 15:23 2021/2/18
     * @Param:
     * @return:
     * @Description: //TODO 批量删除项目及项目关联人员信息，根据所传项目ids字符串，多个项目id用英文逗号分隔
     **/
    ApiResult deleteProjectByIds(String ids, UserSessionVO userSessionVO);
}
