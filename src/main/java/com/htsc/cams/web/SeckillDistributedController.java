package com.htsc.cams.web;

import com.htsc.cams.common.entity.Result;
import com.htsc.cams.queue.kafka.KafkaSender;
import com.htsc.cams.queue.redis.RedisSender;
import com.htsc.cams.service.ISeckillDistributedService;
import com.htsc.cams.service.ISeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 16:34
 * Description: No Description
 */
@Api(tags = "Distribute Seckill")
@RestController
@RequestMapping("/seckillDistributed")
public class SeckillDistributedController {

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,corePoolSize+1,101, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10000));

    @Autowired
    private ISeckillService seckillService;

    @Autowired
    private ISeckillDistributedService seckillDistributedService;

    @Autowired
    private RedisSender redisSender;

    @Autowired
    private KafkaSender kafkaSender;


    @ApiOperation(value="seckill-1 Rediss",nickname = "lzw")
    @PostMapping("/startRedisLock")
    public Result startRedisLock(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillDistributedService.startSeckilRedisLock(killId,userId);
                    System.out.println("user: "+userId+" "+result.get("msg"));
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(15000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
            System.out.println("count: "+seckillCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-2 zookepper",nickname = "lzw")
    @PostMapping("/startZkLock")
    public Result startZkLock(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillDistributedService.startSeckilZksLock(killId,userId);
                    System.out.println("user: "+userId+" "+result.get("msg"));
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(15000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
            System.out.println("count: "+seckillCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-3 Rediss",nickname = "lzw")
    @PostMapping("/startRedisQueue")
    public Result startRedisQueue(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    redisSender.sendChannelMess("seckill",killId+";"+userId);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(15000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
            System.out.println("count: "+seckillCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-4 Kafka",nickname = "lzw")
    @PostMapping("/startKafkaQueue")
    public Result startKafkaQueue(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    kafkaSender.sendChannelMess("seckill",killId+";"+userId);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(15000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
            System.out.println("count: "+seckillCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

}
