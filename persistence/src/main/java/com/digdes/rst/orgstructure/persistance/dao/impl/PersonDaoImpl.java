package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.AttachmentDao;
import com.digdes.rst.orgstructure.persistance.dao.PersonAttachmentDao;
import com.digdes.rst.orgstructure.persistance.dao.PersonDao;
import com.digdes.rst.orgstructure.persistance.dto.PersonSearchDto;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@Log4j
public class PersonDaoImpl extends AbstractDaoImpl<Person, Long> implements PersonDao {
    protected PersonDaoImpl(Class<Person> entityClass) {
        super(entityClass);
    }

    public PersonDaoImpl() {
        super(Person.class);
    }

    @Autowired
    AttachmentDao attachmentDao;

    @Autowired
    PersonAttachmentDao personAttachmentDao;

    @Autowired
    PersonService personService;

    @Override
    public void save(Person person) {
        Boolean newPerson = person.getId() == null;
        if (!newPerson) {
            Person personDB = findById(person.getId());
            personDB.mapPerson(person);
            getCurrentSession().saveOrUpdate(personDB);
            person = personDB;
        } else {
            person.setCreationDate(person.getCreationDate() == null ? new Date() : person.getCreationDate());
            getCurrentSession().saveOrUpdate(person);
        }

        List<Attachment> attachments = person.getAttachments();
        List<PersonAttachment> personAttachments = personAttachmentDao.getByPerson(person);
        List<Attachment> attachmentsDB = personService.getAttachment(personAttachments);
        log.debug("Save person Attachments");
        if (newPerson || !attachmentsDB.isEmpty()) {
            for (Attachment attachment : attachments) {
                personService.saveAttachmentToPerson(person, attachment);
            }
        } else {
            for (Attachment attachment : attachments) {
                if (attachment.getId() == null) {
                    personService.saveAttachmentToPerson(person, attachment);
                }
            }
            List<Long> newIds = attachments.stream().map(Attachment::getId).collect(Collectors.toList());
            log.debug("Remove person Attachments");
            for (Attachment attachment : attachmentsDB) {
                if (!newIds.contains(attachment.getId()))
                    personService.removeAttachmentFromPerson(person, attachment);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Person findPersonById(Long id) {
        return (Person) getCurrentSession().get(Person.class, id);
    }

    @Override
    public void deletePerson(Long id) {
        Person person = findPersonById(id);
        if (person != null) {
            delete(person);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> findPeopleByApplication(Application application) {
        Criteria criteria = getCurrentSession().createCriteria(Person.class);
        criteria.add(Restrictions.eq("application", application));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> findPersonByRole(Person.Role role) {
        Criteria criteria = getCurrentSession().createCriteria(Person.class);
        criteria.add(Restrictions.eq("role", role));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> searchPersonByName(String name) {
        Criteria criteria = getCurrentSession().createCriteria(Person.class);
        if (name != null && !name.isEmpty()) {
            criteria.add(Restrictions.eq("name", name));
        }

        if (name != null && !name.trim().isEmpty()) {
            String text = name;
            if (text.contains("\\")) {
                text = text.replace("\\", "\\\\");
            }
            if (text.contains("%")) {
                text = text.replace("%", "\\%");
            }
            if (text.contains("_")) {
                text = text.replace("_", "\\_");
            }
            criteria.add(Restrictions.or(Restrictions.ilike("name", text, MatchMode.ANYWHERE)));
        }
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> findPeopleByOrgan(Organ organ) {
        Criteria criteria = getCurrentSession().createCriteria(Person.class)
                .createAlias("organs", "organsAlias")
                .add(Restrictions.eq("organsAlias.id", organ.getId()));
        return criteria.list();
    }

    @Override
    public List<Person> getNotIndexed() {
        Criteria criteria = getCurrentSession().createCriteria(Person.class);
        criteria.add(Restrictions.isNull("searchId"));
        return criteria.list();
    }

    @Override
    public List<Person> filterByRequest(PersonSearchDto personSearchDto) {
        Criteria criteria = getCurrentSession().createCriteria(Person.class);
        List<Person.Role> roles = personSearchDto.getRoles();
        if (roles != null) {
            if (roles.isEmpty()) {
                criteria.add(Restrictions.isNull("role"));
            } else {
                criteria.add(Restrictions.in("role", roles));
            }
        }
        String fio = personSearchDto.getFio();
        if (fio != null && !fio.isEmpty()) {
            criteria.add(Restrictions.ilike("name", "%" + fio + "%"));
        }
        return criteria.list();
    }


}
