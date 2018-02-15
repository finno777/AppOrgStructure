package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentOrganization;

import java.util.List;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */
public interface GovernmentOrganizationDao extends AbstractDao<GovernmentOrganization,Long>  {
    List<GovernmentOrganization> getByGovernment(Government government);

    GovernmentOrganization getByGovernmentOrganization(GovernmentOrganization governmentOrganization);
}
