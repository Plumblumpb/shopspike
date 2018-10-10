package com.plumblum.miaosha.redis.key;

import com.plumblum.miaosha.redis.KeyPrefix;

/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/9
 * Time:21:27
 */
public abstract class BasePrefix implements KeyPrefix {

//    过期时间
    public int expireSeconds;

//    前缀
    public String prefix;

    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds ,String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

//    修改key策略
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }



}
