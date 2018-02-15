package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.dto.PersonSearchDto;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.searchService.dto.EntityTransfer;
import com.digdes.rst.searchService.dto.IndexActionType;

import java.util.List;

public interface PersonService {

    void savePerson(Person person, String appId, String userId);

    void deletePerson(Long id,String appId, String userId);

    void savePersons(List<Person> people);

    List<Person> getPeopleByApp(Application application);

    List<Person> getPersonByRole(String role);

    List<Person> getPersonByOrgan(Long id);

    Person getPerson(Long id);

    Person findPerson(Long id);

    List<Person> search(String name);

    List<Person> getAdviser(String key, String value);

    List<Attachment> getAttachPerson(Long id);

    List<PersonAttachment> getAllPersonAttach();

    List<Person> filterByRequest(PersonSearchDto personSearchDto);

    void removeAttachmentFromPerson(Person person, Attachment attachment);

    List<Attachment> getAttachment(List<PersonAttachment> personAttachments);

    void saveAttachmentToPerson(Person person, Attachment attachment);

    boolean reindexMissing(String appId, String userId);

}
