package com.htsc.cams.distributedlock.redis;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/9
 * Time: 19:26
 * Description: No Description
 */
public class RedissLockDemo {

    public void testReentrantLock(RedissonClient redisson){
        RLock lock = redisson.getLock("anyLock");
        try {
            lock.lock();
            lock.lock(10,TimeUnit.SECONDS);
            boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if(res){
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

public void testAsyncReentrantLock(RedissonClient redisson){
    RLock lock = redisson.getLock("anyLock");
    try {
        lock.lockAsync();
        lock.lockAsync(10,TimeUnit.SECONDS);
        RFuture<Boolean> res = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);
        if(res.get()){
            // do your business
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    } finally {
        lock.unlock();
    }

}

public void testFairLock(RedissonClient redisson){
    RLock fairLock = redisson.getFairLock("anyLock");
    try {
        fairLock.lock();
        fairLock.lock(10,TimeUnit.SECONDS);
        boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
        if(res){
            //do your business
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        fairLock.unlock();
    }
    RLock lock = redisson.getFairLock("anyLock");
    try {
        lock.lockAsync();
        lock.lockAsync(10,TimeUnit.SECONDS);
        RFuture<Boolean> res = lock.tryLockAsync(100, 10, TimeUnit.SECONDS);
        if(res.get()){
            // do your business
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    } finally {
        lock.unlock();
    }

}


public void testMultiLock(RedissonClient redisson1,RedissonClient redisson2,RedissonClient redisson3){
    RLock lock1 = redisson1.getLock("lock1");
    RLock lock2 = redisson2.getLock("lock2");
    RLock lock3 = redisson1.getLock("lock3");
    RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);

    try {
        lock.lock();
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        if(res){
            //do your business
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        lock.unlock();
    }

}

public void testRedLock(RedissonClient redisson1,RedissonClient redisson2,RedissonClient redisson3){
    RLock lock1 = redisson1.getLock("lock1");
    RLock lock2 = redisson1.getLock("lock2");
    RLock lock3 = redisson1.getLock("lock3");

    RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
    try {
        lock.lock();
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        if(res){
            // do your business
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        lock.unlock();
    }

    //读写锁（ReadWriteLock）、信号量（Semaphore）、可过期性信号量（PermitExpirableSemaphore）、闭锁（CountDownLatch）
}








































}
