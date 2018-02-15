package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.Person;

import java.util.List;

public interface OrganDao extends AbstractDao<Organ,Long> {

    void delete(Long id);

    Organ findOrganById(Long id);

    List<Organ> getOrganByApplication(Application application);

    List<Organ> getNotIndexed();

}
