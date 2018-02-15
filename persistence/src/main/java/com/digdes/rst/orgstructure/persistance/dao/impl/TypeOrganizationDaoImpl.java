package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.TypeOrganizationDao;
import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;
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
public class TypeOrganizationDaoImpl extends AbstractDaoImpl<TypeOrganization, Long> implements TypeOrganizationDao {
    protected TypeOrganizationDaoImpl(Class<TypeOrganization> entityClass) {
        super(entityClass);
    }
    public TypeOrganizationDaoImpl(){super(TypeOrganization.class);}

    @Override
    public List<TypeOrganization> getByApp(SubdivisionApplication application) {
        Criteria criteria = getCurrentSession().createCriteria(TypeOrganization.class);
        criteria.add(Restrictions.eq("application", application));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }

    @Override
    public List<TypeOrganization> getByApps(List<SubdivisionApplication> subdivisionApplications) {
        Criteria criteria = getCurrentSession().createCriteria(TypeOrganization.class);
        criteria.add(Restrictions.in("application", subdivisionApplications));
        criteria.add(Restrictions.eq("deleted",false));
        return criteria.list();
    }
}
