package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.DivisionDao;
import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.model.Division;
import com.digdes.rst.orgstructure.persistance.model.Government;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class DivisionDaoImpl extends AbstractDaoImpl<Division, Long> implements DivisionDao {
    protected DivisionDaoImpl(Class<Division> entityClass) {
        super(entityClass);
    }
    public DivisionDaoImpl(){super(Division.class);}

    @Override
    public List<Division> findByGovernment(Government byId) {
        Criteria criteria = getCurrentSession().createCriteria(Division.class);
        criteria.add(Restrictions.eq("government", byId));
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
