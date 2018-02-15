package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Application;
import java.io.Serializable;


public interface ApplicationDao extends AbstractDao<Application,Long> {

    Application findByAppId(String appId);
}
