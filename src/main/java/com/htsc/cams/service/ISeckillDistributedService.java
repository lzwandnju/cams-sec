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
public interface ISeckillDistributedService {

    Result startSeckilRedisLock(long seckillId,long userId);

    Result startSeckilZksLock(long seckillId,long userId);

    Result startSeckillLock(long seckillId,long userId,long number);

}
