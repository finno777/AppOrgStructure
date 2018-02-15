package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;

import java.util.List;

public interface GroupOrganizationsService {
    GroupOrganizations getFullGroupById(Long idGroup);

    List<GroupOrganizations> getSimilarGroupByTypeId(Long idType);

    void save(GroupOrganizations groupOrganizations);

    void deleteById(Long id);

    GroupOrganizations getById(Long id);

    List<GroupOrganizations> getGroupOrganizationGovernment();

}
