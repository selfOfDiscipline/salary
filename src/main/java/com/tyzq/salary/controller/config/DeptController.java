package com.tyzq.salary.controller.config;

import com.baomidou.mybatisplus.mapper.Condition;
import com.google.common.collect.Lists;
import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.vo.ApiResult;
import com.tyzq.salary.mapper.DeptMapper;
import com.tyzq.salary.model.Dept;
import com.tyzq.salary.model.vo.base.DeptVO;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-15 09:17
 * @Description: //TODO 部门控制类
 **/
@Api(tags = "【基础配置】··【部门模块】")
@RestController
@RequestMapping(value = "/config/dept")
public class DeptController {

    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Resource
    private DeptMapper deptMapper;

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 14:08 2020/9/18
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 查询全量部门结构
     **/
    @ApiOperation(value = "查询全量部门结构", httpMethod = "GET", notes = "查询全量部门结构")
    @GetMapping(value = "/selectDeptList")
    public ApiResult selectDeptList(HttpServletRequest request) {
        // 获取用户
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        if (null == userSessionVO) {
            return ApiResult.getFailedApiResponse("用户未登录！");
        }
        try {
            // 从缓存获取全量部门集合
            List<DeptVO> allDeptVOList = (List<DeptVO>) RedisUtil.get(Constants.REDIS_DEPT);
            // 校验
            if (CollectionUtils.isEmpty(allDeptVOList)) {
                // 从数据库获取
                List<Dept> deptList = deptMapper.selectList(Condition.create().eq("delete_flag", 0));
                // 校验
                if (!CollectionUtils.isEmpty(deptList)) {
                    // 转换VO集合
                    List<DeptVO> deptVOList = Lists.newArrayListWithCapacity(deptList.size());
                    deptList.forEach(m -> {
                        DeptVO deptVO = new DeptVO();
                        BeanUtils.copyProperties(m, deptVO);
                        deptVOList.add(deptVO);
                    });
                    // 赋值
                    allDeptVOList = deptVOList;
                    // 将全量部门集合放入缓存24小时
                    RedisUtil.set(Constants.REDIS_DEPT, allDeptVOList, Constants.REDIS_COMMON_SECONDS);
                }
            }
            // 再次校验
            if (CollectionUtils.isEmpty(allDeptVOList)) {
                return ApiResult.getFailedApiResponse("全量部门不存在！");
            }
            // 按照父级节点分组
            Map<Long, List<DeptVO>> rootMap = allDeptVOList.stream().sorted(Comparator.comparing(Dept::getSortNum)).collect(Collectors.groupingBy(Dept::getPid));
            // 获取到父级根目录节点集合
            List<DeptVO> rootList = allDeptVOList.stream().filter(a -> a.getPid() == 0).sorted(Comparator.comparing(DeptVO::getSortNum)).collect(Collectors.toList());
            // 递归
            putChildrenList(rootList, rootMap);
            return ApiResult.getSuccessApiResponse(rootList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询部门出现错误异常：" + e);
            return ApiResult.getFailedApiResponse("查询部门出现错误异常！");
        }
    }

    /**
     * 递归生成角色子类
     * @param rootList
     * @param rootMap
     */
    private void putChildrenList(List<DeptVO> rootList, Map<Long, List<DeptVO>> rootMap) {
        rootList.forEach(m -> {
            // 通过根节点id，查看是否有子节点
            List<DeptVO> children = rootMap.get(m.getId());
            if (children != null){
                children = children.stream().sorted(Comparator.comparing(DeptVO::getSortNum)).collect(Collectors.toList());
                m.setChildList(children);
                // 依次递归
                putChildrenList(children, rootMap);
            }
        });
    }

    /*
     * @Author: 郑稳超先生 zwc_503@163.com
     * @Date: 14:37 2021/3/1
     * @Param:
     * @return:
     * @Description: //TODO 获取全量业务归属部门集合 公共方法
     **/
    public List<Dept> selectDeptList() {
        // 从缓存获取全量部门集合
        List<Dept> allDeptList = (List<Dept>) RedisUtil.get(Constants.REDIS_ALL_DEPT);
        // 校验
        if (CollectionUtils.isEmpty(allDeptList)) {
            // 从数据库获取
            List<Dept> deptList = deptMapper.selectList(Condition.create().eq("delete_flag", 0));
            // 将全量初始部门集合放入缓存24小时
            RedisUtil.set(Constants.REDIS_ALL_DEPT, deptList, Constants.REDIS_COMMON_SECONDS);
            return deptList;
        }
        return allDeptList;
    }
}
