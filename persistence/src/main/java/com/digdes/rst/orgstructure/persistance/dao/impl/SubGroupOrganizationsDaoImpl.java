package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.OrganizationDao;
import com.digdes.rst.orgstructure.persistance.dao.SubGroupOrganizationsDao;
import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class SubGroupOrganizationsDaoImpl extends AbstractDaoImpl<SubGroupOrganizations, Long> implements SubGroupOrganizationsDao {
    protected SubGroupOrganizationsDaoImpl(Class<SubGroupOrganizations> entityClass) {
        super(entityClass);
    }
    public SubGroupOrganizationsDaoImpl(){super(SubGroupOrganizations.class);}

    @Autowired
    OrganizationDao organizationDao;

    @Override
    public List<SubGroupOrganizations> getByGroup(GroupOrganizations groupOrganizations) {
        Criteria criteria = getCurrentSession().createCriteria(SubGroupOrganizations.class);
        criteria.add(Restrictions.eq("groupOrganizations", groupOrganizations));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }

    @Override
    public void deleteByGroup(GroupOrganizations groupOrganizations) {
        List<SubGroupOrganizations> byGroup = getByGroup(groupOrganizations);
        for(SubGroupOrganizations subGroupOrganizations : byGroup){
            deleteByEntity(subGroupOrganizations);
        }
    }

    @Override
    public List<SubGroupOrganizations> getByGroups(List<GroupOrganizations> groupOrganizations) {
        Criteria criteria = getCurrentSession().createCriteria(SubGroupOrganizations.class);
        criteria.add(Restrictions.in("groupOrganizations", groupOrganizations));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }

    @Override
    public List<SubGroupOrganizations> getAllActive() {
        Criteria criteria = getCurrentSession().createCriteria(SubGroupOrganizations.class);
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }

    @Override
    public void deleteByEntity(SubGroupOrganizations subGroupOrganizations) {
        organizationDao.deleteBySubGroup(subGroupOrganizations);
        subGroupOrganizations.setDeleted(true);
        saveOrUpdate(subGroupOrganizations);
    }
}
