package com.htsc.cams.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/8
 * Time: 11:39
 * Description: No Description
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    public static final String KEY_PREFIX_VALUE = "htsc:seckill:value";

    public boolean cacheValue(String k,Serializable v,long time){
        String key = KEY_PREFIX_VALUE + k;
        try {
            ValueOperations<Serializable, Serializable> valueOps = redisTemplate.opsForValue();
            valueOps.set(key,v);
            if(time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cacheValue(String k,Serializable v,long time,TimeUnit unit){
        String key = KEY_PREFIX_VALUE + k;
        try {
            ValueOperations<Serializable, Serializable> valueOps = redisTemplate.opsForValue();
            valueOps.set(key,v);
            if(time>0){
                redisTemplate.expire(key,time,unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cacheValue(String k,Serializable v) {
        return cacheValue(k,v,-1);
    }

    public boolean containsValueKey(String k){
        String key = KEY_PREFIX_VALUE+k;
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Serializable getValue(String k){
        try {
            ValueOperations<Serializable, Serializable> valueOps = redisTemplate.opsForValue();
            return valueOps.get(KEY_PREFIX_VALUE+k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removeValue(String k){
        String key = KEY_PREFIX_VALUE + k;
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }














}
