package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.dto.rest.request.PersonAddToOrganDto;
import com.digdes.rst.orgstructure.persistance.model.*;

import java.util.List;

public interface OrganService {

    void saveOrgan(Organ organ, String appId, String userId);

    void deleteOrgan(Long id, String appId, String userId);

    List<Organ> getOrgansByApp(Application application);

    void saveOrgans(List<Organ> organs);

    Organ findOrganById(Long id);

    List<Person> getPersonByOrgan(Organ organ);

    List<Attachment> getAttachOrgan(Long id);

    List<OrganAttachment> getAllOrganAttach();

    Person addPersonToOrgan(PersonAddToOrganDto dto, String appId, String userId);

    Person removePersonFromOrgan(Long organId, Long personId, String appId, String userId);

    boolean reindexMissing(String appId, String userId);
}
