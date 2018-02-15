package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.CuratorPerson;
import com.digdes.rst.orgstructure.persistance.model.Government;

import java.util.List;

public interface CuratorPersonDao extends AbstractDao<CuratorPerson,Long> {
    CuratorPerson findDuplicate(CuratorPerson curatorPerson);

    List<CuratorPerson> findByIdPerson(Long l);

    List<CuratorPerson> findByGovernment(Government byId);
}
