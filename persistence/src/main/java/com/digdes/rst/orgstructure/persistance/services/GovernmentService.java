package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.CuratorPerson;
import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentApplication;
import com.digdes.rst.orgstructure.persistance.model.GovernmentPerson;

import java.util.List;

public interface GovernmentService {
    List<Government> getAllGovernmentByApp(GovernmentApplication application);

    Government getGovernmentById(Long governmentID);

    void saveGovernment(Government government, String appId);

    void addCuratorToGovernment(CuratorPerson divisionPerson);

    CuratorPerson removeCuratorFromGovernment(Long idCurator);

    List<Government> getGovernmentByPerson(Long personId);

    List<CuratorPerson> getGovernmentByCuratorPerson(Long l);

    List<Government> getAllGovernments();

    void deleteById(Long id);

    void saveGovernmentPerson(GovernmentPerson governmentPerson);

    GovernmentPerson getGovernmentPersonByID(Long personID);
}
