package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.commons.portal.PortalUtils;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.dto.PersonSearchDto;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.*;
import com.digdes.rst.searchService.dto.EntityTransfer;
import com.digdes.rst.searchService.dto.IndexActionType;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Log4j
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    ApplicationDao applicationDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    PersonService personService;

    @Autowired
    OrganService organService;

    @Autowired
    AttributeService attributeService;

    @Autowired
    GovernmentService governmentService;

    @Autowired
    AttributeDao attributeDao;

    @Autowired
    CuratorPersonDao curatorPersonDao;

    @Autowired
    PersonAttachmentDao personAttachmentDao;

    @Autowired
    AttachmentDao attachmentDao;

    @Autowired
    SearchService searchService;

    @Override
    public void savePerson(Person person, String appId, String userId) {
        try {
            log.debug("Find App by appId");
            Application application = applicationDao.findByAppId(appId);
            if (!org.apache.commons.lang3.StringUtils.isEmpty(person.getName())) {
                person.setApplication(application);
                person.setCreationDate(new Date());
                person.setLastUpdate(person.getLastUpdate() == null ? new Date() : person.getLastUpdate());
                person.setSitePosition(getPeopleByApp(application).size() + 1);
                log.debug("Person site position " + person.getSitePosition());
                log.debug("Save person");
                personDao.save(person);
                log.debug("Save person " + person.getId() + " attribute");
                attributeService.saveAttributes(person.getAttributes(), person);
                if (person.getCuratorGovernments() != null) {
                    for (CuratorPerson curatorPerson : person.getCuratorGovernments()) {
                        curatorPerson.setPerson(person);
                        governmentService.addCuratorToGovernment(curatorPerson);
                    }
                }
            }
            log.debug(person.getId());
            person.setSearchId(searchService.index(person, appId, userId));
        } catch (Exception e) {
            log.error("***************Don't SAVE Person***************");
            e.getMessage();
        }
    }

    @Override
    public void deletePerson(Long id, String appId, String userId) {
        log.debug("Take personId and DELETE");
        if (searchService.unindexPerson(appId, userId, id)) {
            personDao.deletePerson(id);
        }
    }

    // это порядок отображения?
    //разобраться - удалить - убрать это в бд
    @Override
    public void savePersons(List<Person> people) {
        try {
            log.debug("Take cycle for people");
            for (Person person : people) {
                Person persoById = personDao.findPersonById(person.getId());
                persoById.setId(person.getId());
                personDao.save(persoById);
            }
        } catch (Exception e) {
            log.error("***************ERROR***************");
            log.error("Can't init People");
        }
    }

    @Override
    public List<Person> getPeopleByApp(Application application) {
        try {
            log.debug("Find all Person by application");
            List<Person> people = personDao.findPeopleByApplication(application);

            for (Person person : people) {
                Long id = person.getId();
                person.setAttachments(personService.getAttachPerson(id));
                person.setCuratorGovernments(curatorPersonDao.findByIdPerson(id));
            }

            return people;
        } catch (Exception e) {
            log.error("***************ERROR***************");
            log.error("Can't return people by application");
            List<Person> people = new ArrayList<>();
            log.debug("Work with empty set");
            return people;
        }
    }


    @Override
    public List<Person> getPersonByRole(String role) {
        log.debug("Get people by role");
        List<Person> people = personDao.findPersonByRole(Person.Role.valueOf(role));
        for (Person person : people) {
            Long id = person.getId();
            person.setAttachments(personService.getAttachPerson(id));
            person.setCuratorGovernments(curatorPersonDao.findByIdPerson(id));
        }
        people.sort(Comparator.comparing(Person::getSitePosition));
        return people;
    }

    @Override
    public List<Person> getPersonByOrgan(Long id) {
        log.debug("Find Organ by id");
        Organ organ = organService.findOrganById(id);
        log.debug("Take people belonging to Organ");
        List<Person> people = organService.getPersonByOrgan(organ);
        for (Person person : people) {
            Long personId = person.getId();
            person.setAttachments(personService.getAttachPerson(personId));
            person.setCuratorGovernments(curatorPersonDao.findByIdPerson(personId));
        }
        return people;
    }

    @Override
    public Person getPerson(Long id) {
        log.debug("Find Person by Id;");
        return personDao.findPersonById(id);
    }

    @Override
    public Person findPerson(Long id) {
        try {
            log.debug("Get Person by Id");
            Person person = getPerson(id);
            //log.debug("Get Person by Application");
            //personDao.findPersonById(id);
            return person;
        } catch (Exception e) {
            log.error("***************ERROR***************");
            return null;
        }
    }

    @Override
    public List<Person> search(String name) {
        log.debug("Search person by name");
        List<Person> people = personDao.searchPersonByName(name);
        return people;
    }

    @Override
    public List<Person> getAdviser(String key, String value) {
        List<Person> people = new ArrayList<>();
        for (Attribute attribute : attributeDao.findPeopleByAdviser(value, key)) {
            Person person = attribute.getPerson();
            people.add(person);
        }
        return people;
    }

    @Override
    public List<Attachment> getAttachPerson(Long id) {
        List<Attachment> attachments = new ArrayList<>();
        Person person = findPerson(id);
        List<PersonAttachment> list = personAttachmentDao.getByPerson(person);
        if (list != null) {
            for (PersonAttachment personAttachment : list) {
                attachments.add(personAttachment.getAttachment());
            }
        }
        return attachments;
    }

    @Override
    public List<PersonAttachment> getAllPersonAttach() {
        return personAttachmentDao.getAllPersonAttach();
    }

    @Override
    public List<Person> filterByRequest(PersonSearchDto personSearchDto) {
        List<Person> people = personDao.filterByRequest(personSearchDto);
        for (Person person : people) {
            List<Attachment> attachPerson = getAttachPerson(person.getId());
            person.setAttachments(attachPerson);
        }

        return people;
    }

    @Override
    public void removeAttachmentFromPerson(Person person, Attachment attachment) {
        PersonAttachment personAttachment = personAttachmentDao.getByAttachmentAndPerson(person, attachment);
        if (personAttachment != null && personAttachment.getId() != null)
            personAttachmentDao.delete(personAttachment);
    }

    @Override
    public List<Attachment> getAttachment(List<PersonAttachment> personAttachments) {
        List<Attachment> attachments = new ArrayList<>();
        if (personAttachments != null && !personAttachments.isEmpty())
            for (PersonAttachment personAttachment : personAttachments) {
                attachments.add(personAttachment.getAttachment());
            }
        return attachments;
    }

    @Override
    public void saveAttachmentToPerson(Person person, Attachment attachment) {
        Attachment attachmentDB = attachmentDao.getByUuid(attachment.getUuid());
        if (attachmentDB == null) {
            attachmentDao.saveOrUpdate(attachment);
        } else {
            attachment = attachmentDB;
        }
        PersonAttachment personAttachment = new PersonAttachment();
        personAttachment.setAttachment(attachment);
        personAttachment.setPerson(person);
        if (!personAttachmentDao.isDuplicate(personAttachment))
            personAttachmentDao.saveOrUpdate(personAttachment);
    }

    @Override
    public boolean reindexMissing(String appId, String userId) {
        try {
            personDao.getNotIndexed().forEach(person -> {
                searchService.index(person, appId, userId);
            });
            return true;
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return false;
        }
    }


}
