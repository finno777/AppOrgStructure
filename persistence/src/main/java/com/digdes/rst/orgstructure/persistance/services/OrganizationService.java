package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.OrganizationPerson;

import java.util.List;

public interface OrganizationService {
    Organization getById(Long idOrganization);

    List<Organization> getSimilarOrganizationByID(Long idOrganization);

    void save(Organization organization);

    void deleteById(Long id);

    List<Organization> getOrganizationGovernment();

    OrganizationPerson removePerson(Long idOrganizationPerson);

    void savePerson(OrganizationPerson organizationPerson);
}
