package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Attribute;
import com.digdes.rst.orgstructure.persistance.model.Person;


import java.io.Serializable;
import java.util.List;

public interface AttributeDao extends AbstractDao<Attribute,Long> {

    Attribute findAttributeById(Long id);

    List<Attribute> findAttributeByPerson(Person person);

    List<Attribute> findAttributeByPersonAndKey (Person person, String key);

    void save(Attribute attribute);

    void deleteAttribute(Long id);

    List<Attribute> findAllAttribute();

    List<Attribute> findPeopleByAdviser(String id, String person);

}
