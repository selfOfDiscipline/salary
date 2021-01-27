package com.tyzq.salary.controller.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TTtT {


    public static void main(String[] args) {

        String sss = "20201225080000";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(sss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String format = simpleDateFormat.format(parse);
        System.out.println("format = " + format);


    }

}
