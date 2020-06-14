package com.htsc.cams.common.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/8
 * Time: 11:12
 * Description: No Description
 */
public class LockDemo {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lockDemo();
    }
    public static void lockDemo(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            final int num = i;
            new Runnable(){
                /**
                 * When an object implementing interface <code>Runnable</code> is used
                 * to create a thread, starting the thread causes the object's
                 * <code>run</code> method to be called in that separately executing
                 * thread.
                 * <p>
                 * The general contract of the method <code>run</code> is that it may
                 * take any action whatsoever.
                 *
                 * @see Thread#run()
                 */
                @Override
                public void run() {
                    sync(num);
                }
            }.run();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public static void lock(int i){
        lock.lock();
        lock.unlock();
    }
    public static synchronized void sync(int i){}

}
