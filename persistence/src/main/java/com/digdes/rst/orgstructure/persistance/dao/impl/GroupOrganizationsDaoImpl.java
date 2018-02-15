package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GroupOrganizationsDao;
import com.digdes.rst.orgstructure.persistance.dao.OrganizationDao;
import com.digdes.rst.orgstructure.persistance.dao.SubGroupOrganizationsDao;
import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.TypeOrganization;
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
public class GroupOrganizationsDaoImpl extends AbstractDaoImpl<GroupOrganizations, Long> implements GroupOrganizationsDao {
    protected GroupOrganizationsDaoImpl(Class<GroupOrganizations> entityClass) {
        super(entityClass);
    }
    public GroupOrganizationsDaoImpl(){super(GroupOrganizations.class);}

    @Autowired
    SubGroupOrganizationsDao subGroupOrganizationsDao;

    @Override
    public List<GroupOrganizations> getByType(TypeOrganization typeOrganization) {
        Criteria criteria = getCurrentSession().createCriteria(GroupOrganizations.class);
        criteria.add(Restrictions.eq("typeOrganization", typeOrganization));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }

    @Override
    public void deleteByType(TypeOrganization byId) {

        List<GroupOrganizations> byType = getByType(byId);
        for (GroupOrganizations groupOrganizations :byType){
            subGroupOrganizationsDao.deleteByGroup(groupOrganizations);
            groupOrganizations.setDeleted(true);
            saveOrUpdate(groupOrganizations);
        }
    }

    @Override
    public List<GroupOrganizations> getAllByTypes(List<TypeOrganization> typeOrganizations) {
        Criteria criteria = getCurrentSession().createCriteria(GroupOrganizations.class);
        criteria.add(Restrictions.in("typeOrganization", typeOrganizations));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }
}
