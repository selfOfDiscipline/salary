package com.tyzq.salary.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ProJectName: zhongyi
 * @Author: zwc  zwc_503@163.com
 * @CreateTime: 2019-10-29 15:56
 * @Description: //TODO 密码生成类
 **/
public final class PasswordUtil {

    /* 定义数组*/
    private static final char[] CHARS = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '~', '!', '@', '#', '$', '%', '^', '-', '+', '&', '_'
    };

    private PasswordUtil() {}

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:50 2019/10/29
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO  根据传入长度生成随机字符串
     **/
    public static String randomGenerate(int length) {
        List<String> list = new ArrayList<String>(CHARS.length);
        for (int i = 0; i < CHARS.length; i++) {
            list.add(String.valueOf(CHARS[i]));
        }
        Collections.shuffle(list);

        int count = 0;
        StringBuffer sb = new StringBuffer();
        Random random = new Random(System.nanoTime());
        while (count < length) {
            int i = random.nextInt(list.size());
            sb.append(list.get(i));
            count++;
        }
        return sb.toString();
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 16:50 2019/10/29
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 根据Map参数获取随机字符串
     **/
    @SuppressWarnings("unchecked")
    public static String getSign(Map<String, Object> $params) {
        // 定义一个全局的sign串为空
        String sign = "";
        String[] key = new String[$params.size()];
        int index = 0;
        for (String k : $params.keySet()) {
            key[index] = k;
            index++;
        }
        // 排序按键进行从小到大排序，例如（从A->Z）
        Arrays.sort(key);
        // 遍历拼接
        for (String s : key) {
            sign += s + "=" + $params.get(s) + "&";
        }
        // 截取去掉最后一个多余的“&”号
        sign = sign.substring(0, sign.length() - 1);
        // 将得到的字符串进行处理得到目标格式的字符串
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 使用常见的UTF-8编码替换符号
        sign = sign.replace("%3D", "=").replace("%26", "&");
        // 获取md5加密后的加密串      有必要的话转换为小写toLowerCase()
//        System.out.println("sign ============================ " + sign);
        sign = DigestUtils.md5Hex(sign).toUpperCase();
//        System.out.println("sign ============================ " + sign);
        // 返回sign签名
        return sign;
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 9:55 2019/10/30
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 通过账号-密码-盐值  得到加密后的密码
     **/
    @SuppressWarnings("unchecked")
    public static String getPasswordBySalt(String account, String password, String loginSalt) {
        Map<String, Object> $param = new HashMap<>(3);
        $param.put("account", account);
        $param.put("password", password);
        $param.put("loginSalt", loginSalt);
        return PasswordUtil.getSign($param);
    }

    /*
     * @Author zwc   zwc_503@163.com
     * @Date 10:45 2019/11/20
     * @Param
     * @return
     * @Version 1.0
     * @Description //TODO 获取token  根据用户账号 && 用户密码 && 当前时间戳
     **/
    @SuppressWarnings("unchecked")
    public static String getTokenWithLogin(String account, String userSalt, long timeStamps) {
        Map<String, Object> $param = new HashMap<>(3);
        $param.put("account", account);
        $param.put("userSalt", userSalt);
        $param.put("timeStamps", timeStamps);
        return PasswordUtil.getSign($param);
    }
}
