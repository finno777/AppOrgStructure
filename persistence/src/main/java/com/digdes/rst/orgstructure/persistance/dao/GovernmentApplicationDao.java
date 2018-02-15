package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.GovernmentApplication;

public interface GovernmentApplicationDao extends AbstractDao<GovernmentApplication,Long> {
    GovernmentApplication getAppByAppID(String appId);
}
