package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.ApplicationDao;
import com.digdes.rst.orgstructure.persistance.model.Application;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ApplicationDaoImpl extends AbstractDaoImpl<Application, Long> implements ApplicationDao {

    protected ApplicationDaoImpl(Class<Application> entityClass) {
        super(entityClass);
    }
    public ApplicationDaoImpl(){super(Application.class);}


    @SuppressWarnings("unchecked")
    @Override
    public Application findByAppId(String appId) {
        Criteria criteria = getCurrentSession().createCriteria(Application.class);
        criteria.add(Restrictions.eq("appId", appId));
        return (Application) criteria.uniqueResult();
    }
}
