package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;

import java.util.List;

public interface SubGroupOrganizationsDao extends AbstractDao<SubGroupOrganizations,Long> {
    List<SubGroupOrganizations> getByGroup(GroupOrganizations groupOrganizations);

    void deleteByGroup(GroupOrganizations groupOrganizations);

    List<SubGroupOrganizations> getByGroups(List<GroupOrganizations> groupOrganizations);

    List<SubGroupOrganizations> getAllActive();

    void deleteByEntity(SubGroupOrganizations subGroupOrganizations);
}
