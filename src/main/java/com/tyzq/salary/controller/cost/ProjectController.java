package com.tyzq.salary.controller.cost;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.cost.ProjectQueryVO;
import com.tyzq.salary.model.vo.cost.UserCostQueryVO;
import com.tyzq.salary.service.cost.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:39 2021/2/2
 * @Description: //TODO 项目管理模块
 **/
@Api(tags = "【成本中心】··【项目管理模块】")
@RestController
@RequestMapping(value = "/cost/project")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:46 2021/2/2
     * @Param:
     * @return:
     * @Description: //TODO 查询薪资模块的 基础员工信息列表（用于给项目挂员工）
     **/
    @ApiOperation(value = "查询所有员工基础信息列表", httpMethod = "POST", notes = "查询薪资模块的 基础员工信息列表（用于给项目挂员工）")
    @PostMapping(value = "/selectBaseUserList")
    public ApiResult selectBaseUserList(@RequestBody UserCostQueryVO userCostQueryVO) {
        try {
            // 业务操作
            return projectService.selectBaseUserList(userCostQueryVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询所有员工基础信息列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询所有员工基础信息列表出现错误异常！");
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:30 2021/2/3
     * @Param:
     * @return:
     * @Description: //TODO 查询项目列表
     **/
    @ApiOperation(value = "查询项目列表", httpMethod = "POST", notes = "查询项目列表")
    @PostMapping(value = "/selectProjectList")
    public ApiResult selectProjectList(@RequestBody ProjectQueryVO projectQueryVO) {
        try {
            // 业务操作
            return projectService.selectProjectList(projectQueryVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询项目列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询项目列表出现错误异常！");
        }
    }
}
