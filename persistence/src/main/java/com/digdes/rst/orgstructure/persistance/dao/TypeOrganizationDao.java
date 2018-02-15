package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;
import com.digdes.rst.orgstructure.persistance.model.TypeOrganization;

import java.util.List;

public interface TypeOrganizationDao extends AbstractDao<TypeOrganization,Long> {
    List<TypeOrganization> getByApp(SubdivisionApplication application);

    List<TypeOrganization> getByApps(List<SubdivisionApplication> subdivisionApplications);
}
