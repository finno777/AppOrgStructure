package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Application;


public interface ApplicationService {

    Application initApp(String appId, String nodeId, String pageUri);

    public void save(Application application);

}
