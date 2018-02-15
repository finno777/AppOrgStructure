package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentSubGroup;
import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;

import java.util.List;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */
public interface GovernmentSubGroupService {
    List<GovernmentSubGroup> save(Government government);

    List<SubGroupOrganizations> getByGovernment(Long governmentID);
}
