package com.tyzq.salary.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Administrator
 * @title: PropConfigUtil
 * @projectName housemanage
 * @description: TODO
 * @date 2019/7/1711:03
 */
public class PropConfigUtil {


    private static final String CONFINGPATH="/config.properties";

    public static String getValueByKey(String key){
        Properties prop =  new  Properties();
        InputStream in = PropConfigUtil.class.getResourceAsStream(CONFINGPATH);
        String value="";
        try{
            prop.load(in);
            value = prop.getProperty(key).trim();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getValueByKey(String configPath,String key){
        Properties prop =  new  Properties();
        InputStream in = PropConfigUtil.class.getResourceAsStream(configPath);
        String value="";
        try{
            prop.load(in);
            value = prop.getProperty(key).trim();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getSSOValueByKey(String key, HttpServletRequest request) {
        Properties prop =  new  Properties();
        InputStream in = request.getServletContext().getResourceAsStream("/WEB-INF/sso-config.properties");
        String value="";
        try {
            prop.load(in);
            value = prop.getProperty(key).trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }


}

