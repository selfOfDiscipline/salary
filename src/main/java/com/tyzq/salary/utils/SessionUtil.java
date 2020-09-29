package com.tyzq.salary.utils;

import com.alibaba.fastjson.JSONObject;
import com.tyzq.salary.model.vo.base.UserSessionVO;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ProJectName: notb
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-11-20 13:49
 * @Description: //TODO session工具类
 **/
public class SessionUtil {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 13:50 2019/11/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 取cookie中是sessionId
     **/
    public static String getTokenByCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        String value = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), cookieName)) {
                    value = cookie.getValue();
                    return value;
                }
            }
        }
        return value;
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 13:52 2019/11/16
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 直接获取请求头中Authorization中的用户token
     **/
    public static String getTokenByAuthorization(HttpServletRequest request) {
        String token = "";
        try {
            token = request.getHeaders("Authorization").nextElement();
        } catch (Exception e) {
        }
        return token;
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:44 2019/11/17
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 获取对象
     **/
    public static UserSessionVO getSessionObject(String token) {
        Object result = RedisUtil.get(token);
        if (result == null) {
            return null;
        }
        return JSONObject.parseObject(result.toString(), UserSessionVO.class);
    }
}
