package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.OrganizationPerson;
import com.digdes.rst.orgstructure.persistance.model.Person;

import java.util.List;

public interface OrganizationPersonDao extends AbstractDao<OrganizationPerson,Long> {
    List<OrganizationPerson> findByOrganization(Organization organization);

    List<OrganizationPerson> findByPerson(Person person);
}
