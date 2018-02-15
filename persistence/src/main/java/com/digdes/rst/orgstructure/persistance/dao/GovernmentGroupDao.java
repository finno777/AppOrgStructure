package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentGroup;

import java.util.List;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */
public interface GovernmentGroupDao extends AbstractDao<GovernmentGroup,Long>  {
    List<GovernmentGroup> getByGovernment(Government government);

    GovernmentGroup getByGovernmentGroup(GovernmentGroup governmentGroup);

}
