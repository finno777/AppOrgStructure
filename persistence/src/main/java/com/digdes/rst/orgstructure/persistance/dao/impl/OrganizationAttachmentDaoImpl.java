package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.OrganizationAttachmentDao;
import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.OrganizationAttachment;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class OrganizationAttachmentDaoImpl extends AbstractDaoImpl<OrganizationAttachment, Long> implements OrganizationAttachmentDao {
    protected OrganizationAttachmentDaoImpl(Class<OrganizationAttachment> entityClass) {
        super(entityClass);
    }
    public OrganizationAttachmentDaoImpl(){super(OrganizationAttachment.class);}

    @Override
    public List<OrganizationAttachment> getByOrganization(Organization organization) {
        Criteria criteria = getCurrentSession().createCriteria(OrganizationAttachment.class);
        criteria.add(Restrictions.eq("organization", organization));
        return criteria.list();
    }

    @Override
    public OrganizationAttachment getByAttachmentAndOrganization(Organization organization, Attachment attachment) {
        Criteria criteria = getCurrentSession().createCriteria(OrganizationAttachment.class);
        criteria.add(Restrictions.eq("organization", organization));
        criteria.add(Restrictions.eq("attachment", attachment));
        return (OrganizationAttachment)criteria.uniqueResult();
    }
}
