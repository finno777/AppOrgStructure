package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.GovernmentApplication;

public interface GovernmentApplicationService {
    GovernmentApplication initApp(String appId, String pageUri);
}
