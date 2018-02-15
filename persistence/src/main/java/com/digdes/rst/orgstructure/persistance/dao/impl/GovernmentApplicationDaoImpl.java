package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentApplicationDao;
import com.digdes.rst.orgstructure.persistance.model.GovernmentApplication;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Log4j
public class GovernmentApplicationDaoImpl extends AbstractDaoImpl<GovernmentApplication, Long> implements GovernmentApplicationDao {
    protected GovernmentApplicationDaoImpl(Class<GovernmentApplication> entityClass) {
        super(entityClass);
    }
    public GovernmentApplicationDaoImpl(){super(GovernmentApplication.class);}

    @Override
    public GovernmentApplication getAppByAppID(String appId) {
        Criteria criteria = getCurrentSession().createCriteria(GovernmentApplication.class);
        criteria.add(Restrictions.eq("appId", appId));
        return (GovernmentApplication) criteria.uniqueResult();
    }
}
