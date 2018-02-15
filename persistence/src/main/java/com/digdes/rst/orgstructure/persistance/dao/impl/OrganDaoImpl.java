package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.OrganDao;
import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.Person;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Repository
@Log4j
public class OrganDaoImpl extends AbstractDaoImpl<Organ, Long> implements OrganDao {
    protected OrganDaoImpl(Class<Organ> entityClass) {
        super(entityClass);
    }

    public OrganDaoImpl() {
        super(Organ.class);
    }

    @Override
    public void delete(Long id) {
        Organ organ = findOrganById(id);
        if (organ != null) {
            delete(organ);
        }
    }

    @Override
    public Organ findOrganById(Long id) {
        return (Organ) getCurrentSession().get(Organ.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Organ> getOrganByApplication(Application application) {
        Criteria criteria = getCurrentSession().createCriteria(Organ.class);
        criteria.add(Restrictions.eq("application", application));
        return criteria.list();
    }

    @Override
    public List<Organ> getNotIndexed() {
        Criteria criteria = getCurrentSession().createCriteria(Organ.class);
        criteria.add(Restrictions.isNull("searchId"));
        return criteria.list();
    }


}
