package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.Person;
import com.digdes.rst.orgstructure.persistance.model.PersonAttachment;

import java.util.List;

public interface PersonAttachmentDao extends AbstractDao<PersonAttachment,Long> {

    List<PersonAttachment> getByPerson(Person person);

    PersonAttachment getByAttachmentAndPerson(Person person, Attachment attachment);

    List<PersonAttachment> getAllPersonAttach();

    boolean isDuplicate(PersonAttachment personAttachment);
}
