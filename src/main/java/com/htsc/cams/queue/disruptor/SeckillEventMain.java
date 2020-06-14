package com.htsc.cams.queue.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 13:41
 * Description: No Description
 */
public class SeckillEventMain {

    public static void main(String[] args) {

        producerWithTranslator();

    }

    private static void producerWithTranslator(){
        SeckillEventFactory factory = new SeckillEventFactory();
        int ringBufferSize = 1024;
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };
        Disruptor<SeckillEvent> disruptor = new Disruptor<>(factory, ringBufferSize, threadFactory);
        disruptor.handleEventsWith(new SeckillEventConsumer());
        disruptor.start();
        RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();
        SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);
        for (int i = 0; i < 10; i++) {
            producer.seckill(i,i);
        }
    }

}
