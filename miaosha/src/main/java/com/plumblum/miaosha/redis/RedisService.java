package com.plumblum.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * Created with IDEA
 * author:plumblum
 * Date:2018/10/8
 * Time:23:20
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

//    获取对象
    public <T> T  get(KeyPrefix prefix, String key ,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix() + key;
            String value = jedis.get(realKey);

            T t = stringToEntity(value,clazz);
            return t;
        }finally {
//
            returnToPool(jedis);
        }
    }

//    设置对象
    public <T> boolean set(KeyPrefix keyPrefix,String key,T t){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();

            String realKey = keyPrefix.getPrefix() + key;
            int seconds = keyPrefix.expireSeconds();
            String value = beanToString(t);
            if(value == null || value.length() <= 0) {
                return false;
            }
            if(seconds <= 0) {
                jedis.set(realKey, value);
            }else {
                jedis.setex(realKey, seconds, value);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

//    实体类转为JSON
    private <T> String beanToString(T value){
        if(value == null){
            return  null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

//    JSON转为实体类
    @SuppressWarnings("unchecked")
    private <T> T stringToEntity(String str,Class<T> clazz){
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return  (T)Integer.valueOf(str);
        }else if(clazz == String.class){
            return (T)str;
        }else if (clazz == Long.class || clazz == long.class){
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }

    }

// 关闭jedis
    private void returnToPool(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }
}
