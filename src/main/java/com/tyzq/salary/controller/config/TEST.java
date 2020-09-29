package com.tyzq.salary.controller.config;

import com.tyzq.salary.utils.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ProJectName: salary
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2020-09-18 16:22
 * @Description: //TODO 测试类
 **/
public class TEST {


    public static void main(String[] args) {

        Date thisDateMonth = DateUtils.getThisDateMonth();
        Date thisDateMonth2 = DateUtils.getThisDateMonth();
        System.out.println("thisDateMonth = " + thisDateMonth);
        Date thisDateLastMonth = DateUtils.getThisDateLastMonth();
        System.out.println("thisDateLastMonth = " + thisDateLastMonth);

        String dateString111 = DateUtils.getDateString(thisDateMonth, "yyyy-MM");
        String dateString = DateUtils.getDateString(new Date(), "yyyy-MM");


        if (dateString111.equals(dateString)) {
            System.out.println("true = " + true);
        } else {
            System.out.println("false = " + false);

        }

        String value = "0099";

        System.out.println("value = " + value);
        System.out.println("Integer.valueOf(value) = " + Integer.valueOf(value));

        Integer integer = Integer.valueOf(value);
        integer++;
        String s = integer.toString();
        int length = integer.toString().length();
        System.out.println("length = " + length);

        switch (length) {
            case 1 :
                s = "000" + s;
                break;
            case 2 :
                s = "00" + s;
                break;
            case 3 :
                s = "0" + s;
                break;
        }
        System.out.println("s = " + s);

        System.out.println("Integer.valueOf(value) = " + Integer.valueOf(value));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
//        Date parse = simpleDateFormat.parse("2020-05-11");

        if ("2020-08".compareTo("2020-08") > -1) {
            System.out.println("\"dayu\" = " + "dayu");
        } else {
            System.out.println("\"xiaoyu\" = " + "xiaoyu");
        }


    }

    private boolean checkSocialFlag (Date startSocialDate) {
        String socialDate = DateUtils.getDateString(startSocialDate, "yyyy-MM");
        String dateLastMonth = DateUtils.getDateString(DateUtils.getThisDateLastMonth(), "yyyy-MM");
        // 校验
        if (socialDate.equals(dateLastMonth)) {
            return true;
        }
        return false;
    }

}
