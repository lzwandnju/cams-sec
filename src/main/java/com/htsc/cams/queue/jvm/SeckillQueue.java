package com.htsc.cams.queue.jvm;

import com.htsc.cams.common.entity.SuccessKilled;
import org.codehaus.groovy.transform.tailrec.VariableAccessReplacer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 15:33
 * Description: No Description
 */
public class SeckillQueue {

    static final int QUEUE_MAX_SIZE = 100;
    static BlockingQueue<SuccessKilled> blockingQueue = new LinkedBlockingQueue<SuccessKilled>(QUEUE_MAX_SIZE);

    private SeckillQueue(){}

    private static class SingletonHolder{
        private static SeckillQueue queue = new SeckillQueue();
    }

    public static SeckillQueue getMailQueue(){
        return SingletonHolder.queue;
    }

    public Boolean produce(SuccessKilled kill)  throws InterruptedException {
        return blockingQueue.offer(kill);
    }

    public SuccessKilled consume() throws InterruptedException {
        return blockingQueue.take();
    }

    public int size(){
        return blockingQueue.size();
    }

}
