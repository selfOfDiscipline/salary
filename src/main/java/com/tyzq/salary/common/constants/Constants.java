package com.tyzq.salary.common.constants;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: Constants
 * @ProjectName: housemanage
 * @Author: 郑稳超 zwc_503@163.com
 * @Date： 2019/7/16 15:40
 * @Description: //TODO 请求常量类
 * @Version: 1.0
 */
public class Constants implements Serializable {

    public static final long serialVersionUID = 1L;

    /* user_session键*/
    public static final String USER_SESSION = "userSession";

    /* 用户REDIS SESSIONID存储前缀*/
    public static final String ACCESS_TOKEN = "accessToken_";

    /* 全量菜单REDIS MENU存储KEY*/
    public static final String REDIS_MENU = "redisMenu";

    /* 全量角色REDIS 角色存储KEY*/
    public static final String REDIS_ROLE = "redisRole";

    /* 全量部门REDIS 部门存储KEY*/
    public static final String REDIS_DEPT = "redisDept";

    /* 全量工资类个税数据REDIS 存储KEY*/
    public static final String REDIS_SALARY_PERSON_TAX = "redisSalaryPersonTax";

    /* 全量非工资类个税数据REDIS 存储KEY*/
    public static final String REDIS_SALARY_NON_PERSON_TAX = "redisSalaryNonPersonTax";

    /* redis 用户session 有效时间为两小时*/
    public static final long REDIS_SESSION_SECONDS = 60 * 30 * 4;

    /* redis 全量菜单 有效时间为24小时*/
    public static final long REDIS_COMMON_SECONDS = 60 * 30 * 4 * 12;

    /* 定义总经理角色ID*/
    public static final long ADMIN_ROLE_ID = 1l;

    /* 定义副总经理ID*/
    public static final long OTHER_ROLE_ID = 2l;

    /* 定义财务经理ID*/
    public static final long MONEY_ROLE_ID = 3l;

    /* 定义薪资核算角色ID*/
    public static final long SALARY_DEPT_ROLE_ID = 12l;

    /* 定义人力资源总监角色ID----仅该角色人员才能操作汇总单据按钮*/
    public static final long FINANCE_ROLE_ID = 14l;

    /* 定义出纳角色ID----仅该角色人员下单据通过。才会累加员工的年度累计数据*/
    public static final long CASHIER_ROLE_ID = 16l;

    /* 标准计薪规则比例*/
    public static final BigDecimal STANDARD_SALARY_RATIO = new BigDecimal("21.75");

    /* 管理员key*/
    public static final String ADMIN_KEY = "JF8s3H&5-9G483HTh9e8yh-HF*wh-98H";
}
