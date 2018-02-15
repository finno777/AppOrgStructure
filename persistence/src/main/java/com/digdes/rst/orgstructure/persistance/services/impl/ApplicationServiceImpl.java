package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.ApplicationDao;
import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.services.ApplicationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Log4j
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationDao applicationDao;

    public Application initApp(String appId, String nodeId, String pageUri) {
            log.debug("Catch appId and init application");
            Application application = applicationDao.findByAppId(appId);
            log.debug("Check for repeated object");
            if (application == null) {
                log.debug("Not found");
                application = new Application();
                application.setAppId(appId);
                application.setNodeId(nodeId);
                log.debug("Save application");
                application.setPageUri(pageUri);
                applicationDao.saveOrUpdate(application);
            }else {
                if (pageUri != null &&
                        (application.getPageUri() == null ||
                                (application.getPageUri() != null && !application.getPageUri().equals(pageUri)))) {
                    log.debug("Update Uri application : "  +application);
                    application.setPageUri(pageUri);
                    applicationDao.saveOrUpdate(application);
                }
            }
            log.debug("Found object and return it");
            return application;
    }

    public void save(Application application) {
        applicationDao.saveOrUpdate(application);
    }


}
