package com.htsc.cams.queue.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 15:45
 * Description: No Description
 */
@Component
public class KafkaSender {


    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendChannelMess(String channel,String message){
        kafkaTemplate.send(channel,message);
    }

}
