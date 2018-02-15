package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.dto.PersonSearchDto;
import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.Person;
import java.util.List;


public interface PersonDao extends AbstractDao<Person,Long> {

    void save(Person person);

    Person findPersonById(Long id);

    void deletePerson(Long id);

    List<Person> findPeopleByApplication(Application application);

    List<Person> findPersonByRole(Person.Role role);

    List<Person> searchPersonByName(String name);

    List<Person> findPeopleByOrgan(Organ organ);

    List<Person> getNotIndexed();

    List<Person> filterByRequest(PersonSearchDto personSearchDto);
}
