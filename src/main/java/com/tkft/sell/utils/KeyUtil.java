package com.tkft.sell.utils;

import java.util.Random;

/**
 * @Auther xinyuan
 * @Date 11/18/18 7:38 PM
 **/
public class KeyUtil {
    //生成唯一主键 时间+随机数
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
