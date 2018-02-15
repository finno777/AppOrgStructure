package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.AttachmentDao;
import com.digdes.rst.orgstructure.persistance.model.Attachment;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class AttachmentDaoImpl extends AbstractDaoImpl<Attachment, Long> implements AttachmentDao {
    protected AttachmentDaoImpl(Class<Attachment> entityClass) {
        super(entityClass);
    }
    public AttachmentDaoImpl(){super(Attachment.class);}

    @Override
    public List<Attachment> findAll() {
        return getCurrentSession().createCriteria(Attachment.class).list();
    }

    @Override
    public Attachment getByUuid(Long uuid) {
        Criteria criteria=getCurrentSession().createCriteria(Attachment.class);
        criteria.add(Restrictions.eq("uuid",uuid));
        List<Attachment> attachments = criteria.list();
        if(attachments.size()>0) {
            return attachments.get(0);
        }else {
            return null;
        }
    }
}
