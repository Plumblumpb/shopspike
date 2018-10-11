package com.plumblum.miaosha.redis.key;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/9
 * Time:21:39
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
