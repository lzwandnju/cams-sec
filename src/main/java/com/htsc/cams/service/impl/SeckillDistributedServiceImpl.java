package com.htsc.cams.service.impl;

import com.htsc.cams.common.dynamicquery.DynamicQuery;
import com.htsc.cams.common.entity.Result;
import com.htsc.cams.common.entity.SuccessKilled;
import com.htsc.cams.common.enums.SeckillStatEnum;
import com.htsc.cams.distributedlock.redis.RedissLockUtil;
import com.htsc.cams.distributedlock.zookeeper.ZkLockUtil;
import com.htsc.cams.service.ISeckillDistributedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 16:33
 * Description: No Description
 */
@Service
public class SeckillDistributedServiceImpl implements ISeckillDistributedService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    @Transactional
    public Result startSeckilRedisLock(long seckillId, long userId) {
        boolean res = false;
        try {
            res = RedissLockUtil.tryLock(seckillId+"", TimeUnit.SECONDS,3,10);
            long count = checkExist(seckillId);
            return startGenerateSeckill(count,seckillId,userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(res){
                RedissLockUtil.unlock(seckillId+"");
            }
        }
        return Result.ok(SeckillStatEnum.SUCCESS);
    }

    @Override
    @Transactional
    public Result startSeckilZksLock(long seckillId, long userId) {
        try {
            ZkLockUtil.acquire(seckillId+"");
            long count = checkExist(seckillId);
            return startGenerateSeckill(count, seckillId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ZkLockUtil.release(seckillId+"");
        }
        return Result.ok(SeckillStatEnum.SUCCESS);
    }

    @Override
    @Transactional
    public Result startSeckillLock(long seckillId, long userId, long number) {
        boolean res = false;
        try {
            res = RedissLockUtil.tryLock(seckillId + "", TimeUnit.SECONDS, 3, 20);
            long count = checkExist(seckillId);
            return startGenerateSeckill(count,seckillId,userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(res){
                RedissLockUtil.unlock(seckillId+"");
            }
        }
        return Result.ok(SeckillStatEnum.SUCCESS);
    }

    public Result startGenerateSeckill(long number,long seckillId,long userId){
        if(number>0){
            SuccessKilled killed = new SuccessKilled();
            killed.setSeckillId(seckillId);
            killed.setUserId(userId);
            killed.setState((short)0);
            killed.setCreateTime(new Timestamp(new Date().getTime()));
            dynamicQuery.save(killed);
            String nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=? AND number>0";
            dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
            return Result.ok(SeckillStatEnum.SUCCESS);
        }else{
            return Result.error(SeckillStatEnum.END);
        }
    }

    public long checkExist(long seckillId){
        String sql = "SELECT number FROM seckill WHERE seckill_id=?";
        Object object =  dynamicQuery.nativeQueryObject(sql, new Object[]{seckillId});
        long number =  ((Number) object).longValue();
        return number;
    }

}
