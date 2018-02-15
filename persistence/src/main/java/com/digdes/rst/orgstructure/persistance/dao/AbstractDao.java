package com.digdes.rst.orgstructure.persistance.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * @author Martynov IS
 */
public interface AbstractDao<E, I extends Serializable> {

    public E findById(I id);

    public E saveOrUpdate(E entity);

    public void delete(E entity);

    E merge(E entity);

    List<E> findAll();

    public Session getCurrentSession();


}
