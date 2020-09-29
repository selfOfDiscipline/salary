package com.tyzq.salary.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
 * @Author zwc   zwc_503@163.com
 * @Date 10:59 2019/10/24
 * @Param 
 * @return 
 * @Version 1.0
 * @Description //TODO 
 **/
public class CookieUtil {

    /**
     * 获取cookie值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookie(HttpServletRequest request , String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), cookieName)) {
                    String value = cookie.getValue();
                    return value;
                }
            }
        }
        return null;
    }
}
