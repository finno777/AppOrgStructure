package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;

public interface SubdivisionApplicationService {
    SubdivisionApplication initApp(String appId, String pageUri);

    SubdivisionApplication findByAppId(String appId);
}
