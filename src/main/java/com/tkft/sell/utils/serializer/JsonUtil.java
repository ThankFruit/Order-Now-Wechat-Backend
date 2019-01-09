package com.tkft.sell.utils.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Auther xinyuan
 * @Date 1/8/19 11:11 PM
 **/
public class JsonUtil {
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
