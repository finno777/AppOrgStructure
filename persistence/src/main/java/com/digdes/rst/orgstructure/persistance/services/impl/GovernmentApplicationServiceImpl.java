package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentApplicationDao;
import com.digdes.rst.orgstructure.persistance.model.GovernmentApplication;
import com.digdes.rst.orgstructure.persistance.services.GovernmentApplicationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Log4j
public class GovernmentApplicationServiceImpl implements GovernmentApplicationService {

    @Autowired
    GovernmentApplicationDao governmentApplicationDao;

    public GovernmentApplication initApp(String appId, String pageUri) {
        GovernmentApplication application = governmentApplicationDao.getAppByAppID(appId);
        if (application == null) {
            application = new GovernmentApplication();
            application.setAppId(appId);
            application.setPageUri(pageUri);
            governmentApplicationDao.saveOrUpdate(application);
        }

        if(pageUri!=null && application.getPageUri()!=null && !application.getPageUri().equals(pageUri)){
            application.setPageUri(pageUri);
            governmentApplicationDao.saveOrUpdate(application);
        }
        return application;
    }

    public void save(GovernmentApplication application) {
        governmentApplicationDao.saveOrUpdate(application);
    }


}
