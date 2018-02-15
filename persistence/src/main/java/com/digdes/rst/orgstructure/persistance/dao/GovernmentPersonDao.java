package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.GovernmentPerson;
import com.digdes.rst.orgstructure.persistance.model.Person;

import java.util.List;

public interface GovernmentPersonDao extends AbstractDao<GovernmentPerson,Long> {
    List<GovernmentPerson> findByPerson(Person person);
}
