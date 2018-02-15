package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.OrganizationPhoneDao;
import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.OrganizationPhone;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class OrganizationPhoneDaoImpl extends AbstractDaoImpl<OrganizationPhone, Long> implements OrganizationPhoneDao {
    protected OrganizationPhoneDaoImpl(Class<OrganizationPhone> entityClass) {
        super(entityClass);
    }
    public OrganizationPhoneDaoImpl(){super(OrganizationPhone.class);}

    @Override
    public List<OrganizationPhone> getByOrganization(Organization organization) {
        Criteria criteria = getCurrentSession().createCriteria(OrganizationPhone.class);
        criteria.add(Restrictions.eq("organization", organization));
        return criteria.list();
    }
}
