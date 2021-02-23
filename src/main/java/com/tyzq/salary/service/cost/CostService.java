package com.tyzq.salary.service.cost;

import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.model.vo.base.UserSessionVO;

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
}
