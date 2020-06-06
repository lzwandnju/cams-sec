package com.htsc.cams.common.dynamicquery;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 16:21
 * Description: No Description
 */
public interface DynamicQuery {

    public void save(Object entity);

    public void update(Object entity);

    public <T> void delete(Class<T> entityClass,Object entity);

    public <T> void delete(Class<T> entityClass,Object[]entityids);

    <T> List<T> nativeQueryList(String nativeSql, Object... params);

    <T> List<T> nativeQueryListMap(String nativeSql,Object... params);

    <T> List<T> nativeQueryListModel(Class<T> resultClass,String nativeSql,Object... params);

    Object nativeQueryObject(String nativeSql,Object... params);

    Object[] nativeQueryArray(String nativeSql,Object... params);

    int nativeExecuteUpdate(String nativeSql,Object... params);

}
