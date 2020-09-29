package com.tyzq.salary.utils;


/**
 * 驼峰工具类
 */
public class CamelUtils {
	public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     * 
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
            	if(sb.length() > 0)
            		sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else if(Character.isDigit(c) && i > 0 && !Character.isDigit(param.charAt(i - 1))){
            	if(sb.length() > 0)
            		sb.append(UNDERLINE);
                sb.append(c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     * 
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * 驼峰格式字符串首字母转换为大写
     * @param param
     * @return
     */
    public static String camelToFirstUpperCase(String param){
    	if (param == null || "".equals(param.trim())) {
            return "";
        }
    	String sReturn = param;
    	
    	if(sReturn.length() > 0){
    		sReturn = Character.toUpperCase(sReturn.charAt(0)) + sReturn.substring(1);
    	}
    	
    	return sReturn;
    }
}