package com.tyzq.salary.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: CommonUtil
 * @ProjectName: zhongyi
 * @Author: 郑稳超 zwc_503@163.com
 * @Date： 2019/7/25 13:34
 * @Description: //TODO  自定义公共常量util
 * @Version: 1.0
 */
public class CommonUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/25 13:37
     * @Param: 
     * @return: 
     * @Version: 
     * @Description: //TODO 获取pageNum  默认传1
     **/
    public static int pageNum(Map<String, Object> map) {
        return (map.get("pageNum") != null && map.get("pageNum") != "") ? Integer.valueOf(map.get("pageNum").toString()) : 1;
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/25 13:41
     * @Param: 
     * @return: 
     * @Version: 
     * @Description: //TODO 获取pageSize  默认传10
     **/
    public static int pageSize(Map<String, Object> map) {
        return (map.get("pageSize") != null && map.get("pageSize") != "") ? Integer.valueOf(map.get("pageSize").toString()) : 10;
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/25 13:44
     * @Param: 
     * @return: 
     * @Version: 
     * @Description: //TODO 非空判断
     **/
    public static boolean nonNull(Object object) {
        return (object != null && object != "") ? true : false;
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/31 9:16
     * @Param: 
     * @return: 
     * @Version: 
     * @Description: //TODO 自定义三木运算
     **/
    public static Object thisOrNull(Object object) {
        return (object == null || object == "") ? null : object;
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/31 9:58
     * @Param:
     * @return:
     * @Version:
     * @Description: //TODO 日期转字符串
     **/
    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat sdf;
        String result = "";
        if (nonNull(date)) {
            sdf = new SimpleDateFormat(format);
            result = sdf.format(date);
        }
        return result;
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/31 9:58
     * @Param:
     * @return:
     * @Version:
     * @Description: //TODO 日期转字符串
     **/
    public static String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        if (nonNull(date)) {
            result = sdf.format(date);
        }
        return result;
    }

    /*
     * @Author: 郑稳超 zwc_503@163.com
     * @Date: 2019/7/31 10:04
     * @Param:
     * @return:
     * @Version:
     * @Description: //TODO 字符串转日期
     **/
    public static Date convertStringToDate(String date, String format) throws ParseException {
        SimpleDateFormat sdf;
        Date result = null;
        if (nonNull(date)) {
            sdf = new SimpleDateFormat(format);
            result = sdf.parse(date);
        }
        return result;
    }
}
