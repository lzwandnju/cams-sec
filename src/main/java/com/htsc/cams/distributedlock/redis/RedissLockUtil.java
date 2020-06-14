package com.htsc.cams.distributedlock.redis;

import jodd.datetime.TimeUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/9
 * Time: 19:26
 * Description: No Description
 */
public class RedissLockUtil {

    private static RedissonClient redissonClient;

    public  RedissonClient getRedissonClient() {
        return redissonClient;
    }

    public  void setRedissonClient(RedissonClient redissonClient) {
        RedissLockUtil.redissonClient = redissonClient;
    }

    public static RLock lock(String lockKey){
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    public static void unlock(String lockKey){
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    public static void unlock(RLock lock){
        lock.unlock();
    }

    public static RLock lock(String lockKey,int timeout){
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    public static RLock lock(String lockKey,TimeUnit unit,int timeout){
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout,unit);
        return lock;
    }

    public static boolean tryLock(String lockKey,int waitTime,int leaseTime){
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime,leaseTime,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tryLock(String lockKey,TimeUnit unit,int waitTime,int leaseTime){
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime,leaseTime,unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


}
