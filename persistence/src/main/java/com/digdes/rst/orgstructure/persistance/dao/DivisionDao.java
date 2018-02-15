package com.digdes.rst.orgstructure.persistance.dao;


import com.digdes.rst.orgstructure.persistance.model.Division;
import com.digdes.rst.orgstructure.persistance.model.Government;

import java.util.List;

public interface DivisionDao extends AbstractDao<Division,Long> {
    List<Division> findByGovernment(Government byId);
}
