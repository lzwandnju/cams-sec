package com.htsc.cams.common.aop;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 15:22
 * Description: No Description
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Servicelock {

    String description() default "";

}


