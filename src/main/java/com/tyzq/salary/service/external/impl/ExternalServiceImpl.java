package com.tyzq.salary.service.external.impl;

import com.tyzq.salary.common.enums.ExternalCodeEnum;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.UserMapper;
import com.tyzq.salary.mapper.UserSalaryMapper;
import com.tyzq.salary.model.User;
import com.tyzq.salary.model.vo.external.ExternalSalaryParamVO;
import com.tyzq.salary.model.vo.external.ExternalSalaryResultVO;
import com.tyzq.salary.service.external.ExternalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:54 2021/1/26
 * @Description: //TODO 对外接口实现类
 **/
@Service
@Transactional
public class ExternalServiceImpl implements ExternalService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserSalaryMapper userSalaryMapper;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 16:05 2021/1/26
     * @Param:
     * @return:
     * @Description: //TODO 根据账号和时间，查询用户薪资数据信息
     **/
    @Override
    public ApiResult querySalaryExternal(ExternalSalaryParamVO paramVO) {
        // 查当前用户是否存在于本系统中
        User user = new User();
        user.setUserAccount(paramVO.getUserAccount());
        user.setDeleteFlag(0);
        user = userMapper.selectOne(user);
        // 校验
        if (null == user) {
            return ApiResult.getFailedApiResponse(ExternalCodeEnum.QUERY_SALARY_10002.getCode(), ExternalCodeEnum.QUERY_SALARY_10002.getMessage());
        }
        // 转换日期
        String salaryDate = paramVO.getSalaryDate() + "-01 00:00:00";
        // 查询薪资信息
        List<ExternalSalaryResultVO> salaryResultVOList = userSalaryMapper.querySalaryExternal(paramVO.getUserAccount(), salaryDate);
        // 校验
        if (CollectionUtils.isEmpty(salaryResultVOList)) {
            return ApiResult.getSuccessApiResponse();
        }
        return ApiResult.getSuccessApiResponse(salaryResultVOList.get(0));
    }
}
