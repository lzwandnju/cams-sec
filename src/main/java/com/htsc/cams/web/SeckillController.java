package com.htsc.cams.web;

import com.htsc.cams.common.entity.Result;
import com.htsc.cams.common.entity.SuccessKilled;
import com.htsc.cams.queue.disruptor.DisruptorUtil;
import com.htsc.cams.queue.disruptor.SeckillEvent;
import com.htsc.cams.queue.jvm.SeckillQueue;
import com.htsc.cams.service.ISeckillService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 16:33
 * Description: No Description
 */
@Slf4j
@Api(tags="Seckill")
@RestController
@RequestMapping("/seckill")
public class SeckillController {
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1,101,TimeUnit.SECONDS,
                           new LinkedBlockingQueue<Runnable>(1000));

    @Autowired
    private ISeckillService seckillService;

    @ApiOperation(value="seckill-1(low)",nickname = "lzw")
    @PostMapping("/start")
    public Result start(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillService.startSeckil(killId, userId);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-2(low)",nickname = "lzw")
    @PostMapping("/startLock")
    public Result startLock(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillService.startSeckilLock(killId, userId);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-3(low)",nickname = "lzw")
    @PostMapping("/startAopLock")
    public Result startAopLock(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillService.startSeckilAopLock(killId, userId);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-4(low)",nickname = "lzw")
    @PostMapping("/startDBPCC_ONE")
    public Result startDBPCC_ONE(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillService.startSeckilDBPCC_ONE(killId, userId);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }


    @ApiOperation(value="seckill-5(low)",nickname = "lzw")
    @PostMapping("/startDBPCC_TWO")
    public Result startDBPCC_TWO(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillService.startSeckilDBPCC_TWO(killId, userId);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }


    @ApiOperation(value="seckill-6(low)",nickname = "lzw")
    @PostMapping("/startDBOCC")
    public Result startDBOCC(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Result result = seckillService.startSeckilDBOCC(killId, userId,4);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-7",nickname = "lzw")
    @PostMapping("/startQueue")
    public Result startQueue(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    SuccessKilled kill = new SuccessKilled();
                    kill.setSeckillId(seckillId);
                    kill.setUserId(userId);
                    try {
                        Boolean flag = SeckillQueue.getMailQueue().produce(kill);
                        if(flag){
                            System.out.println("success");
                        }else{
                            System.out.println("failed");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
            System.out.println("seckillCount: "+seckillCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="seckill-8", nickname = "lzw")
    @PostMapping("/startDisuptorQueue")
    public Result startDisruptorQueue(long seckillId){
        seckillService.deleteSeckill(seckillId);
        final long killId = seckillId;
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task  = new Runnable() {
                @Override
                public void run() {
                    SeckillEvent kill = new SeckillEvent();
                    kill.setSeckillId(killId);
                    kill.setUserId(userId);
                    DisruptorUtil.producer(kill);
                }
            };
            executor.execute(task);
        }
        try {
            Thread.sleep(10000);
            Long seckillCount = seckillService.getSeckillCount(seckillId);
            System.out.println("Total size: " +seckillCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

}

