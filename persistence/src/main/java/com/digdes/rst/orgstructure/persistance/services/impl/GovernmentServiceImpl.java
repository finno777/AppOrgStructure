package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Log4j
@Transactional
public class GovernmentServiceImpl implements GovernmentService {
    @Autowired
    GovernmentApplicationDao governmentApplicationDao;

    @Autowired
    GovernmentDao governmentDao;

    @Autowired
    GovernmentPersonDao governmentPersonDao;

    @Autowired
    CuratorPersonDao curatorPersonDao;

    @Autowired
    DivisionService divisionService;

    @Autowired
    GovernmentGroupService governmentGroupService;

    @Autowired
    GovernmentSubGroupService governmentSubGroupService;

    @Autowired
    GovernmentOrganizationService governmentOrganizationService;

    @Autowired
    PersonDao personDao;

    @Autowired
    PersonService personService;



    @Override
    public List<Government> getAllGovernmentByApp(GovernmentApplication application) {
        return governmentDao.findByApp(application);
    }

    @Override
    public Government getGovernmentById(Long governmentID) {
        if(governmentID!=null){
            Government government = governmentDao.findById(governmentID);
            GovernmentPerson person = government.getPerson();
            if(person!=null && person.getPerson()!=null){
                Person personPerson = person.getPerson();
                List<Attachment> attachPerson = personService.getAttachPerson(personPerson.getId());
                personPerson.setAttachments(attachPerson);
            }
            government.setCurators(curatorPersonDao.findByGovernment(government));
            government.setDivisions(divisionService.getDivisionsByGovernment(governmentID));
            government.setGroupOrganizations(governmentGroupService.getByGovernment(governmentID));
            government.setOrganizations(governmentOrganizationService.getByGovernment(governmentID));
            government.setSubGroupOrganizations(governmentSubGroupService.getByGovernment(governmentID));
            return government;
        }else{
            return null;
        }

    }

    @Override
    public void saveGovernment(Government government, String appId) {
        GovernmentApplication application=governmentApplicationDao.getAppByAppID(appId);
        log.debug("***************take appId***************" + application);
        if(application!=null){
            log.debug("Check Government.Title Empty");
            if (!org.apache.commons.lang3.StringUtils.isEmpty(government.getTitle())) {
                government.setApplication(application);
                governmentDao.saveOrUpdate(government);
                log.debug("***************save***************");
                governmentGroupService.save(government);
                governmentOrganizationService.save(government);
                governmentSubGroupService.save(government);
                List<CuratorPerson> curators = government.getCurators();
                for(CuratorPerson curatorPerson :curators){
                    curatorPerson.setGovernment(government);
                    addCuratorToGovernment(curatorPerson);
                }
            }
        }
    }

    @Override
    public void addCuratorToGovernment(CuratorPerson curatorPerson) {
        if(curatorPerson.getId()!=null){
            log.debug("Прямое сохранение связи");
            curatorPersonDao.saveOrUpdate(curatorPerson);
        }else{
            CuratorPerson duplicate= curatorPersonDao.findDuplicate(curatorPerson);
            if(duplicate!=null && duplicate.getId()!=null){
                log.debug("Найден дубль");
                curatorPerson.setId(duplicate.getId());
            }
            log.debug("Сохранение связи");
            curatorPersonDao.saveOrUpdate(curatorPerson);
        }
    }

    @Override
    public CuratorPerson removeCuratorFromGovernment(Long idCurator) {
        CuratorPerson byId = curatorPersonDao.findById(idCurator);
        curatorPersonDao.delete(byId);
        return byId;
    }

    @Override
    public List<Government> getGovernmentByPerson(Long personId) {
        Person person = new Person();
        person.setId(personId);
        List<GovernmentPerson> governmentPeople = governmentPersonDao.findByPerson(person);
        List<Government> governments = new ArrayList<Government>();
        if(governmentPeople!=null && governmentPeople.size()>0){
            for(GovernmentPerson governmentPerson: governmentPeople){
                governments.add(governmentPerson.getGovernment());
            }
        }
        return governments;
    }

    @Override
    public List<CuratorPerson> getGovernmentByCuratorPerson(Long l) {
        List<CuratorPerson> curatorPersons = curatorPersonDao.findByIdPerson(l);
        return curatorPersons;
    }

    @Override
    public List<Government> getAllGovernments() {
        return governmentDao.getAllActive();
    }

    @Override
    public void deleteById(Long id) {
        Government byId = governmentDao.findById(id);
        List<Division> divisionsByGovernment = divisionService.getDivisionsByGovernment(id);
        for (Division division: divisionsByGovernment){
            divisionService.deleteById(division.getId());
        }
        byId.setDeleted(true);
        governmentDao.saveOrUpdate(byId);
    }

    @Override
    public void saveGovernmentPerson(GovernmentPerson governmentPerson) {
        Person person = governmentPerson.getPerson();
        log.debug(person.getName());
        log.debug(person.getId());
        personDao.save(person);
        governmentPersonDao.saveOrUpdate(governmentPerson);
        Government government = governmentDao.findById(governmentPerson.getGovernment().getId());
        government.setPerson(governmentPerson);
        governmentDao.saveOrUpdate(government);
    }

    @Override
    public GovernmentPerson getGovernmentPersonByID(Long personID) {
        GovernmentPerson byId = governmentPersonDao.findById(personID);
        if(byId!=null && byId.getPerson()!=null){
            Person personPerson = byId.getPerson();
            List<Attachment> attachPerson = personService.getAttachPerson(personPerson.getId());
            personPerson.setAttachments(attachPerson);
        }
        return byId;
    }
}
