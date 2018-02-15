package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.OrganizationDao;
import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.TypeOrganization;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class OrganizationDaoImpl extends AbstractDaoImpl<Organization, Long> implements OrganizationDao {
    protected OrganizationDaoImpl(Class<Organization> entityClass) {
        super(entityClass);
    }
    public OrganizationDaoImpl(){super(Organization.class);}

    @Override
    public List<Organization> getBySubGroup(SubGroupOrganizations subGroupOrganizations) {
        Criteria criteria = getCurrentSession().createCriteria(Organization.class);
        criteria.add(Restrictions.eq("subGroupOrganizations", subGroupOrganizations));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }

    @Override
    public void deleteBySubGroup(SubGroupOrganizations subGroupOrganizations) {
        List<Organization> bySubGroup = getBySubGroup(subGroupOrganizations);
        for(Organization organization: bySubGroup){
            organization.setDeleted(true);
            saveOrUpdate(organization);
        }
    }

    @Override
    public List<Organization> getByType(TypeOrganization typeOrganization) {
        Criteria criteria = getCurrentSession().createCriteria(Organization.class);
        criteria.add(Restrictions.eq("typeOrganization", typeOrganization));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }

    @Override
    public void deleteByType(TypeOrganization byId) {
        Criteria criteria = getCurrentSession().createCriteria(Organization.class);
        criteria.add(Restrictions.eq("typeOrganization", byId));
        criteria.add(Restrictions.eq("deleted",false));
        List<Organization> organizations = criteria.list();
        for(Organization organization: organizations){
            organization.setDeleted(true);
            saveOrUpdate(organization);
        }
    }

    @Override
    public List<Organization> getAllByTypes(List<TypeOrganization> typeOrganizations) {
        Criteria criteria = getCurrentSession().createCriteria(Organization.class);
        criteria.add(Restrictions.in("typeOrganization", typeOrganizations));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }
}
