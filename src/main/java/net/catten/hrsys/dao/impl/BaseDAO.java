package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IBaseDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Created by catten on 16/5/26.
 */
public abstract class BaseDAO<Entity> implements IBaseDAO<Entity> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Class<Entity> clazz = null;

    public BaseDAO(){
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        clazz = (Class<Entity>) type.getActualTypeArguments()[0];
    }

    private void bindParameter(Query query,Object...args){
        if(args!=null&&args.length>0){
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i,args[i]);
            }
        }
    }

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Entity save(Entity entity) {
        getSession().save(entity);
        return entity;
    }

    @Override
    public Entity load(Serializable id) {
        return getSession().load(clazz,id);
    }

    @Override
    public void delete(Serializable id) {
        getSession().delete(load(id));
    }

    @Override
    public void update(Object entity) {
        getSession().update(entity);
    }

    @Override
    public Collection<Entity> collection(String hql, Object... args) {
        Query query = getSession().createQuery(hql);
        bindParameter(query,args);
        return query.list();
    }

    @Override
    public Entity query(String hql, Object... args) {
        Query query = getSession().createQuery(hql);
        bindParameter(query,args);
        return (Entity) query.uniqueResult();
    }
}
