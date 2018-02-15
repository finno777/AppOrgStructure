package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentDao;
import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentApplication;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class GovernmentDaoImpl extends AbstractDaoImpl<Government, Long> implements GovernmentDao {
    protected GovernmentDaoImpl(Class<Government> entityClass) {
        super(entityClass);
    }
    public GovernmentDaoImpl(){super(Government.class);}

    @Override
    public List<Government> findByApp(GovernmentApplication application) {
        Criteria criteria = getCurrentSession().createCriteria(Government.class);
        criteria.add(Restrictions.eq("application", application));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.addOrder(Order.asc("position"));
        return criteria.list();
    }

    @Override
    public List<Government> getAllActive() {
        Criteria criteria = getCurrentSession().createCriteria(Government.class);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }
}
