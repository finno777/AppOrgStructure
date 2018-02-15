package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentOrganizationDao;
import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentOrganization;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */

@Repository
@Transactional
@Log4j
public class GovernmentOrganizationDaoImpl  extends AbstractDaoImpl<GovernmentOrganization, Long> implements GovernmentOrganizationDao {
    protected GovernmentOrganizationDaoImpl(Class<GovernmentOrganization> entityClass) {
        super(entityClass);
    }
    public GovernmentOrganizationDaoImpl(){super(GovernmentOrganization.class);}

    @Override
    public List<GovernmentOrganization> getByGovernment(Government government) {
        Criteria criteria = getCurrentSession().createCriteria(GovernmentOrganization.class);
        criteria.add(Restrictions.eq("government", government));
        return criteria.list();
    }

    @Override
    public GovernmentOrganization getByGovernmentOrganization(GovernmentOrganization governmentOrganization) {
        Criteria criteria = getCurrentSession().createCriteria(GovernmentOrganization.class);
        criteria.add(Restrictions.eq("government", governmentOrganization.getGovernment()));
        criteria.add(Restrictions.eq("organization", governmentOrganization.getOrganization()));
        return (GovernmentOrganization)criteria.uniqueResult();
    }
}
