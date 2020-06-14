package com.htsc.cams.service.impl;

import com.htsc.cams.common.dynamicquery.DynamicQuery;
import com.htsc.cams.common.entity.Result;
import com.htsc.cams.common.entity.Seckill;
import com.htsc.cams.common.entity.SuccessKilled;
import com.htsc.cams.common.enums.SeckillStatEnum;
import com.htsc.cams.repository.SeckillRepository;
import com.htsc.cams.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 16:33
 * Description: No Description
 */
@Service("seckillService")
public class SeckillServiceImpl implements ISeckillService {

    private Lock lock = new ReentrantLock(true);

    @Autowired
    private DynamicQuery dynamicQuery;

    @Autowired
    private SeckillRepository seckillRepository;


    @Override
    public List<Seckill> getSeckillList() {
        return seckillRepository.findAll();
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillRepository.findOne(seckillId);
    }

    @Override
    public Long getSeckillCount(long seckillId) {
        String nativeSql = "SELECT count(*) FROM success_killed WHRRE seckill)id = ?";
        Object object = dynamicQuery.nativeQueryObject(nativeSql,new Object[]{seckillId});
        return  ((Number)object).longValue();
    }

    @Override
    @Transactional
    public void deleteSeckill(long seckillId) {
        String nativeSql = "DELETE FROM success_killed WHERE seckill_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{seckillId});
        nativeSql = "UPDATE seckill SET number = 100 WHERE seckill_id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{seckillId});
    }

    @Override
    @Transactional
    public Result startSeckil(long seckillId, long userId) {
        //校验库存
        String sql = "SELECT number FROM seckill WHERE seckill_id=?";
        long number = checkExist(sql, seckillId);
        String newSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
        return startSeckillTemplate(newSql,number,seckillId,userId,false);
    }

    @Override
    @Transactional
    public Result startSeckilLock(long seckillId, long userId) {
        try {
            lock.lock();
            String sql =  "SELECT number FROM seckill WHERE seckill_id=?";
            long number = checkExist(sql, seckillId);
            String newSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
            return startSeckillTemplate(newSql,number,seckillId,userId,true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return Result.ok(SeckillStatEnum.SUCCESS);
    }

    @Override
    @Transactional
    public Result startSeckilAopLock(long seckillId, long userId) {
        String sql = "SELECT number FROM seckill WHERE seckill_id=?";
        long number = checkExist(sql, seckillId);
        String newSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
        return startSeckillTemplate(newSql,number,seckillId,userId,true);
    }

    @Override
    @Transactional
    public Result startSeckilDBPCC_ONE(long seckillId, long userId) {
        String sql = "SELECT number FROM seckill WHERE seckill_id=? FOR UPDATE";
        long number = checkExist(sql, seckillId);
        String newSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
        return startSeckillTemplate(newSql,number,seckillId,userId,false);
    }

    @Override
    @Transactional
    public Result startSeckilDBPCC_TWO(long seckillId, long userId) {
        //单用户抢购一件商品没有问题、但是抢购多件商品不建议这种写法
        String sql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=? AND number>0";//UPDATE锁表
        int count = dynamicQuery.nativeExecuteUpdate(sql, new Object[]{seckillId});
        return startGenerateSeckill(count,seckillId,userId);
    }

    @Override
    @Transactional
    public Result startSeckilDBOCC(long seckillId, long userId, long number) {
        Seckill kill = seckillRepository.findOne(seckillId);
        if(kill.getNumber()>0){
            //乐观锁
            String sql = "UPDATE seckill  SET number=number-?,version=version+1 WHERE seckill_id=? AND version = ?";
            int count = dynamicQuery.nativeExecuteUpdate(sql, new Object[]{number,seckillId,kill.getVersion()});
            return startGenerateSeckill(count,seckillId,userId);
        }else{
            return Result.error(SeckillStatEnum.END);
        }
    }

    public Result startGenerateSeckill(int count,long seckillId,long userId){
        if(count>0){
            SuccessKilled killed = new SuccessKilled();
            killed.setSeckillId(seckillId);
            killed.setUserId(userId);
            killed.setState((short)0);
            killed.setCreateTime(new Timestamp(new Date().getTime()));
            dynamicQuery.save(killed);
            return Result.ok(SeckillStatEnum.SUCCESS);
        }else{
            return Result.error(SeckillStatEnum.END);
        }
    }

    public Result startSeckillTemplate(String sql, long count, long seckillId,long userId,boolean flag){
        if(count>0){
            dynamicQuery.nativeExecuteUpdate(sql, new Object[]{seckillId});
            SuccessKilled killed = new SuccessKilled();
            killed.setSeckillId(seckillId);
            killed.setUserId(userId);
            if(flag){
                killed.setState(Short.parseShort(count+""));
            }else {
                killed.setState((short) 0);
            }
            killed.setCreateTime(new Timestamp(new Date().getTime()));
            dynamicQuery.save(killed);
            return Result.ok(SeckillStatEnum.SUCCESS);
        }else{
            return Result.error(SeckillStatEnum.END);
        }
    }

    public long checkExist(String sql,long seckillId){
        Object object =  dynamicQuery.nativeQueryObject(sql, new Object[]{seckillId});
        long number =  ((Number) object).longValue();
        return number;
    }

}
