package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.AttributeDao;
import com.digdes.rst.orgstructure.persistance.model.Attribute;
import com.digdes.rst.orgstructure.persistance.model.Person;
import com.digdes.rst.orgstructure.persistance.services.AttributeService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Log4j
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    AttributeDao attributeDao;

    @Autowired
    PersonService personService;


    @Override
    public void saveAttributes(List<Attribute> attributes, Person person) {
        try {
            log.debug("Check List for null");
            if (attributes != null && attributes.size()>0) {
                for (Attribute attribute : attributes) {
                    log.debug("Save Person Attributes");
                    saveAttribute(attribute, person);
                }
            }
        }
        catch (Exception e){
            log.error("***************ERROR***************");
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void saveAttribute(Attribute attribute, Person person) {
        attribute.setPerson(person);
        Attribute attributeBD = prepareAttributePerson(attribute);
        if(attributeBD!=null){
            attributeDao.saveOrUpdate(attributeBD);
        }else {
            attributeDao.saveOrUpdate(attribute);
        }
    }

    public Attribute prepareAttributePerson(Attribute attribute){
        List<Attribute> attributes=attributeDao.findAttributeByPersonAndKey(attribute.getPerson(), attribute.getKey());
        if( attributes.size()>0) {
            Attribute attributeDB = attributes.get(0);
            attributeDB.setValue(attribute.getValue());
            attribute = attributeDB;
            return attribute;
        }
        return null;
    }

    @Override
    public List<Attribute> getAllAttributes() {
        return attributeDao.findAllAttribute();
    }

    @Override
    public List<Attribute> getAttributeByPerson(Long id) {
        Person person=personService.getPerson(id);
        return attributeDao.findAttributeByPerson(person);
    }



}
