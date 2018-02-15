package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Government;
import com.digdes.rst.orgstructure.persistance.model.GovernmentSubGroup;

import java.util.List;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */
public interface GovernmentSubGroupDao extends AbstractDao<GovernmentSubGroup,Long>  {
    List<GovernmentSubGroup> getByGovernment(Government government);

    GovernmentSubGroup getByGovernmentSubGroup(GovernmentSubGroup subGroup);
}
