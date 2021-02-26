package com.tyzq.salary.service.cost;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.ProjectCost;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.model.vo.cost.ProjectCostQueryVO;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:38 2021/2/2
 * @Description: //TODO 成本中心接口
 **/
public interface CostService {

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 10:09 2021/2/22
     * @Param:
     * @return:
     * @Description: //TODO 一键生成当月基础成本数据信息, 日期不存在就默认生成上个月的,日期格式：2021-02
     **/
    ApiResult generateCostDataByDate(String date, UserSessionVO userSessionVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 15:49 2021/2/24
     * @Param:
     * @return:
     * @Description: //TODO 查询项目的月度成本详情,根据项目编号 + 所选月份(格式：yyyy-MM)，月份不传默认为上个月，查询该项目下所有成本，用于计算
     **/
    ApiResult getProjectCostByCondition(ProjectCostQueryVO costQueryVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 9:28 2021/2/25
     * @Param:
     * @return:
     * @Description: //TODO 计算当前明细（人员） 计算当前明细（人员），有id是修改，无id新增
     **/
    ApiResult computeThisCost(String projectCode, String costDate, ProjectCost projectCost, UserSessionVO userSessionVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:59 2021/2/25
     * @Param:
     * @return:
     * @Description: //TODO 计算项目的毛利，根据项目编号，所选月份，所填写的项目本月完成度
     **/
    ApiResult computeThisProject(String projectCode, String costDate, String monthFinishRatio, UserSessionVO userSessionVO);

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 10:03 2021/2/26
     * @Param:
     * @return:
     * @Description: //TODO 批量删除项目成本明细，根据所传项目成本明细id字符串，多个用英文逗号拼接
     **/
    ApiResult deleteProjectCostByIds(String ids, UserSessionVO userSessionVO);
}
