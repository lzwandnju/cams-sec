package com.htsc.cams.queue.disruptor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/10
 * Time: 13:40
 * Description: No Description
 */
public class SeckillEvent implements Serializable {

    private static final long serialVersionUID = -9122317274623686519L;

    private long seckillId;

    private long userId;

    public SeckillEvent() {
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
