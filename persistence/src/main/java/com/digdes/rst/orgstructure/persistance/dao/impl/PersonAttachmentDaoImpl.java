package com.digdes.rst.orgstructure.persistance.dao.impl;
import com.digdes.rst.orgstructure.persistance.dao.PersonAttachmentDao;
import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.Person;
import com.digdes.rst.orgstructure.persistance.model.PersonAttachment;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
@Transactional
@Log4j
public class PersonAttachmentDaoImpl extends AbstractDaoImpl<PersonAttachment, Long> implements PersonAttachmentDao {
    protected PersonAttachmentDaoImpl(Class<PersonAttachment> entityClass) {
        super(entityClass);
    }
    public PersonAttachmentDaoImpl(){super(PersonAttachment.class);}


    @Override
    public List<PersonAttachment> getByPerson(Person person) {
        Criteria criteria = getCurrentSession().createCriteria(PersonAttachment.class);
        criteria.add(Restrictions.eq("person", person));
        return criteria.list();
    }

    @Override
    public PersonAttachment getByAttachmentAndPerson(Person person, Attachment attachment) {
        Criteria criteria = getCurrentSession().createCriteria(PersonAttachment.class);
        criteria.add(Restrictions.eq("person", person));
        criteria.add(Restrictions.eq("attachment", attachment));
        return (PersonAttachment) criteria.uniqueResult();
    }

    @Override
    public List<PersonAttachment> getAllPersonAttach() {
        return getCurrentSession().createCriteria(PersonAttachment.class).list();
    }

    @Override
    public boolean isDuplicate(PersonAttachment personAttachment) {
        Criteria criteria = getCurrentSession().createCriteria(PersonAttachment.class);
        criteria.add(Restrictions.eq("person", personAttachment.getPerson()));
        criteria.add(Restrictions.eq("attachment", personAttachment.getAttachment()));
        return criteria.list().size()>0;
    }
}
