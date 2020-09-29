package com.tyzq.salary.utils;

import java.util.UUID;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-27 18:05
 * @Description: //TODO 生成uuid工具类
 **/
public class UUIDUtil {

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 18:07 2020/9/27
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 生成大写UUID
     **/
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
