package com.digdes.rst.orgstructure.persistance.dao.impl;
import com.digdes.rst.orgstructure.persistance.dao.OrganAttachmentDao;
import com.digdes.rst.orgstructure.persistance.model.*;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
@Transactional
@Log4j
public class OrganAttachmentDaoImpl extends AbstractDaoImpl<OrganAttachment, Long> implements OrganAttachmentDao {
    protected OrganAttachmentDaoImpl(Class<OrganAttachment> entityClass) {
        super(entityClass);
    }
    public OrganAttachmentDaoImpl(){super(OrganAttachment.class);}


    @Override
    public List<OrganAttachment> getByOrgan(Organ organ) {
        Criteria criteria = getCurrentSession().createCriteria(OrganAttachment.class);
        criteria.add(Restrictions.eq("organ", organ));
        return criteria.list();
    }

    @Override
    public OrganAttachment getByAttachmentAndOrgan(Organ organ, Attachment attachment) {
        Criteria criteria = getCurrentSession().createCriteria(OrganAttachment.class);
        criteria.add(Restrictions.eq("organ", organ));
        criteria.add(Restrictions.eq("attachment", attachment));
        return (OrganAttachment)criteria.uniqueResult();
    }

    @Override
    public List<OrganAttachment> getAllOrganAttach() {
        return getCurrentSession().createCriteria(OrganAttachment.class).list();
    }

    @Override
    public boolean isDuplicate(OrganAttachment organAttachment) {
        Criteria criteria = getCurrentSession().createCriteria(OrganAttachment.class);
        criteria.add(Restrictions.eq("organ", organAttachment.getOrgan()));
        criteria.add(Restrictions.eq("attachment", organAttachment.getAttachment()));
        return criteria.list().size()>0;
    }
}
