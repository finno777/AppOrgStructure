package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentPersonDao;
import com.digdes.rst.orgstructure.persistance.model.GovernmentPerson;
import com.digdes.rst.orgstructure.persistance.model.Person;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class GovernmentPersonDaoImpl extends AbstractDaoImpl<GovernmentPerson, Long> implements GovernmentPersonDao {
    protected GovernmentPersonDaoImpl(Class<GovernmentPerson> entityClass) {
        super(entityClass);
    }
    public GovernmentPersonDaoImpl(){super(GovernmentPerson.class);}

    @Override
    public List<GovernmentPerson> findByPerson(Person person) {
        Criteria criteria = getCurrentSession().createCriteria(GovernmentPerson.class);
        criteria.add(Restrictions.eq("person", person));
        return criteria.list();
    }
}