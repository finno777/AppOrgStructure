package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.OrganizationPersonDao;
import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.OrganizationPerson;
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
public class OrganizationPersonDaoImpl extends AbstractDaoImpl<OrganizationPerson, Long> implements OrganizationPersonDao {
    protected OrganizationPersonDaoImpl(Class<OrganizationPerson> entityClass) {
        super(entityClass);
    }
    public OrganizationPersonDaoImpl(){super(OrganizationPerson.class);}

    @Override
    public List<OrganizationPerson> findByOrganization(Organization organization) {
        Criteria criteria = getCurrentSession().createCriteria(OrganizationPerson.class);
        criteria.add(Restrictions.eq("organization", organization));
        return criteria.list();
    }

    @Override
    public List<OrganizationPerson> findByPerson(Person person) {
        Criteria criteria = getCurrentSession().createCriteria(OrganizationPerson.class);
        criteria.add(Restrictions.eq("person", person));
        return criteria.list();
    }
}
