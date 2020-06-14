package com.htsc.cams.queue.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 13:40
 * Description: No Description
 */
public class SeckillEventFactory implements EventFactory<SeckillEvent> {

    public SeckillEvent newInstance(){
        return new SeckillEvent();
    }

}
