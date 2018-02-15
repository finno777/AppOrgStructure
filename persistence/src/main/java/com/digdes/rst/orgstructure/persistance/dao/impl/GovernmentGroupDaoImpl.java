package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentGroupDao;
import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentGroup;
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
public class GovernmentGroupDaoImpl  extends AbstractDaoImpl<GovernmentGroup, Long> implements GovernmentGroupDao {
    protected GovernmentGroupDaoImpl(Class<GovernmentGroup> entityClass) {
        super(entityClass);
    }
    public GovernmentGroupDaoImpl(){super(GovernmentGroup.class);}

    @Override
    public List<GovernmentGroup> getByGovernment(Government government) {
            Criteria criteria = getCurrentSession().createCriteria(GovernmentGroup.class);
            criteria.add(Restrictions.eq("government", government));
            return criteria.list();
    }

    @Override
    public GovernmentGroup getByGovernmentGroup(GovernmentGroup governmentGroup) {
        Criteria criteria = getCurrentSession().createCriteria(GovernmentGroup.class);
        criteria.add(Restrictions.eq("government", governmentGroup.getGovernment()));
        criteria.add(Restrictions.eq("groupOrganizations", governmentGroup.getGroupOrganizations()));
        return (GovernmentGroup) criteria.uniqueResult();
    }
}
