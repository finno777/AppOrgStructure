package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.DivisionService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Log4j
@Transactional
public class DivisionServiceImpl implements DivisionService {
    @Autowired
    DivisionDao divisionDao;

    @Autowired
    GovernmentDao governmentDao;

    @Autowired
    DivisionPersonDao divisionPersonDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    PersonService personService;

    @Override
    public Division getDivisionById(Long divisionID) {
        Division division = divisionDao.findById(divisionID);
        List<DivisionPerson> list = divisionPersonDao.getByDivision(division);
        for(DivisionPerson divisionPerson : list){
            List<Attachment> attachPerson = personService.getAttachPerson(divisionPerson.getPerson().getId());
            divisionPerson.getPerson().setAttachments(attachPerson);
        }
        division.setPersonList(list);
        return division;
    }

    @Override
    public List<Division> getDivisionsByGovernment(Long id) {
        Government byId = governmentDao.findById(id);
        return divisionDao.findByGovernment(byId);
    }

    @Override
    public void saveDivision(Division division) {
        divisionDao.saveOrUpdate(division);
    }

    @Override
    public void addDivisionPerson(DivisionPerson divisionPerson) {
        if(divisionPerson.getId()!=null){
            Long id = divisionPerson.getDivision().getId();
            log.debug(id);
            Division byId = divisionDao.findById(id);
            divisionPerson.setDivision(byId);
            personDao.save(divisionPerson.getPerson());
            divisionPersonDao.saveOrUpdate(divisionPerson);
        }else{
            DivisionPerson duplicate= divisionPersonDao.findDuplicate(divisionPerson);
            if(duplicate!=null && duplicate.getId()!=null){
                divisionPerson.setId(duplicate.getId());
            }
            divisionPersonDao.saveOrUpdate(divisionPerson);
        }
    }

    @Override
    public DivisionPerson removeDivisionPerson(Long idDivisionPerson) {
        DivisionPerson byId = divisionPersonDao.findById(idDivisionPerson);
        divisionPersonDao.delete(byId);
        return byId;
    }

    @Override
    public void deleteById(Long id) {
        Division byId = divisionDao.findById(id);
        byId.setDeleted(true);
        divisionDao.saveOrUpdate(byId);
    }

    @Override
    public void saveDivisionPerson(DivisionPerson divisionPerson) {
        Person person = divisionPerson.getPerson();
        personDao.save(person);
        Long id = divisionPerson.getDivision().getId();
        log.debug(id);
        Division byId = divisionDao.findById(id);
        divisionPerson.setDivision(byId);
        divisionPersonDao.saveOrUpdate(divisionPerson);
    }

    @Override
    public DivisionPerson getDivisionPerson(Long divisionPersonId) {
        DivisionPerson divisionPerson = divisionPersonDao.findById(divisionPersonId);
        List<Attachment> attachPerson = personService.getAttachPerson(divisionPerson.getPerson().getId());
        divisionPerson.getPerson().setAttachments(attachPerson);
        return divisionPerson;
    }
}
