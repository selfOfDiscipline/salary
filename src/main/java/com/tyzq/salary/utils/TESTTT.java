package com.tyzq.salary.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SystemUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TESTTT {


    public static void main(String[] args) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = DateUtils.stepMonthWithDate(new Date(), 3);
//        System.out.println("date = " + sdf.format(date));
        long l = System.currentTimeMillis();
        System.out.println("l = " + l);

        String sign = DigestUtils.md5Hex("tyzq-suyao" + "&" + "2020-12" + "&" + l + "&" + "QS21FCA75610B922B7B2609B40E3H32W").toLowerCase();
        System.out.println("sign = " + sign);
    }


    public static void main111(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            // 获取当前日期
            Date thisDate = sdf.parse(sdf.format(new Date()));
            System.out.println("thisDate = " + thisDate);
            // 转换合同服务开始日期
            Date contractDate = sdf.parse("2019-12-03");
            System.out.println("contractDate = " + contractDate);
            // 定义当前月份  年份
            int thisMonth = 0;
            int thisYear = 0;
            // 校验孰晚
            if (thisDate.before(contractDate)) {
                // 取合同服务开始日期 当做实际占用月份
                calendar.setTime(contractDate);
                thisMonth = calendar.get(Calendar.MONTH) + 1;
                thisYear = calendar.get(Calendar.YEAR);
            } else {
                // 取当前日期 当做实际占用月份
                calendar.setTime(thisDate);
                thisMonth = calendar.get(Calendar.MONTH) + 1;
                thisYear = calendar.get(Calendar.YEAR);
            }
            // 定义实际占用日期
            String realityMonthDate = thisYear + "-" + thisMonth;
            System.out.println("realityMonthDate = " + realityMonthDate);

        } catch (Exception e) {

        }


    }


}
