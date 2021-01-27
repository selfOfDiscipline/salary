package com.tyzq.salary.interceptor;

import com.tyzq.salary.common.constants.Constants;
import com.tyzq.salary.common.enums.ApiCodeEnum;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import com.tyzq.salary.utils.DateUtils;
import com.tyzq.salary.utils.RedisUtil;
import com.tyzq.salary.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/*
 * @Author zwc   zwc_503@163.com
 * @Date 10:58 2019/10/24
 * @Param
 * @return
 * @Version 1.0
 * @Description //TODO
 **/
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    /* 过滤接口*/
    public static final List<String> URL_LIST = new ArrayList<String>() {{
        add("/config/user/login");
        add("/external/api/querySalaryExternal");
    }};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("=================进入拦截器=================");
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        logger.info("requestUri:"+requestUri);
        logger.info("contextPath:"+contextPath);
        logger.info("url:"+url);

        // 过滤掉vue中的OPTIONS请求
        if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
            return true;
        }
        // 过滤接口
        if (URL_LIST.contains(url)) {
            // 后台管理登录接口
            return true;
        }
        // 获取登录态
        UserSessionVO userSessionVO = (UserSessionVO) request.getSession().getAttribute(Constants.USER_SESSION);
        if (userSessionVO != null) {
            return true;
        }
        // 获取浏览器token
        String token = SessionUtil.getTokenByAuthorization(request);
        // 中间件获取
        userSessionVO = SessionUtil.getSessionObject(Constants.ACCESS_TOKEN + token);
        if (userSessionVO == null) {
            // 跳转登录页
            // 用户未登录
            logger.info("=====未登录，跳转至登录页面======");
            response.sendError(ApiCodeEnum.LOGIN.getCode(), ApiCodeEnum.LOGIN.getMessage());
            return false;
        }
        // 重新赋值时间
        userSessionVO.setLastLoginTime(DateUtils.getNowDateString());
        // 将对象放入当前session
        request.getSession().setAttribute(Constants.USER_SESSION, userSessionVO);
        // 重新设置过期时间为半小时
        RedisUtil.expire(Constants.ACCESS_TOKEN + token, Constants.REDIS_SESSION_SECONDS);
        logger.info("=====用户登录成功======");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

}
