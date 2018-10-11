package com.plumblum.miaosha.utils;

import java.util.UUID;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/9
 * Time:22:23
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
