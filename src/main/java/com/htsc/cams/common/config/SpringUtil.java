package com.htsc.cams.common.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 15:36
 * Description: No Description
 */
@Component
public class SpringUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext = applicationContext;
        }
        System.out.println("http://www.baidu.com");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T>T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name,clazz);
    }
}
