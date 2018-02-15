package com.digdes.rst.orgstructure.persistance.dao.impl;


import com.digdes.rst.orgstructure.persistance.dao.AbstractDao;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author Martynov IS
 */
@Transactional
@Log4j
public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E, I> {

    @Autowired
    SessionFactory sessionFactory;

    private Class<E> entityClass;

    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public E findById(I id) {
        return (E) getCurrentSession().get(entityClass, id);
    }

    @Override
    public E saveOrUpdate(E entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void delete(E entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public E merge(E entity) {
        getCurrentSession().merge(entity);
        return entity;
    }

    @Override
    public List<E> findAll() {
        return getCurrentSession().createCriteria(entityClass).list();
    }

    public <T> T onlyOne(String param, List<T> listObject) {
        if (listObject != null && listObject.size() == 1) {
            return listObject.get(0);
        } else {
            if (listObject == null || listObject.size() == 0) {
                log.trace(String.format("Not found user with param = [%s]", param));
            } else {
                log.trace(String.format("Found by %s more than one user. List size = %d", param, listObject.size()));
            }
        }
        return null;
    }

}
