package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;

import java.util.List;

public interface SubdivisionApplicationDao extends AbstractDao<SubdivisionApplication,Long> {
    SubdivisionApplication getAppByAppID(String appId);

    List<SubdivisionApplication> getAppByAppType(SubdivisionApplication.ApplicationType  applicationType);

}
