package com.htsc.cams.service;

import com.htsc.cams.common.entity.Result;
import com.htsc.cams.common.entity.Seckill;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 16:32
 * Description: No Description
 */
public interface ISeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    Long getSeckillCount(long seckillId);

    void deleteSeckill(long seckillId);

    Result startSeckil(long seckillId, long userId);

    Result startSeckilLock(long seckillId,long userId);

    Result startSeckilAopLock(long seckillId,long userId);

    Result startSeckilDBPCC_ONE(long seckillId,long userId);

    Result startSeckilDBPCC_TWO(long seckillId,long userId);

    Result startSeckilDBOCC(long seckillId,long userId,long number);

}
