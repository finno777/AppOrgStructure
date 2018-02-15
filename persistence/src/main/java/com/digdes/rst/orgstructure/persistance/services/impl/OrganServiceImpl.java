package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.dto.rest.request.PersonAddToOrganDto;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.OrganService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import com.digdes.rst.orgstructure.persistance.services.SearchService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j
public class OrganServiceImpl implements OrganService {
    @Autowired
    OrganDao organDao;

    @Autowired
    ApplicationDao applicationDao;

    @Autowired
    PersonService personService;

    @Autowired
    PersonDao personDao;

    @Autowired
    AttachmentDao attachmentDao;

    @Autowired
    OrganAttachmentDao organAttachmentDao;

    @Autowired
    SearchService searchService;

    @Override
    public void saveOrgan(Organ organ, String appId, String userId) {
        Boolean newOrgan = organ.getId() == null;
        Application application = applicationDao.findByAppId(appId);
        log.debug("***************take appId***************" + application);
        if (application != null) {
            log.debug("Check Organ.Name Empty");
            if (!org.apache.commons.lang3.StringUtils.isEmpty(organ.getName())) {
                organ.setApplication(application);
                organ.setSitePosition(getOrgansByApp(application).size() + 1);
                log.debug("organ clear : " + organ.toString());
                if (organ.getId() != null) {
                    Organ organDB = organDao.findById(organ.getId());
                    organDB.mapOrgan(organ);
                    organ = organDB;
                }
                organ.setLastUpdate(new Date());
                organDao.saveOrUpdate(organ);


                log.debug("organ : " + organ.toString());
                log.debug("***************save***************");
                List<Attachment> attachments = organ.getAttachments();
                List<OrganAttachment> organAttachments = organAttachmentDao.getByOrgan(organ);
                List<Attachment> attachmentsDB = getAttachment(organAttachments);
                if (newOrgan || attachmentsDB.isEmpty()) {
                    log.debug("1 " + (newOrgan || !attachmentsDB.isEmpty()));
                    log.debug(newOrgan);
                    log.debug(attachmentsDB.isEmpty());
                    for (Attachment attachment : attachments) {
                        saveAttachmentToOrgan(organ, attachment);
                    }
                } else {
                    for (int i = 0; i < attachments.size(); i++) {
                        if (attachments.get(i).getId() == null) {
                            saveAttachmentToOrgan(organ, attachments.get(i));
                            log.debug(attachments.get(i).getId());
                        }
                    }
                    List<Long> newIds = attachments.stream().map(Attachment::getId).collect(Collectors.toList());
                    newIds.forEach(i -> {
                        log.debug(i);
                    });
                    for (Attachment attachment : attachmentsDB) {
                        log.debug("not contain " + attachment.getId());
                        if (!newIds.contains(attachment.getId()))
                            removeAttachmentFromOrgan(organ, attachment);
                    }
                }
            }
        }
        searchService.index(organ, appId, userId);
    }

    @Override
    public void deleteOrgan(Long id, String appId, String userId) {
        log.debug("Check Organ.Id is null");
        if (organDao.findOrganById(id) != null) {
            log.debug("Find Person by Organ");
            if (findOrganById(id).getPeople() != null) {
                for (Person person : findOrganById(id).getPeople()) {
                    log.debug("Set Person.Organ null");
                    person.setOrgans(null);
                }
            }
            log.debug("***************DELETE Organ***************");
            if (searchService.unindexOrgan(appId, userId, id)) {
                organDao.delete(id);
            }
        }
    }

    @Override
    public List<Organ> getOrgansByApp(Application application) {
        List<Organ> organs = organDao.getOrganByApplication(application);
        for (Organ organ : organs) {
            organ.setAttachments(getAttachOrgan(organ.getId()));
        }
        organs.sort(Comparator.comparing(Organ::getSitePosition));
        return organs;
    }

    @Override
    public void saveOrgans(List<Organ> organs) {
        log.debug("Find Organs size");
        for (Organ organ : organs) {
            Organ organById = findOrganById(organ.getId());
            log.debug("Set Organ.SitePosition");
            organById.setSitePosition(organ.getSitePosition());
            log.debug("***************SAVE Organ***************");
            organDao.saveOrUpdate(organById);
        }
    }


    @Override
    public Organ findOrganById(Long id) {
        Organ organById = organDao.findOrganById(id);
        List<Person> peopleByOrgan = personDao.findPeopleByOrgan(organById);
        organById.setPeople(peopleByOrgan);
        return organById;
    }

    @Override
    public List<Person> getPersonByOrgan(Organ organ) {
        log.debug("Find People By Organ");
        List<Person> people = personDao.findPeopleByOrgan(organ);
        return people;
    }

    @Override
    public List<Attachment> getAttachOrgan(Long id) {
        List<Attachment> attachments = new ArrayList<>();
        Organ organ = findOrganById(id);
        List<OrganAttachment> list = organAttachmentDao.getByOrgan(organ);
        if (list != null) {
            for (OrganAttachment organAttachment : list) {
                attachments.add(organAttachment.getAttachment());
            }
        }
        return attachments;
    }

    @Override
    public List<OrganAttachment> getAllOrganAttach() {
        return organAttachmentDao.getAllOrganAttach();
    }


    @Override
    public Person addPersonToOrgan(PersonAddToOrganDto dto, String appId, String userId) {
        Person person = null;
        Application application = applicationDao.findByAppId(appId);
        Organ organ = organDao.findOrganById(dto.getOrganId());
        if (organ == null || application == null) throw new EntityNotFoundException();
        if (dto.getPersonId() != null) {
            person = personDao.findPersonById(dto.getPersonId());
            person.getOrgans().add(organ);
            //  organ.getPeople().add(person);
        } else {
            person = new Person();
            person.setEmail(dto.getEmail());
            person.setName(dto.getName());
            person.setPhone(dto.getPhone());
            person.setPosition(dto.getPosition());
            person.setRole(Person.Role.MEMBER);

            //?, this sitePosition stuff should be refactored and moved to autogen in database
            person.setSitePosition(personService.getPeopleByApp(application).size() + 1);
            //?

            person.setApplication(application);
            person.getOrgans().add(organ);
            // organ.getPeople().add(person);
            person = personDao.saveOrUpdate(person);
            person.setSearchId(searchService.index(person, appId, userId));
            organ.setSearchId(searchService.index(organ, appId, userId));
        }
           /* person.getOrgans().add(organ);
            organ.getPeople().add(person);*/

        return person;
    }

    @Override
    public Person removePersonFromOrgan(Long organId, Long personId, String appId, String userId) {
        Person person = personDao.findPersonById(personId);
        person.getOrgans().removeIf(organ -> organ.getId().equals(organId));
        if (searchService.unindexPerson(appId, userId, personId)) {
            personDao.deletePerson(personId);
        }
        return person;
    }

    @Override
    public boolean reindexMissing(String appId, String userId) {
        try {
            organDao.getNotIndexed().forEach(organ -> {
                searchService.index(organ, appId, userId);
            });
            return true;
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return false;
        }
    }


    private void saveAttachmentToOrgan(Organ organ, Attachment attachment) {
        Attachment attachmentDB = attachmentDao.getByUuid(attachment.getUuid());
        if (attachmentDB == null) {
            attachmentDao.saveOrUpdate(attachment);
        } else {
         //   log.debug(attachmentDB);
          //  attachment = attachmentDB;
          //  log.debug(attachment.getId());
            attachment.setId(attachmentDB.getId());
        }
        OrganAttachment organAttachment = new OrganAttachment();
        organAttachment.setAttachment(attachment);
        organAttachment.setOrgan(organ);
        if (!organAttachmentDao.isDuplicate(organAttachment)) {
            log.debug("not duplicate");
            organAttachmentDao.saveOrUpdate(organAttachment);
        }
        log.debug("at the end " +attachment);
    }

    private void removeAttachmentFromOrgan(Organ organ, Attachment attachment) {
        OrganAttachment organAttachment = organAttachmentDao.getByAttachmentAndOrgan(organ, attachment);
        if (organAttachment != null)
            organAttachmentDao.delete(organAttachment);
    }

    private List<Attachment> getAttachment(List<OrganAttachment> organAttachments) {
        List<Attachment> attachments = new ArrayList<>();
        if (organAttachments != null && !organAttachments.isEmpty())
            for (OrganAttachment organAttachment : organAttachments) {
                attachments.add(organAttachment.getAttachment());
            }
        return attachments;
    }

}

