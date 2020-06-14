package com.htsc.cams.distributedlock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 12:04
 * Description: No Description
 */
public class ZkLockUtil {

    @Value("${zookeeper.address}")
    private static String address;

    private static CuratorFramework client;

    static {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        client.start();
    }

    public static void acquire(String lockKey){
        try {
            InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock/" + lockKey);
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void release(String lockKey){
        try {
            InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock/" + lockKey);
            mutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
