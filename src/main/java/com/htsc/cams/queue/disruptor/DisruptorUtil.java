package com.htsc.cams.queue.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 13:40
 * Description: No Description
 */
@Component
public class DisruptorUtil {
    static Disruptor<SeckillEvent> disruptor = null;
    static {
        SeckillEventFactory factory = new SeckillEventFactory();
        int ringBufferSize = 1024;
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };
        disruptor = new Disruptor<SeckillEvent>(factory,ringBufferSize,threadFactory);
        disruptor.handleEventsWith(new SeckillEventConsumer());
        disruptor.start();
    }

    public static void producer(SeckillEvent kill){
        RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();
        SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);
        producer.seckill(kill.getSeckillId(),kill.getUserId());
    }

}
