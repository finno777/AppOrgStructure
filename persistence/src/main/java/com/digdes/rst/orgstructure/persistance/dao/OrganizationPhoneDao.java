package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.OrganizationPhone;

import java.util.List;

public interface OrganizationPhoneDao extends AbstractDao<OrganizationPhone,Long> {
    List<OrganizationPhone> getByOrganization(Organization organization);
}
