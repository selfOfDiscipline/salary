package com.tyzq.salary.service.cost.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.common.vo.BootstrapTablePageVO;
import com.tyzq.salary.mapper.UserMapper;
import com.tyzq.salary.model.vo.cost.UserCostQueryVO;
import com.tyzq.salary.model.vo.cost.UserResultVO;
import com.tyzq.salary.service.cost.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/*
 * @Author: 郑稳超先生 zwc_503@163.com
 * @Date: 14:37 2021/2/2
 * @Description: //TODO 项目管理接口实现类
 **/
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private UserMapper userMapper;

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 16:53 2021/2/2
     * @Param:
     * @return:
     * @Description: //TODO 查询薪资模块的 基础员工信息列表（用于给项目挂员工）
     **/
    @Override
    public ApiResult selectBaseUserList(UserCostQueryVO userCostQueryVO) {
        // 查询数据库
        PageHelper.startPage(userCostQueryVO.getPageNum(), userCostQueryVO.getPageSize());
        List<UserResultVO> dataList = userMapper.selectBaseUserList(userCostQueryVO);
        PageInfo<UserResultVO> info = new PageInfo<>(dataList);
        // 定义分页工具类并赋值   总条数 & 总页数 & 返回数据
        BootstrapTablePageVO<UserResultVO> tablePageVO = new BootstrapTablePageVO<>();
        tablePageVO.setTotal(info.getTotal());
        tablePageVO.setDataList(info.getList());
        return ApiResult.getSuccessApiResponse(tablePageVO);
    }
}
