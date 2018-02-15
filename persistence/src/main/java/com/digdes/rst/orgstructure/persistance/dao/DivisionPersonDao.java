package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Division;
import com.digdes.rst.orgstructure.persistance.model.DivisionPerson;

import java.util.List;

public interface DivisionPersonDao extends AbstractDao<DivisionPerson,Long> {
    DivisionPerson findDuplicate(DivisionPerson divisionPerson);

    List<DivisionPerson> getByDivision(Division division);
}
