package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentGroup;
import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;

import java.util.List;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */
public interface GovernmentGroupService {
    List<GovernmentGroup> save(Government government);

    List<GroupOrganizations> getByGovernment(Long governmentID);
}
