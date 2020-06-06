package com.htsc.cams.common.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 15:23
 * Description: No Description
 */
@Component
@Scope
@Aspect
public class LockAspect {

    private static Lock lock = new ReentrantLock(true);

    @Pointcut("@annotation(com.htsc.cams.common.aop.Servicelock)")
    public void lockAspect(){}

    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint){
        lock.lock();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }

}
