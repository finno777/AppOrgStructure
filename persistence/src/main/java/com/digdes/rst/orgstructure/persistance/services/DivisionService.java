package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Division;
import com.digdes.rst.orgstructure.persistance.model.DivisionPerson;
import com.digdes.rst.orgstructure.persistance.model.Person;

import java.util.List;

public interface DivisionService {
    Division getDivisionById(Long divisionID);

    List<Division> getDivisionsByGovernment(Long id);

    void saveDivision(Division division);

    void addDivisionPerson(DivisionPerson divisionPerson);

    DivisionPerson removeDivisionPerson(Long idDivisionPerson);

    void deleteById(Long id);

    void saveDivisionPerson(DivisionPerson divisionPerson);

    DivisionPerson getDivisionPerson(Long divisionPersonId);
}
