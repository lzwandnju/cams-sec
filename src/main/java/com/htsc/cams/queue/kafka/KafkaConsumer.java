package com.htsc.cams.queue.kafka;

import com.htsc.cams.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 15:45
 * Description: No Description
 */
@Component
public class KafkaConsumer {
    @Autowired
    private ISeckillService seckillService;



    @KafkaListener(topics = {"seckill"})
    public void receiveMessage(String message){
        String[] array = message.split(";");
        seckillService.startSeckil(Long.parseLong(array[0]),Long.parseLong(array[1]));
    }
}
