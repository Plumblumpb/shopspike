package com.plumblum.miaosha.redis;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/9
 * Time:21:26
 */
public interface KeyPrefix {

//    过期时间
    public int expireSeconds();

//    前缀
    public String getPrefix();
}
