package com.htsc.cams.queue.disruptor;


import com.htsc.cams.common.config.SpringUtil;
import com.htsc.cams.service.ISeckillService;
import com.lmax.disruptor.EventHandler;
import jdk.nashorn.internal.ir.CallNode;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 13:40
 * Description: No Description
 */
public class SeckillEventConsumer implements EventHandler<SeckillEvent> {

    private ISeckillService seckillService = (ISeckillService) SpringUtil.getBean("seckillService");

    @Override
    public void onEvent(SeckillEvent seckillEvent, long l, boolean b) throws Exception {
        seckillService.startSeckil(seckillEvent.getSeckillId(),seckillEvent.getUserId());
    }
}
