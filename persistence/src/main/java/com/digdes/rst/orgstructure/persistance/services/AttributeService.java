package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Attribute;
import com.digdes.rst.orgstructure.persistance.model.Person;

import java.util.List;

public interface AttributeService {

    void saveAttributes(List<Attribute>attributes, Person person);

    void saveAttribute(Attribute attribute, Person person);

    List<Attribute> getAttributeByPerson(Long id);

    Attribute prepareAttributePerson(Attribute attribute);

    List<Attribute> getAllAttributes();

}
