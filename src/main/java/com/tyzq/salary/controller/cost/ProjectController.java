package com.tyzq.salary.controller.cost;

import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.ProjectQueryVO;
import com.tyzq.salary.model.vo.cost.ProjectSaveVO;
import com.tyzq.salary.model.vo.cost.UserCostQueryVO;
import com.tyzq.salary.service.cost.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:09 2021/2/4
     * @Param:
     * @return:
     * @Description: //TODO 新增修改项目列表
     **/
    @ApiOperation(value = "新增修改项目列表", httpMethod = "POST", notes = "新增修改项目列表")
    @PostMapping(value = "/saveOrUpdateProject")
    public ApiResult saveOrUpdateProject(@RequestBody ProjectSaveVO projectSaveVO, HttpServletRequest request) {
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return projectService.saveOrUpdateProject(projectSaveVO, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("新增修改项目列表错误异常：" + e);
            return ApiResult.getFailedApiResponse("新增修改项目列表出现错误异常！");
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 11:09 2021/2/5
     * @Param:
     * @return:
     * @Description: //TODO 根据项目id，查询项目详情包含项目所属人员列表
     **/
    @ApiOperation(value = "查询项目详情接口", httpMethod = "POST", notes = "根据项目id，查询项目详情包含项目所属人员列表")
    @GetMapping(value = "/getProjectInfoById")
    public ApiResult getProjectInfoById(@RequestParam("id") Long id) {
        try {
            // 业务操作
            return projectService.getProjectInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询项目详情接口错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询项目详情接口出现错误异常！");
        }
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 15:20 2021/2/18
     * @Param:
     * @return:
     * @Description: //TODO 批量删除项目及项目关联人员信息，根据所传项目ids字符串，多个项目id用英文逗号分隔
     **/
    @ApiOperation(value = "批量删除项目及项目人员", httpMethod = "GET", notes = "批量删除项目及项目关联人员信息，根据所传项目ids字符串，多个项目id用英文逗号分隔")
    @GetMapping(value = "/deleteProjectByIds")
    public ApiResult deleteProjectByIds(@RequestParam("ids") String ids, HttpServletRequest request) {
        // 校验
        if (StringUtils.isBlank(ids)) {
            return ApiResult.getFailedApiResponse("请至少传一条ID！");
        }
        // 获取session用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        try {
            // 业务操作
            return projectService.deleteProjectByIds(ids, userSessionVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量删除项目及项目人员错误异常：" + e);
            return ApiResult.getFailedApiResponse("批量删除项目及项目人员出现错误异常！");
        }
    }
}
