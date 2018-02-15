package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentOrganization;
import com.digdes.rst.orgstructure.persistance.model.Organization;

import java.util.List;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */
public interface GovernmentOrganizationService {
    List<GovernmentOrganization> save(Government government);

    List<Organization> getByGovernment(Long governmentID);
}
