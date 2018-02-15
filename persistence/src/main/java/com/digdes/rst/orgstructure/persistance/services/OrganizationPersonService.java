package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.OrganizationPerson;

public interface OrganizationPersonService {
    void savePerson(OrganizationPerson organizationPerson);

    OrganizationPerson getById(Long idOrganizationPerson);
}
