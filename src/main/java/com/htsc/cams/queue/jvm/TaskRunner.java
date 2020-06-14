package com.htsc.cams.queue.jvm;

import com.htsc.cams.common.entity.SuccessKilled;
import com.htsc.cams.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 15:37
 * Description: No Description
 */
@Component
public class TaskRunner implements ApplicationRunner {

    @Autowired
    private ISeckillService seckillService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        while(true){
            SuccessKilled kill = SeckillQueue.getMailQueue().consume();
            if(kill!=null){
                seckillService.startSeckil(kill.getSeckillId(),kill.getUserId());
            }
        }
    }
}
