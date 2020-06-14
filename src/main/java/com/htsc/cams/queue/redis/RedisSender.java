package com.htsc.cams.queue.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 15:50
 * Description: No Description
 */
@Service
public class RedisSender {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sendChannelMess(String channel,String message){
        stringRedisTemplate.convertAndSend(channel,message);
    }
}


