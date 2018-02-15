package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.SubdivisionApplicationDao;
import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;
import com.digdes.rst.orgstructure.persistance.services.SubdivisionApplicationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class SubdivisionApplicationServiceImpl implements SubdivisionApplicationService{
    @Autowired
    SubdivisionApplicationDao subdivisionApplicationDao;

    public SubdivisionApplication initApp(String appId, String pageUri) {
        SubdivisionApplication application = subdivisionApplicationDao.getAppByAppID(appId);
        if (application == null) {
            application = new SubdivisionApplication(appId);
            application.setPageUri(pageUri);
            subdivisionApplicationDao.saveOrUpdate(application);
        }

        if(pageUri!=null && application.getPageUri()!=null && !application.getPageUri().equals(pageUri)){
            application.setPageUri(pageUri);
            subdivisionApplicationDao.saveOrUpdate(application);
        }

        return application;
    }

    @Override
    public SubdivisionApplication findByAppId(String appId) {
        return subdivisionApplicationDao.getAppByAppID(appId);
    }

    public void save(SubdivisionApplication application) {
        subdivisionApplicationDao.saveOrUpdate(application);
    }
}
