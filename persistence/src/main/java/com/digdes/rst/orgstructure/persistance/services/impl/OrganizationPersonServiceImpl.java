package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.OrganizationPersonDao;
import com.digdes.rst.orgstructure.persistance.dao.PersonDao;
import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.OrganizationPerson;
import com.digdes.rst.orgstructure.persistance.model.Person;
import com.digdes.rst.orgstructure.persistance.services.OrganizationPersonService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class OrganizationPersonServiceImpl implements OrganizationPersonService {
    @Autowired
    OrganizationPersonDao organizationPersonDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    PersonService personService;

    @Override
    public void savePerson(OrganizationPerson organizationPerson) {
        Person person = organizationPerson.getPerson();
        personDao.save(person);
        organizationPersonDao.saveOrUpdate(organizationPerson);
    }

    @Override
    public OrganizationPerson getById(Long idOrganizationPerson) {
        OrganizationPerson byId = organizationPersonDao.findById(idOrganizationPerson);
        List<Attachment> attachPerson = personService.getAttachPerson(byId.getPerson().getId());
        byId.getPerson().setAttachments(attachPerson);
        log.debug("attachPerson.size() = " + attachPerson.size());
        return byId;
    }
}
