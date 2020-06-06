package com.htsc.cams.common.dynamicquery;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 16:32
 * Description: No Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeQueryResultEntity {

}
