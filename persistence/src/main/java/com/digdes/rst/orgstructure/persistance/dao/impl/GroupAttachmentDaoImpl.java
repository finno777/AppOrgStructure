package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.GroupAttachmentDao;
import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.GroupAttachment;
import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class GroupAttachmentDaoImpl extends AbstractDaoImpl<GroupAttachment, Long> implements GroupAttachmentDao {
    protected GroupAttachmentDaoImpl(Class<GroupAttachment> entityClass) {
        super(entityClass);
    }
    public GroupAttachmentDaoImpl(){super(GroupAttachment.class);}

    @Override
    public List<GroupAttachment> getByGroup(GroupOrganizations groupOrganizations) {
        Criteria criteria = getCurrentSession().createCriteria(GroupAttachment.class);
        criteria.add(Restrictions.eq("groupOrganizations", groupOrganizations));
        return criteria.list();
    }

    @Override
    public GroupAttachment getByAttachmentAndGroup(GroupOrganizations groupOrganizations, Attachment attachment) {
        Criteria criteria = getCurrentSession().createCriteria(GroupAttachment.class);
        criteria.add(Restrictions.eq("groupOrganizations", groupOrganizations));
        criteria.add(Restrictions.eq("attachment", attachment));
        return (GroupAttachment)criteria.uniqueResult();
    }

    @Override
    public boolean isDuplicate(GroupAttachment groupAttachment) {
        Criteria criteria = getCurrentSession().createCriteria(GroupAttachment.class);
        criteria.add(Restrictions.eq("groupOrganizations", groupAttachment.getGroupOrganizations()));
        criteria.add(Restrictions.eq("attachment", groupAttachment.getAttachment()));
        return criteria.list().size()>0;
    }
}
