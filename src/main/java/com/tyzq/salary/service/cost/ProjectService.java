package com.tyzq.salary.service.cost;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.cost.ProjectQueryVO;
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
    ApiResult selectProjectList(ProjectQueryVO projectQueryVO);
}
