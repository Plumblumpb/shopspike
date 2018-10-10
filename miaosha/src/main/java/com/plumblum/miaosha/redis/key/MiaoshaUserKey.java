package com.plumblum.miaosha.redis.key;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/9
 * Time:21:34
 */
public class MiaoshaUserKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    private MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
}
