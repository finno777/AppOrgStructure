package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.SubdivisionApplicationDao;
import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class SubdivisionApplicationDaoImpl extends AbstractDaoImpl<SubdivisionApplication, Long> implements SubdivisionApplicationDao {
    protected SubdivisionApplicationDaoImpl(Class<SubdivisionApplication> entityClass) {
        super(entityClass);
    }
    public SubdivisionApplicationDaoImpl(){super(SubdivisionApplication.class);}

    @Override
    public SubdivisionApplication getAppByAppID(String appId) {
        Criteria criteria = getCurrentSession().createCriteria(SubdivisionApplication.class);
        criteria.add(Restrictions.eq("appId", appId));
        return (SubdivisionApplication) criteria.uniqueResult();
    }

    @Override
    public List<SubdivisionApplication> getAppByAppType(SubdivisionApplication.ApplicationType applicationType) {
        Criteria criteria = getCurrentSession().createCriteria(SubdivisionApplication.class);
        criteria.add(Restrictions.eq("typeApplication", applicationType));
        return criteria.list();
    }
}
