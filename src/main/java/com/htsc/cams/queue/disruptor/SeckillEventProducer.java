package com.htsc.cams.queue.disruptor;


import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 13:41
 * Description: No Description
 */
public class SeckillEventProducer {

    private final static EventTranslatorVararg translator = new EventTranslatorVararg<SeckillEvent>() {
        @Override
        public void translateTo(SeckillEvent seckillEvent, long seq, Object... objs) {
            seckillEvent.setSeckillId((Long)objs[0]);
            seckillEvent.setUserId((Long)objs[1]);
        }
    };
    private final RingBuffer<SeckillEvent> ringBuffer;

    public SeckillEventProducer(RingBuffer<SeckillEvent> ringBuffer){
        this.ringBuffer = ringBuffer;
    }

    public void seckill(long seckillId,long userId){
        this.ringBuffer.publishEvent(translator,seckillId,userId);
    }

}
