package com.tyzq.salary.utils;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @ProJectName: zhongyi
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-25 10:00
 * @Description: //TODO test
 **/
public class Test {


    public static void mainCountDown(String[] args) throws InterruptedException {
        //1、 创建CountDownLatch 对象， 设定需要计数的子线程数目
        final CountDownLatch latch=new CountDownLatch(1);
        System.out.println("主线程开始执行....");
            new Thread(){
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"  开始执行存储过程..");
                        Thread.sleep(2000);
                        System.out.println(Thread.currentThread().getName()+"  存储过程执行完毕...");
                        //2、子线程执行完毕，计数减1
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        System.out.println("等待子线程执行完毕...");
        //3、 当前线程挂起等待
        latch.await();
        System.out.println("主线程执行完毕....");
    }

    public static void main(String[] args) {
        String ids = "1,2,3,4";
        // 转换id为集合
        List<Integer> collectIds = Arrays.asList(ids.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
        int sum = collectIds.stream().mapToInt(s -> s).sum();
        System.out.println("max = " + sum);
        String s = PasswordUtil.randomGenerate(8);
        System.out.println("s = " + s);
        Map<String, Object> map = Maps.newHashMap();
        map.put("account", "666666");
        map.put("password", "123456");
        map.put("loginSalt", "uFi-FpliFFaFK&nR_-t0VFN%E9+lJM!R");
        String sign = PasswordUtil.getSign(map);
        String s1 = PasswordUtil.randomGenerate(32);
        System.out.println("s1 = " + s1);
//9893D62FE1A889B26057D3F00D951A56
//9893D62FE1A889B26057D3F00D951A56
//9893D62FE1A889B26057D3F00D951A56

//        collectIds.forEach(s -> System.out.println("s = " + s));
    }

}
