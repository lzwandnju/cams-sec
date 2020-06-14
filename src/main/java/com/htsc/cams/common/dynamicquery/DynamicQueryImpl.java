package com.htsc.cams.common.dynamicquery;

import jdk.nashorn.internal.ir.CallNode;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 16:33
 * Description: No Description
 */
@Repository
public class DynamicQueryImpl implements DynamicQuery{

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    @Override
    public void save(Object entity) {
        em.persist(entity);
    }

    @Override
    public void update(Object entity) {
        em.merge(entity);
    }

    @Override
    public <T> void delete(Class<T> entityClass, Object entityid) {
        delete(entityClass,new Object[]{entityid});
    }

    @Override
    public <T> void delete(Class<T> entityClass, Object[] entityids) {
        for(Object id : entityids){
            em.remove(em.getReference(entityClass,id));
        }
    }

    private Query createNativeQuery(String sql, Object... params){
        Query q = em.createNativeQuery(sql);
        if(params != null && params.length>0){
            for(int i=0;i<params.length;i++){
                q.setParameter(i+1,params[i]);
            }
        }
        return q;
    }

    @Override
    public <T> List<T> nativeQueryList(String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.TO_LIST);
        return q.getResultList();
    }

    @Override
    public <T> List<T> nativeQueryListMap(String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return q.getResultList();
    }

    @Override
    public <T> List<T> nativeQueryListModel(Class<T> resultClass, String nativeSql, Object... params) {
        Query q = createNativeQuery(nativeSql, params);
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(resultClass));
        return q.getResultList();
    }

    @Override
    public Object nativeQueryObject(String nativeSql, Object... params) {
        int size = createNativeQuery(nativeSql, params).getResultList().size();
        if(size==0){
            System.out.println("The result is empty");
            return null;
        }
        return createNativeQuery(nativeSql,params).getSingleResult();
    }

    @Override
    public  Object[] nativeQueryArray(String nativeSql, Object... params) {
        int size = createNativeQuery(nativeSql, params).getResultList().size();
        if(size==0){
            System.out.println("The result is empty");
            return null;
        }
        return (Object[])createNativeQuery(nativeSql,params).getSingleResult();
    }

    @Override
    public int nativeExecuteUpdate(String nativeSql, Object... params) {
        return createNativeQuery(nativeSql,params).executeUpdate();
    }
}
