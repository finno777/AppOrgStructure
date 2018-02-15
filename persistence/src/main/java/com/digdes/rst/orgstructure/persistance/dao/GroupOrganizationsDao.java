package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.TypeOrganization;

import java.util.List;

public interface GroupOrganizationsDao extends AbstractDao<GroupOrganizations,Long> {
    List<GroupOrganizations> getByType(TypeOrganization typeOrganization);

    void deleteByType(TypeOrganization byId);

    List<GroupOrganizations> getAllByTypes(List<TypeOrganization> typeOrganizations);
}
