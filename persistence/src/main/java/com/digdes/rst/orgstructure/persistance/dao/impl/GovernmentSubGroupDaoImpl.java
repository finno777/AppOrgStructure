package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentSubGroupDao;
import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentSubGroup;
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
public class GovernmentSubGroupDaoImpl  extends AbstractDaoImpl<GovernmentSubGroup, Long> implements GovernmentSubGroupDao {
    protected GovernmentSubGroupDaoImpl(Class<GovernmentSubGroup> entityClass) {
        super(entityClass);
    }
    public GovernmentSubGroupDaoImpl(){super(GovernmentSubGroup.class);}

    @Override
    public List<GovernmentSubGroup> getByGovernment(Government government) {
        Criteria criteria = getCurrentSession().createCriteria(GovernmentSubGroup.class);
        criteria.add(Restrictions.eq("government", government));
        return criteria.list();
    }

    @Override
    public GovernmentSubGroup getByGovernmentSubGroup(GovernmentSubGroup subGroup) {
        Criteria criteria = getCurrentSession().createCriteria(GovernmentSubGroup.class);
        criteria.add(Restrictions.eq("government", subGroup.getGovernment()));
        criteria.add(Restrictions.eq("subGroupOrganizations", subGroup.getSubGroupOrganizations()));
        return (GovernmentSubGroup) criteria.uniqueResult();
    }
}
