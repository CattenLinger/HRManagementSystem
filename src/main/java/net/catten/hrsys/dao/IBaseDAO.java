package net.catten.hrsys.dao;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by catten on 16/5/26.
 */
public interface IBaseDAO<Entity> {

    /**
     * @param entity
     * @return
     */
    Entity save(Entity entity);

    /**
     * @param id
     * @return
     */
    Entity load(Serializable id);

    /**
     * @param id
     */
    void delete(Serializable id);

    /**
     * @param entity
     */
    void update(Entity entity);

    /**
     * @param hql
     * @param args
     * @return
     */
    Collection<Entity> collection(String hql,Object...args);

    /**
     * @param hql
     * @param args
     * @return
     */
    Entity query(String hql,Object...args);
}
