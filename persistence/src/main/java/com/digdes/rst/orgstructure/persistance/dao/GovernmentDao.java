package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentApplication;

import java.util.List;

public interface GovernmentDao extends AbstractDao<Government,Long> {
    List<Government> findByApp(GovernmentApplication application);

    List<Government> getAllActive();
}
