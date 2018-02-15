package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.TypeOrganization;

import java.util.List;

public interface OrganizationDao extends AbstractDao<Organization,Long> {
    List<Organization> getBySubGroup(SubGroupOrganizations subGroupOrganizations);

    void deleteBySubGroup(SubGroupOrganizations subGroupOrganizations);

    List<Organization> getByType(TypeOrganization typeOrganization);

    void deleteByType(TypeOrganization byId);

    List<Organization> getAllByTypes(List<TypeOrganization> typeOrganizations);
}
