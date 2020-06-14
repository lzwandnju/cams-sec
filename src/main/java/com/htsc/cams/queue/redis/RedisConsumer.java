package com.htsc.cams.queue.redis;

import com.htsc.cams.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 15:50
 * Description: No Description
 */
@Service
public class RedisConsumer {


    @Autowired
    private ISeckillService seckillService;

    public void receiveMessage(String message){
        String[] array = message.split(";");
        seckillService.startSeckil(Long.parseLong(array[0]),Long.parseLong(array[1]));
    }

}
